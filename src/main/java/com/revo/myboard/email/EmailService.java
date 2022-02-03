package com.revo.myboard.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * Created By Revo
 */

@Service
public class EmailService {

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
	
	public boolean sendActiavtionLink(String email, String code) {
		var prop = new Properties();
		prop.put("mail.smtp.auth", auth);
		prop.put("mail.smtp.starttls.enable", tls);
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", port);
		prop.put("mail.smtp.ssl.trust", ssl);
		var session = Session.getInstance(prop, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(user, password);
		    }
		});
		try{
			var message = new MimeMessage(session);
			message.setFrom(new InternetAddress("MyBoard"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("MyBorad Account Activation");
			var mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent("To active your account click this link: http://localhost:8080/active?code="+code, "text/html; charset=utf-8");
			var multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);
			Transport.send(message);
		} catch(Exception e) { System.out.println(e); return false; }
		return true;
	}
	
}
