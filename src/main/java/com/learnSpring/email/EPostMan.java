package com.learnSpring.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * This class sends an email using JavaMailSender
 *
 */

public class EPostMan {

	private JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * This method sends an email
	 * 
	 * @param from
	 *            - Email address from which the email is being sent
	 * @param to
	 *            - Email address to which the email is being sent
	 * @param subject
	 *            - Subject of the email
	 */
		
	public void sendMail(String from, String to, String subject) {

		// Generate email content
		EmailContentGenerator emailContentGenerator = new EmailContentGenerator();
		String body = emailContentGenerator.generateEmailContent();
		// Create a MimeMessage
		MimeMessage mail = mailSender.createMimeMessage();
		//Use the helper class to populate the MimeMessage
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mail);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			//Set the boolean flag for html to true
			helper.setText(body,true);	
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		mailSender.send(mail);
	}

}
