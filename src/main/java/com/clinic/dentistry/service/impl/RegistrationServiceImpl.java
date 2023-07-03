package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.user.RegisterForm;
import com.clinic.dentistry.dto.user.UserEditForm;
import com.clinic.dentistry.models.*;
import com.clinic.dentistry.repo.EmployeeRepository;
import com.clinic.dentistry.repo.OutpatientCardRepository;
import com.clinic.dentistry.repo.UserRepository;
import com.clinic.dentistry.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private OutpatientCardRepository outpatientCardRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MailService mailService;
    @Override
    public ApiResponse userRegistration(RegisterForm request) {
        if (this.isUserInDB(request)) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Пользователь с такими данными уже существует!")
                    .build();
        }
        String password = request.getPassword();
        OutpatientCard outpatientCard = OutpatientCard.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .gender(request.getGender())
                .build();
        outpatientCardRepository.save(outpatientCard);

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .roles(Collections.singleton(Role.USER))
                .outpatientCard(outpatientCard)
                .build();
        userRepository.save(user);
        try {
            mailService.sendNotification(
                    "Здравствуйте, " + outpatientCard.getFullName() + "! \n" +
                            "\nБлагодарим за регистрацию на нашем сайте. \n"+
                            "\nВаш логин: " + user.getUsername() +
                            "\nВаш пароль: " + password + "\n" +
                            "\nПри необходимости Вы можете изменить их по ссылке http://стоматология.online/user/me \n" +
                            " \nС нетерпением ждем Вас в нашей стоматологической клинике!!! \n" +
                            "\nС уважением, \n" +
                            "Коллектив стоматологической клиники 'Улыбка премиум' ",
                    outpatientCard.getEmail(),
                    "Успешная регистрация"
            );
        } catch (MailException ignored) {
        }

        return ApiResponse.builder()
                .status(HttpStatus.CREATED)
                .message("Регистрация прошла успешно")
                .build();
    }

    @Override
    public ApiResponse createUser(RegisterForm request) {
        if (this.isUserInDB(request)) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Пользователь с такими данными уже существует!")
                    .build();
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .roles(request.getRoles())
                .build();

        if (user.getRoles().contains(Role.DOCTOR)) {
            Employee employee = Employee.builder()
                    .fullName(request.getFullName())
                    .jobTitle(request.getJobTitle())
                    .workStart(request.getWorkStart())
                    .workEnd(request.getWorkEnd())
                    .workDays(request.getWorkDays())
                    .durationApp(request.getDurationApp())
                    .build();
            employeeRepository.save(employee);
            user.setEmployee(employee);
        }

        if (user.getRoles().contains(Role.USER)) {
            OutpatientCard outpatientCard = OutpatientCard.builder()
                    .email(request.getEmail())
                    .fullName(request.getFullName())
                    .gender(request.getGender())
                    .build();
            outpatientCardRepository.save(outpatientCard);
            user.setOutpatientCard(outpatientCard);
        }

        userRepository.save(user);
        if (user.getOutpatientCard() != null){
            try {
                mailService.sendNotification(
                        "Здравствуйте, " + user.getOutpatientCard().getFullName() + "! \n" +
                                "\nВы успешно зарегистрированы администратором на нашем сайте.\n"+
                                "\nВаш логин: " + request.getUsername() +
                                "\nВаш пароль: " + request.getPassword() + "\n" +
                                "\nПри необходимости Вы можете изменить их по ссылке http://стоматология.online/user/me \n" +
                                "\nС нетерпением ждем Вас в нашей стоматологической клинике!!! \n" +
                                "\nС уважением, \n" +
                                "Коллектив стоматологической клиники 'Улыбка премиум' ",
                        user.getOutpatientCard().getEmail(),
                        "Успешная регистрация"
                );
            } catch (MailException ignored) {
                return ApiResponse.builder()
                        .status(HttpStatus.CREATED)
                        .message("Регистрация прошла успешно")
                        .build();
            }
        }
        return ApiResponse.builder()
                .status(HttpStatus.CREATED)
                .message("Регистрация прошла успешно")
                .build();
    }

    @Override
    public ApiResponse editUser(Long userId, UserEditForm form) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Не найден пользователь с ID " + userId)
                    .build();
        }


        if (userRepository.findUserByUsername(form.getUsername()) != null &&
                !optionalUser.get().getUsername().equals(form.getUsername())){
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Пользователь с такими данными уже существует!")
                    .build();
        }


        try {
            User userDb = optionalUser.get();
            String oldFullName = "";
            String oldEmail = "";
            if (userDb.getRoles().contains(Role.USER)){
                oldFullName = userDb.getOutpatientCard().getFullName();
                oldEmail = userDb.getOutpatientCard().getEmail();
            }

            Set<Role> roles = form.getRoles() != null ? form.getRoles() : Collections.emptySet();

            if (form.getUsername() != null) userDb.setUsername(form.getUsername());

            if (form.getActive() != null) userDb.setActive(form.getActive());

            if (form.getRoles() != null) userDb.setRoles(form.getRoles());

            if (form.getPassword() != null && !form.getPassword().isEmpty()) {
                userDb.setPassword(passwordEncoder.encode(form.getPassword()));
            }

            if (roles.contains(Role.USER)) {
                OutpatientCard card = userDb.getOutpatientCard() != null ? userDb.getOutpatientCard() : new OutpatientCard();
                card.setEmail(form.getEmail());
                card.setFullName(form.getFullName());
                card.setGender(form.getGender());
                outpatientCardRepository.save(card);
                userDb.setOutpatientCard(card);
            }

            if (roles.contains(Role.DOCTOR)) {
                Employee employee = employeeRepository.findEmployeeById(form.getEmployeeId());
                userDb.setEmployee(employee);
            } else {
                userDb.setEmployee(null);
            }

            userRepository.save(userDb);
            if(userDb.getOutpatientCard() != null) {
                try {
                    String gender = "";
                    if(userDb.getOutpatientCard().getGender().equals(Gender.MALE)){
                        gender = "Мужской";
                    }
                    if(userDb.getOutpatientCard().getGender().equals(Gender.FEMALE)){
                        gender = "Женский";
                    }
                    String passwordMessage;
                    if (form.getPassword() != null){
                        passwordMessage = "\nВаш новый пароль: " + form.getPassword();
                    }
                    else {
                        passwordMessage = "\nВаш пароль остался прижним";
                    }
                    mailService.sendNotification(
                    "Здравствуйте, " + oldFullName + "! \n" +
                            "\nВаш профиль успешно отредактирован администратором.\n"+
                            "\nВаш новый логин: " + userDb.getUsername() +
                            passwordMessage +
                            "\nВаше новое ФИО: " + userDb.getOutpatientCard().getFullName() +
                            "\nВаш новый Email: " + userDb.getOutpatientCard().getEmail() +
                            "\nВаш новый пол: " + gender + "\n" +
                            "\nПри необходимости Вы можете изменить их по ссылке http://стоматология.online/user/me \n" +
                            "\nС нетерпением ждем Вас в нашей стоматологической клинике!!! \n" +
                            "\nС уважением, \n" +
                            "Коллектив стоматологической клиники 'Улыбка премиум' ",
                    oldEmail,
                    "Успешное обновление профиля"
                    );
                    if (!form.getEmail().equals(oldEmail)) {
                        mailService.sendNotification(
                                "Здравствуйте, " + form.getFullName() + "! \n" +
                                        "\nВаш профиль успешно отредактирован администратором.\n"+
                                        "\nВаш новый логин: " + userDb.getUsername() +
                                        "\nВаш новый пароль: " + form.getPassword() +
                                        "\nВаше новое ФИО: " + userDb.getOutpatientCard().getFullName() +
                                        "\nВаш новый Email: " + userDb.getOutpatientCard().getEmail() +
                                        "\nВаш новый пол: " + gender + "\n" +
                                        "\nПри необходимости Вы можете изменить их по ссылке http://стоматология.online/user/me \n" +
                                        "\nС нетерпением ждем Вас в нашей стоматологической клинике!!! \n" +
                                        "\nС уважением, \n" +
                                        "Коллектив стоматологической клиники 'Улыбка премиум' ",
                                form.getEmail(),
                                "Успешное обновление профиля"
                        );
                    }


                } catch (MailException ignore) {
                }
            }
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Пользователь отредактирован")
                    .build();
        }catch (Exception e){
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Неккоректный запрос")
                    .build();
        }

    }

    @Override
    public boolean isUsernameVacant(String username) {
        User user = userRepository.findUserByUsername(username);
        return user == null;
    }

    private boolean isUserInDB(RegisterForm request) {
        User user = userRepository.findByUsername(request.getUsername());
        return user != null;
    }

    @Override
    public HashMap<String, Object> getUserList(){
        HashMap<String, Object> model = new HashMap<>();
        model.put("users", userService.findAllUsers());
        model.put("withArchived", true);
        return model;
    }

    @Override
    public HashMap<String, Object> getUsr(Long userId){
        HashMap<String, Object> model = new HashMap<>();
        User user = userService.findUser(userId);
        if (user != null) {
            model.put("user", user);
            model.put("roles", Role.values());
            model.put("employees", employeeService.findAllEmployees());
           // model.put("users", outpatientCardService.findAllCards());
            return model;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
        );
    }

    @Override
    public HashMap<String, Object> getMyProfile(User user){
        HashMap<String, Object> model = new HashMap<>();
        model.put("user", user);
        return model;
    }
}
