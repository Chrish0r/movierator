package com.movierator.movierator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.movierator.movierator.model.MediaRating;

@Service
public class DeletedReviewEmailSenderService {

	@Autowired
	private MailSender emailSender;

	public void sendConfirmationMailForDeletedReview(MediaRating rating) {
	
		String email = rating.getUser().getEmail();
		
		SimpleMailMessage message = getConfirmationMessage();
		message.setTo(email);
		
		emailSender.send(message);
	}

	private SimpleMailMessage getConfirmationMessage() {
		SimpleMailMessage message = new SimpleMailMessage();
	    message.setFrom("kurtphife@gmail.com");
	    message.setSubject("Review deleted");
	    message.setText("Your review has been deleted successfully!");

	    return message;
	}
	
}
