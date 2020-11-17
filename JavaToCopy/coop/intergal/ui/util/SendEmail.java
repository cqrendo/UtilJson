package coop.intergal.ui.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import coop.intergal.AppConst;

public class SendEmail {

	public static void SendEmail(String destinatario, String asunto, String cuerpo) {
	    String remitente = AppConst.EMAIL_REMITENTE;
	    String clave = AppConst.EMAIL_CLAVE;
	    String elhost = AppConst.EMAIL_HOST;
	    String puerto = AppConst.EMAIL_PORT;

	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", elhost);  //El servidor SMTP de Google
	    props.put("mail.smtp.user", remitente);
	    props.put("mail.smtp.clave", clave);    //La clave de la cuenta
	    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.port", puerto); //El puerto SMTP seguro de Google

	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);

	    try {
	        message.setFrom(new InternetAddress(remitente));
	        message.addRecipients(Message.RecipientType.TO, destinatario);   //Se podrían añadir varios de la misma manera
	        message.setSubject(asunto);
//	        message.setText(cuerpo);
	        
	        MimeBodyPart mimeBodyPart = new MimeBodyPart();
	        mimeBodyPart.setContent(cuerpo, "text/html");
	         
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(mimeBodyPart);
	         
	        message.setContent(multipart);
	        
	        Transport transport = session.getTransport("smtp");
	        transport.connect(elhost, remitente, clave);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	    }
	    catch (MessagingException me) {
	        me.printStackTrace();   //Si se produce un error
	    }
	}
}
