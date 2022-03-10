package com.revo.myboard.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.revo.myboard.exception.EmailSendingException;

/*
 * Created By Revo
 */

@Service
public class EmailService {

	@Value("${mail.smtp.host}")
	private String host;
	@Value("${mail.smtp.port}")
	private String port;
	@Value("${mail.smtp.user}")
	private String user;

	public void sendActiavtionLink(String email, String code) {
		try {
			var prop = new Properties();
			prop.put("mail.smtp.host", host);
			prop.put("mail.smtp.port", port);
			var session = Session.getInstance(prop);
			var message = new MimeMessage(session);
			message.setFrom(new InternetAddress("Activation@revo-dev.pl"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Kod aktywacyjny dla konta forum.revo-dev.pl");
			var mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent("Aby aktywować swoje konto przejdź do linku: http://forum.revo-dev.pl/#/active/" + code,
					"text/html; charset=utf-8");
			var multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);
			Transport.send(message);
		} catch(Exception exception) {
			throw new EmailSendingException(email, exception.getMessage() + exception.getCause().getMessage());
		}
	}

}
