package ru.javaops.masterjava.service;

import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import ru.javaops.masterjava.config.Configs;

@Slf4j
public class MailProvider {

    private volatile static Config config = null;

    private static class MailHolder {
        static final Email email;

        static {
            if (config == null) {
                config = Configs.getConfig("mail.conf", "mail");
            }
            email = new SimpleEmail();
            email.setHostName(config.getString("host"));
            email.setSmtpPort(config.getInt("port"));
            email.setAuthenticator(new DefaultAuthenticator(config.getString("username"), config.getString("password")));
            email.setSSLOnConnect(config.getBoolean("useSSL"));
            email.setTLS(config.getBoolean("useTLS"));
            email.setDebug(config.getBoolean("debug"));
            try {
                email.setFrom(config.getString("fromName"));
            } catch (EmailException e) {
                log.error("Illegal argument in MailHolder Email.setFrom() method");
                e.printStackTrace();
            }
        }
    }

    public static Email getEmail() {
        return MailHolder.email;
    }

    public static void init(Config config) {
        MailProvider.config = config;
    }
}
