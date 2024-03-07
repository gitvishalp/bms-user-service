package com.bms.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CodeMailSender implements Serializable {
	
	
	private static final long serialVersionUID = -3352576607392595839L;
	
	private final JavaMailSender mailSender;
	
	
	public String sendCode(String name, String senderEmail, String code) throws UnsupportedEncodingException, MessagingException {
		try {
			MimeMessage message = mailSender.createMimeMessage();           
		    MimeMessageHelper helper = new MimeMessageHelper(message);
		    helper.setFrom("noreply@BMS");
		    helper.setTo(senderEmail);
		     
		    String subject = "BMS- ForgetPassword";
		     
		    String content = "<p>Dear "+name+","+ "</p>"
		            + "<p> We received a request to reset the password for your BMS portal"
		            + " associated with BMS. Your security is our top priority,"
		            + " and we are here to assist you in accessing your account securely."
		            + "<br>Your Temporary Password is:</p>"
		            + "<p><b> " + "<font face=\"Verdana\" size =\"5\" >" + code + "</font></b></p>"
		            + "<p><b> Use this code to login </b></p>" 
		            + "<p><font color=\"red\"> this code is valid only for first login. </font></p>"
		            + "<br>"
		            + "<b>Team BMS<b><br>"
		            + "<h2 style='color:#0771a6'>BMS</h2>";
		    helper.setSubject(subject);
		    helper.setText(content, true);
		    mailSender.send(message); 
			return "Code send to registered Email!! ";
		}catch(Exception ex) {
			return ex.toString();
		}
		
	}

}
