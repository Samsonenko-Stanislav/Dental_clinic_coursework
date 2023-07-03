package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.dto.AddAppointmentDTO;
import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.AppointmentDto;
import com.clinic.dentistry.dto.AppointmentEditForm;
import com.clinic.dentistry.models.*;
import com.clinic.dentistry.repo.AppointmentRepository;
import com.clinic.dentistry.repo.EmployeeRepository;
import com.clinic.dentistry.repo.UserRepository;
import com.clinic.dentistry.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private GoodService goodService;

    @Autowired
    private CheckService checkService;

    private LocalDateTime now = LocalDateTime.now();
    @Autowired
    private MailService mailService;

    @Override
    public List<AppointmentDto> getAvailableDatesByDoctors(Iterable<User> doctors) {
        Map<User, Map<String, ArrayList<String>>> availableDatesByDoctor = new HashMap<>();
        Map<String, ArrayList<String>> availableDates;
        ArrayList<String> avalibleTimes;
        Boolean dateTaken;
        Iterable<Appointment> appointments = appointmentRepository.findAll();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        List<AppointmentDto> dtoList = new ArrayList<>();
        for (User doctor : doctors) {
            AppointmentDto dto = new AppointmentDto(doctor);
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusWeeks(2);
            availableDates = new TreeMap<>();
            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                System.out.println();
                if (doctor.getEmployee().getWorkDays().toString().toUpperCase().contains(date.getDayOfWeek().toString().toUpperCase())) {
                    avalibleTimes = new ArrayList();
                    LocalTime workStart = LocalTime.of(8, 0);
                    LocalTime workEnd = LocalTime.of(17, 0);
                    if (doctor.getEmployee() != null) {
                        workStart = doctor.getEmployee().getWorkStart();
                        workEnd = doctor.getEmployee().getWorkEnd();
                    }
                    LocalDateTime startTime = date.atTime(workStart);
                    LocalDateTime endTime = date.atTime(workEnd);
                    for (LocalDateTime time = startTime; time.isBefore(endTime); time = time.plusMinutes(doctor.getEmployee().getDurationApp())) {
                        dateTaken = Boolean.FALSE;
                        for (Appointment appointment : appointments) {
                            if (appointment.getDoctor().equals(doctor.getEmployee()) && appointment.getDate().equals(time)
                                    && (appointment.getActive() || (!appointment.getActive() && appointment.getConclusion() != null))) {
                                dateTaken = Boolean.TRUE;
                                break;
                            }
                        }
                        if (dateTaken.equals(Boolean.FALSE)) {
                            avalibleTimes.add(time.format(formatterTime));
                        }
                    }
                    availableDates.put(date.format(formatterDate), avalibleTimes);
                    dto.timetable.add(
                            new AppointmentDto.DayTimes(date.format(formatterDate), avalibleTimes));
                }
            }
            availableDatesByDoctor.put(doctor, availableDates);
            dtoList.add(dto);
        }
        return dtoList;
