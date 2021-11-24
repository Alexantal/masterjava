package ru.javaops.masterjava.service.mail;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import ru.javaops.masterjava.service.MailProvider;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MailSender {

    static void sendMail(List<Addressee> to, List<Addressee> cc, String subject, String body) throws EmailException, UnsupportedEncodingException {
        log.info("Send mail to \'" + to + "\' cc \'" + cc + "\' subject \'" + subject + (log.isDebugEnabled() ? "\nbody=" + body : ""));
        Email email = MailProvider.getEmail();

        List<InternetAddress> toAddresses = new ArrayList<>();
        for (Addressee a : to) {
            toAddresses.add(new InternetAddress(a.getEmail(), a.getName()));
        }
        email.setTo(toAddresses);

        List<InternetAddress> ccAddresses = new ArrayList<>();
        for (Addressee a : cc) {
            ccAddresses.add(new InternetAddress(a.getEmail(), a.getName()));
        }
        email.setCc(ccAddresses);
        email.setSubject(subject);
        email.setMsg(body);
        email.send();
    }
}
