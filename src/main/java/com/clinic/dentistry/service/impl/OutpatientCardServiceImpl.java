package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.user.UserEditForm;
import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.OutpatientCardRepository;
import com.clinic.dentistry.repo.UserRepository;
import com.clinic.dentistry.service.MailService;
import com.clinic.dentistry.service.OutpatientCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OutpatientCardServiceImpl implements OutpatientCardService {
    @Autowired
    private OutpatientCardRepository outpatientCardRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;

    @Override
    public ApiResponse userMeEdit(User user, UserEditForm form) {
        ApiResponse response = new ApiResponse();
        OutpatientCard card = user.getOutpatientCard();
        if (card == null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Ошибка структуры данных");
            return response;
        }

        StringBuilder sb = new StringBuilder();
        System.out.println(form.getUsername());
        System.out.println(user.getUsername());
        if ((!form.getUsername().equals(user.getUsername()) && form.getUsername()!=null) && form.getPassword() == null)
        {
            System.out.println("Here");
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("При смене логина обязательна смена пароля");
            return response;
        }
        if (form.getUsername() != null && !form.getUsername().equals(user.getUsername())) {
            if (!userRepository.existsByUsername(form.getUsername())) {
                user.setUsername(form.getUsername());
                sb.append("логин изменен ");
            } else {
                System.out.println("Here3");
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setMessage("Пользователь с таким логином уже существует!");
                return response;
            }
        }




        if (form.getPassword() != null && !form.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            sb.append("пароль изменен ");
        }

        if (form.getFullName() != null && !form.getFullName().equals(card.getFullName())) {
            card.setFullName(form.getFullName());
            if (user.getEmployee() != null){
                user.getEmployee().setFullName(form.getFullName());
            }
            sb.append("имя изменено ");
        }

        if (form.getEmail() != null && !form.getEmail().equals(card.getEmail())) {
                try {
                    mailService.sendNotification(
                            "Здравствуйте, " + form.getFullName() + "! \n" +
                                    "Ваш E-mail успешно изменен. \n" +
                                    "\nВаш логин: " + form.getUsername()  +
                                    "\nВаш пароль: " + form.getPassword() +
                                    "\nС уважением, \n" +
                                    "Коллектив стоматологической клиники 'Улыбка премиум' ",
                            form.getEmail(),
                            "Успешная смена e-mail"
                    );
                    mailService.sendNotification(
                            "Здравствуйте, " + card.getFullName() + "! \n" +
                                    "Ваш E-mail успешно изменен  на \n" + form.getEmail() +
                                    "\nВаш логин: " + form.getUsername()  +
                                    "\nВаш пароль: " + form.getPassword() +
                                    "\nС уважением, \n" +
                                    "Коллектив стоматологической клиники 'Улыбка премиум' ",
                            user.getOutpatientCard().getEmail(),
                            "Успешная смена e-mail"
                    );
                } catch (MailException ignored) {
                }

                card.setEmail(form.getEmail());
                sb.append("email изменен ");
            }
        else {
            if (form.getUsername() != null && form.getPassword() != null) {
                try {
                    mailService.sendNotification(
                            "Здравствуйте, " + form.getFullName() + "! \n" +
                                    "Ваши логин и пароль успешно исменены. \n" +
                                    "\nВаш логин: " + form.getUsername() +
                                    "\nВаш пароль: " + form.getPassword() +
                                    "\nС уважением, \n" +
                                    "Коллектив стоматологической клиники 'Улыбка премиум' ",
                            form.getEmail(),
                            "Успешная смена логина и пароля"
                    );
                } catch (MailException ignored) {
                }
            }
        }

        if (form.getGender() != card.getGender()) {
            card.setGender(form.getGender());
            sb.append("пол изменен ");
        }

        outpatientCardRepository.save(user.getOutpatientCard());
        userRepository.save(user);

        response.setStatus(HttpStatus.OK);
        response.setMessage(sb.toString());
        return response;
    }

}