//        return availableDatesByDoctor;
    }


    @Override
    public List<Appointment> getClientList(User user) {
        List<Appointment> result = new ArrayList<>();
        appointmentRepository.findByClientAndConclusionNotNullOrderByDate(user.getOutpatientCard()).forEach(result::add);
        appointmentRepository.findByClientAndActiveTrueAndConclusionNullOrderByDate(user.getOutpatientCard()).forEach(result::add);
        return result;
    }

    @Override
    public List<Appointment> getDoctorList(User user) {
        List<Appointment> result = new ArrayList<>();
        appointmentRepository.findByDoctorAndConclusionNotNullOrderByDate(user.getEmployee()).forEach(result::add);
        appointmentRepository.findByDoctorAndActiveTrueAndConclusionNullOrderByDate(user.getEmployee()).forEach(result::add);
        return result;
    }


    @Override
    public Iterable<User> getActiveDoctors() {
        return userRepository.findByRolesInAndActiveTrue(Collections.singleton(Role.DOCTOR));
    }

    @Override
    public void addAppointment(User user, AddAppointmentDTO addAppointment) {
        Appointment appointment = new Appointment();
        Employee doctor = employeeRepository.findEmployeeById(addAppointment.getDoctorId());
        System.out.println("Here" + doctor);
        appointment.setDoctor(doctor);
        appointment.setClient(user.getOutpatientCard());
        appointment.setDate(addAppointment.getDate());
        appointment.setActive(Boolean.TRUE);
        appointmentRepository.save(appointment);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");
        try {
            mailService.sendNotification(
                    "Здравствуйте, " + appointment.getClient().getFullName() + "! \n" +
                            "\nВы успешно записаны на прием к врачу " + appointment.getDoctor().getJobTitle() +
                            " " + appointment.getDoctor().getFullName() + "  на " + appointment.getDate().format(formatter) +
                            "\n С нетерпением ждем Вас в нашей стоматологической клинике!!! \n" +
                            "Если Ваши планы изменились и Вы не сможете придти на прием, большая просьба отменить запись по ссылке: \n"
                            + "http://стоматология.online/appointments/" + "edit/" + appointment.getId() +
                            " \n С уважением, \n" +
                            "Коллектив стоматологической клиники 'Улыбка премиум' ",
                    appointment.getClient().getEmail(),
                    "Запись на прием"
            );
        } catch (MailException ignored) {
        }

    }

    @Override
    public Boolean isCanEditByDoctor(User user, Appointment appointment) {
        return appointment.getDoctor() != null && user.getEmployee() != null &&
                appointment.getDoctor().getId().equals(user.getEmployee().getId())
                && now.isAfter(appointment.getDate().toLocalDate().atStartOfDay())
                && appointment.getActive();
    }

    @Override
    public Boolean isCanCancel(User user, Appointment appointment) {
        return appointment.getActive() && appointment.getClient() != null && user.getOutpatientCard() != null
                && appointment.getClient().getId().equals(user.getOutpatientCard().getId())
                && now.isBefore(appointment.getDate().toLocalDate().atStartOfDay().plusDays(1));
    }

    @Override
    public Appointment findAppointment(Long id) {
        return appointmentRepository.findAppointmentById(id);
    }

    @Override
    public Boolean isVacantAppointment(AddAppointmentDTO addAppointment){
        Employee doctor = employeeRepository.findEmployeeById(addAppointment.getDoctorId());
        if (doctor == null)
            return false;
       Iterable<Appointment> appointments = appointmentRepository.findAppointmentByDoctorOrderByDate(doctor);
       for (Appointment appointment:appointments) {
           System.out.println(appointment.getDate() + " " + addAppointment.getDate());
             if (appointment.getDate().equals(addAppointment.getDate()) &&
           (appointment.getActive().equals(true) ||
                   appointment.getActive().equals(false) && appointment.getConclusion()!=null ))
                 return false;
       }
       return true;
       }
    @Override
    public Boolean isUserAppointment(User user, Appointment appointment){
            return (!(user.getOutpatientCard() != null && !appointment.getClient().getId().equals(user.getOutpatientCard().getId())
                    || user.getEmployee() != null && !appointment.getDoctor().getId().equals(user.getEmployee().getId())))
                    || ((user.getOutpatientCard() != null && user.getEmployee() != null &&(appointment.getClient().getId().equals(user.getOutpatientCard().getId())
                      || appointment.getDoctor().getId().equals(user.getEmployee().getId()))));
    }

    @Override
    public List<AppointmentDto> appointmentsAddForm(){
        Map<String, Object> model = new HashMap<>();
        Iterable<User> doctors = getActiveDoctors();
        List<AppointmentDto> availableDatesByDoctor = getAvailableDatesByDoctors(doctors);
        model.put("doctors", availableDatesByDoctor);
        return availableDatesByDoctor;
    }

    @Override
    public ApiResponse appointmentsAdd(User user, AddAppointmentDTO addAppointment){
        if (employeeService.findEmployee(addAppointment.getDoctorId()) == null)
            return ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Врач с ID " + addAppointment.getDoctorId() + " не найден!!")
                    .build();
        if (isVacantAppointment(addAppointment)) {
            addAppointment(user, addAppointment);
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED)
                    .message("Запись добавлена!")
                    .build();
        }

        return ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Данное время не доступно!")
                .build();
    }

    @Override
    public Map<String, Object> getAppointment(User user, Long appointmentId){
        Map<String, Object> model = new HashMap<>();

        Appointment appointment =  findAppointment(appointmentId);
        if (appointment != null) {
            if (isUserAppointment(user, appointment)) {
                Boolean readOnly = Boolean.TRUE;
                Boolean canCancel = Boolean.FALSE;
                if (isCanEditByDoctor(user, appointment)) {
                    readOnly = Boolean.FALSE;
                    Iterable<Good> goods = goodService.findActiveGoods();
                    model.put("goods", goods);
                }

                Optional<Check> check = checkService.findCheck(appointment);
                if (check.isPresent()) {
                    Iterable<CheckLine> checkLines = checkService.findCheckLines(check);
                    model.put("checkLines", checkLines);
                }

                if (isCanCancel(user, appointment)) {
                    canCancel = Boolean.TRUE;
                }

                model.put("appointment", appointment);
                model.put("readOnly", readOnly);
                model.put("canCancel", canCancel);
                return model;
            }
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
        );
    }

    @Override
    public ApiResponse editAppointment(User user, Long appointmentId, AppointmentEditForm form){
        Appointment appointment = findAppointment(appointmentId);
        if (appointment != null) {
            if  (isCanEditByDoctor(user, appointment )){
                checkService.addConclusion(appointment, form);
                checkService.createCheckFromForm(form, appointment);
               return ApiResponse.builder()
                        .status(HttpStatus.CREATED)
                        .message("Врачебное заключение добавлено!")
                        .build();
            }
            else return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Вы не можете оставаить заключение по записи с ID " + appointmentId + " !")
                    .build();

        }
        else return ApiResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("Запись с ID " + appointmentId + " не найдена!")
                .build();
    }

    @Override
    public ApiResponse appointmentsCancel(User user, Long appointmentId){
        Appointment appointment = appointmentRepository.findAppointmentById(appointmentId);
        if (appointment != null){
            if (isCanCancel(user, appointment)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");
                try{
                    mailService.sendNotification(
                            "Здравствуйте, " + appointment.getClient().getFullName() + "! \n" +
                                    "\nВаша запись на прием к врачу" + appointment.getDoctor().getJobTitle() +
                                    " " + appointment.getDoctor().getFullName() + "  на " + appointment.getDate().format(formatter) +
                                    " успешно отменена. \n" +
                                    "\nС уважением, \n" +
                                    "Коллектив стоматологической клиники 'Улыбка премиум' ",
                            appointment.getClient().getEmail(),
                            "Отмена записи на прием"
                    );
                } catch (MailException ignored) {
                }
                appointmentRepository.delete(appointment);
                return ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Запись с ID "+ appointmentId + " успешно отменена!").build();
            }
            else
                return ApiResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("Вы не можете отменить запись с ID " + appointmentId + "!").build();
        }
        else
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Вы не можете отменить запись с ID " + appointmentId + "!").build();
    }
}





