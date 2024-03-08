package com.movierator.movierator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.movierator.movierator.model.User;

@Service
public class DeletedUserEmailSenderService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MailSender emailSender;

	public void sendConfirmationMailForDeletedUser(User user) {
		 logger.info("Sending confirmation email...");
		String email = user.getEmail();

		SimpleMailMessage message = getConfirmationMessage(user);
		message.setTo(email);

		emailSender.send(message);
		logger.info("Confrimation email to inform user " + user.getLogin()
				+ " that his or her account has been deleted, has been sent to following email: " + user.getEmail());
	}

	private SimpleMailMessage getConfirmationMessage(User user) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("kurtphife@gmail.com");
		message.setSubject("User account deleted");
		message.setText(getEmailBody(user));

		return message;
	}

	private String getEmailBody(User user) {

		return "Dear " + user.getLogin() + "," + System.lineSeparator()
		        + System.lineSeparator()
				+ "we inform you that your user account has has been deleted successfully!" + System.lineSeparator()
				+ "Should you ever wish to restore your account with all your previous account information, then please contact our support."
				+ System.lineSeparator() + "But please be aware that all your reviews have been deleted ultimately."
				+ System.lineSeparator() + "However, as already stated, all your account information and your account settings can be restored." 
				+ System.lineSeparator() + "We hope to see you again!" 
				+ System.lineSeparator() + System.lineSeparator() +  "Best regards,"
				+ System.lineSeparator() + "Your MovieRator Team";
	}
}
