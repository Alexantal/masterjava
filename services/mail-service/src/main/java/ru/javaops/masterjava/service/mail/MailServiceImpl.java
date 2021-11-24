package ru.javaops.masterjava.service.mail;

import com.google.common.collect.ImmutableList;
import org.apache.commons.mail.EmailException;

import javax.jws.WebService;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebService(endpointInterface = "ru.javaops.masterjava.service.mail.MailService")
public class MailServiceImpl implements MailService {
    public void sendMail(List<Addressee> to, List<Addressee> cc, String subject, String body) {
        try {
            MailSender.sendMail(to, cc, subject, body);
        } catch (EmailException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MailService mailService = new MailServiceImpl();
        mailService.sendMail(ImmutableList.of(new Addressee("masterjava@javaops.ru", null)), null, "Subject", "Body");
    }
}