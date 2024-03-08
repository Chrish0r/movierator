package com.movierator.movierator.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class MovieRatorMailSender {
  private Logger logger = LoggerFactory.getLogger(MovieRatorMailSender.class);

  @Value("${spring.mail.host:#{null}}")
  private String smtpHost;

  @Value("${spring.mail.port:25}")
  private Integer smtpPort;

  @Value("${spring.mail.username:#{null}}")
  private String smtpUsername;

  @Value("${spring.mail.password:#{null}}")
  private String smtpPassword;

  @Value("${spring.mail.properties.mail.smtp.auth:false}")
  private Boolean smtpAuth;

  @Value("${spring.mail.properties.mail.smtp.starttls.enable:false}")
  private Boolean smtpStarttlsEnabled;

  @Bean
  public MailSender getMailSender() {
    if (smtpHost == null || smtpUsername == null || smtpPassword == null) {
      logger.warn("Mail settings not available. Using DummyMailSender as fallback!");
      return new DummyMailSender();
    }

    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(smtpHost);
    mailSender.setPort(smtpPort);
    mailSender.setUsername(smtpUsername);
    mailSender.setPassword(smtpPassword);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");

    if (smtpAuth) {
      props.put("mail.smtp.auth", "true");
    }
    if (smtpStarttlsEnabled) {
      props.put("mail.smtp.starttls.enable", "true");
    }

    return mailSender;
  }
}

class DummyMailSender implements MailSender {
  private Logger logger = LoggerFactory.getLogger(DummyMailSender.class);

  @Override
  public void send(SimpleMailMessage simpleMessage) throws MailException {
    logger.debug("Fake sending message");
  }

  @Override
  public void send(SimpleMailMessage... simpleMessages) throws MailException {
    logger.debug("Fake sending messages");
  }
}
