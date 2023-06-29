package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.dto.AddAppointmentDTO;
import com.clinic.dentistry.dto.AppointmentDto;
import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.Role;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.AppointmentRepository;
import com.clinic.dentistry.repo.EmployeeRepository;
import com.clinic.dentistry.repo.UserRepository;
import com.clinic.dentistry.service.AppointmentService;
import com.clinic.dentistry.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
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

    private LocalDateTime now = LocalDateTime.now();
    @Autowired
    private MailService mailService;

    @Override
    public List<AppointmentDto> getAvailableDatesByDoctors(Iterable<User> doctors) {
        Map<User, Map<String, ArrayList<String>>> availableDatesByDoctor = new HashMap<>();
        Map<String, ArrayList<String>> availableDates;
        ArrayList<String> avalibleTimes;
        Boolean dateTaken;

        Iterable<Appointment> appointments = appointmentRepository.findByActiveTrue();

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");


        List<AppointmentDto> dtoList = new ArrayList<>();
        for (User doctor : doctors) {
            AppointmentDto dto = new AppointmentDto(doctor);

            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusWeeks(2);
            availableDates = new TreeMap<>();

            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                if (!(date.getDayOfWeek().equals(DayOfWeek.SUNDAY) || (date.getDayOfWeek().equals(DayOfWeek.MONDAY)))) {
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
                            if (appointment.getDoctor().equals(doctor.getEmployee()) && appointment.getDate().equals(time)) {
                                dateTaken = Boolean.TRUE;
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
    public Iterable<Appointment> getArchiveAppointmentsForClient(User user) {
        return appointmentRepository.findByClientAndConclusionNotNull(user.getOutpatientCard());
    }

    @Override
    public Iterable<Appointment> getArchiveAppointmentsForDoctor(User user) {
        return appointmentRepository.findByDoctorAndConclusionNotNull(user.getEmployee());
    }

    @Override
    public Iterable<Appointment> getActiveAppointmentsForClient(User user) {
        return appointmentRepository.findByClientAndActiveTrueAndConclusionNull(user.getOutpatientCard());
    }

    @Override
    public Iterable<Appointment> getActiveAppointmentsForDoctor(User user) {
        return appointmentRepository.findByDoctorAndActiveTrueAndConclusionNull(user.getEmployee());
    }


    @Override
    public List<Appointment> getClientList(User user) {
        List<Appointment> result = new ArrayList<>();
        appointmentRepository.findByClientAndConclusionNotNull(user.getOutpatientCard()).forEach(result::add);
        appointmentRepository.findByClientAndActiveTrueAndConclusionNull(user.getOutpatientCard()).forEach(result::add);
        return result;
    }

    @Override
    public List<Appointment> getDoctorList(User user) {
        List<Appointment> result = new ArrayList<>();
        appointmentRepository.findByDoctorAndConclusionNotNull(user.getEmployee()).forEach(result::add);
        appointmentRepository.findByDoctorAndActiveTrueAndConclusionNull(user.getEmployee()).forEach(result::add);
        return result;
    }


    @Override
    public Iterable<User> getActiveDoctors() {
        return userRepository.findByRolesInAndActiveTrue(Collections.singleton(Role.DOCTOR));
    }

    @Override
    public Appointment addAppointment(User user, AddAppointmentDTO addAppointment) {
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

        return appointment;
    }

    @Override
    public void cancelAppointment(Appointment appointment) {
        appointment.setActive(Boolean.FALSE);
        appointmentRepository.save(appointment);
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
        return appointment.getClient() != null && user.getOutpatientCard() != null
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
       Iterable<Appointment> appointments = appointmentRepository.findAppointmentByDoctor(doctor);
       for (Appointment appointment:appointments) {
           System.out.println(appointment.getDate() + " " + addAppointment.getDate());
             if (appointment.getDate().equals(addAppointment.getDate()))
                 return false;
       }

       return true;
       }

}





