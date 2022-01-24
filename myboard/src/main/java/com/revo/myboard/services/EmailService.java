package com.revo.myboard.services;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.revo.myboard.repositories.UserRepository;

/*
 * Email Service Class
 * 
 * Created By Revo
 */

@Service
public class EmailService {

	/*
	 * Data
	 */
	
	@Autowired
	private UserRepository userRepo;
	@Value("${mail.smtp.auth}")
	private boolean auth;
	@Value("${mail.smtp.starttls.enable}")
	private String tls;
	@Value("${mail.smtp.host}")
	private String host;
	@Value("${mail.smtp.port}")
	private String port;
	@Value("${mail.smtp.ssl.trust}")
	private String ssl;
	@Value("${mail.smtp.user}")
	private String user;
	@Value("${mail.smtp.password}")
	private String password;
	
	/*
	 * Send email with activation link
	 */
	
	public boolean sendActiavtionLink(String email) {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", auth);
		prop.put("mail.smtp.starttls.enable", tls);
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", port);
		prop.put("mail.smtp.ssl.trust", ssl);
		Session session = Session.getInstance(prop, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(user, password);
		    }
		});
		try{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("MyBoard"));
			message.setRecipients(
			  Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("MyBorad Account Activation");

			String msg = "To active your account type this code "+userRepo.findByEmail(email).get().getCode()+" on this link: https://myboard.pl/active";

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);

			Transport.send(message);
		} catch(Exception e) { return false; }
		return true;
	}
	
}
