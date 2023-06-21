package com.clinic.dentistry.aspect;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.service.MailService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;

@Component
@org.aspectj.lang.annotation.Aspect
public class MailAspect {

    private final ApplicationContext applicationContext;
    private MailService mailService;

    @Autowired
    public MailAspect(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void setEmailService() { this.mailService = applicationContext.getBean(MailService.class); }

    @Before("sendMailReg()")
    public void emailSendReg(JoinPoint joinPoint) {
        OutpatientCard outpatientCard = (OutpatientCard) joinPoint.getArgs()[0];
        mailService.sendNotification(
                "Здравствуйте, " + outpatientCard.getFullName() + "! \n" +
                "Благодарим за регистрацию на нашем сайте. С нетерпением ждем Вас в нашей стоматологической клинике!!! \n" +
                "С уважением, \n" +
                "Коллектив стоматологической клиники 'Улыбка премиум' ",
                outpatientCard.getEmail()
        );
    }

    @Before("sendMailAddApp()")
    public void emailSendAddApp(JoinPoint joinPoint){
        Appointment appointment = (Appointment) joinPoint.getArgs()[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");
        mailService.sendNotification(
        "Здравствуйте, " + appointment.getClient().getFullName() + "! \n" +
            "Вы успешно записаны на прием к врачу" + appointment.getDoctor().getJobTitle() +
            " " + appointment.getDoctor().getFullName() + "  на " + appointment.getDate().format(formatter) +
            "\n С нетерпением ждем Вас в нашей стоматологической клинике!!! \n" +
                "Если Ваш и планы изменились и Вы не сможете придти на прием, большая просьба отменить запись по ссылке: \n"
                + "http://стоматология.online/appointments/" + appointment.getId() + "/edit" +
            " \n С уважением, \n" +
            "Коллектив стоматологической клиники 'Улыбка премиум' ",
            appointment.getClient().getEmail()
        );
    }

    @Before("sendMailCancelApp()")
    public void emailSendCancelApp(JoinPoint joinPoint){
        Appointment appointment = (Appointment) joinPoint.getArgs()[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");
        mailService.sendNotification(
                "Здравствуйте, " + appointment.getClient().getFullName() + "! \n" +
                        "Ваша запись на прием к врачу" + appointment.getDoctor().getJobTitle() +
                        " " + appointment.getDoctor().getFullName() + "  на " + appointment.getDate().format(formatter) +
                        " успешно отменена. \n" +
                        "С уважением, \n" +
                        "Коллектив стоматологической клиники 'Улыбка премиум' ",
                appointment.getClient().getEmail()
        );
    }

    @Before("sendMailConclusion()")
    public void emailSendConclusion(JoinPoint joinPoint){
        Appointment appointment = (Appointment) joinPoint.getArgs()[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");
        mailService.sendNotification(
                "Здравствуйте, " + appointment.getClient().getFullName() + "! \n" +
                        "Звключение врача" + appointment.getDoctor().getJobTitle() +
                        " " + appointment.getDoctor().getFullName() + "  от " + appointment.getDate().format(formatter) +
                        " : \n" + appointment.getConclusion() + "\n" +
                        "С заключением, а также со списком оказанных Вам услуг можно ознакомиться по ссылке: \n"
                        + "http://стоматология.online/appointments/" + appointment.getId() + "/edit" +
                        " \n С уважением, \n" +
                        "Коллектив стоматологической клиники 'Улыбка премиум' ",
                appointment.getClient().getEmail()
        );
    }
    @Pointcut("@annotation(com.clinic.dentistry.annotations.SendMailReg)")
    public void sendMailReg() {}

    @Pointcut("@annotation(com.clinic.dentistry.annotations.SendMailAddApp)")
    public void sendMailAddApp() {}

    @Pointcut("@annotation(com.clinic.dentistry.annotations.SendMailCancelApp)")
    public void sendMailCancelApp() {}
    @Pointcut("@annotation(com.clinic.dentistry.annotations.SendMailConclusion)")
    public void sendMailConclusion() {}
}
