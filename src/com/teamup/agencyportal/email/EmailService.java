
package com.teamup.agencyportal.email;

import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.teamup.agencyportal.constant.AgencyPortalConstant;

public class EmailService
{
	private static EmailService instance = null;
    Properties properties = System.getProperties();
    Session session = null;
    InternetAddress from =  null;
	private Logger log = Logger.getLogger(getClass());

    public EmailService() { 
    	
    	
	    	  properties.put("mail.smtp.host",AgencyPortalConstant.EMAIL_HOST);
		      properties.put("mail.smtp.auth","true");
		      properties.put("mail.smtp.starttls.enable","true");
		      try {
				  	  from = new InternetAddress(AgencyPortalConstant.EMAIL_FROM);
			           session =  Session.getDefaultInstance(properties,  
		    		   new javax.mail.Authenticator() {  
			        	   @Override
		    	  			protected PasswordAuthentication getPasswordAuthentication() {  
		    	  				return new PasswordAuthentication(AgencyPortalConstant.EMAIL_USERNAME,AgencyPortalConstant.EMAIL_PASSWORD);  
		    	  			}  
		      		}); 
		      } catch (Exception e) {log.error(e);}
	}
    
    public static EmailService getInstance()
	{
		if (instance == null)
			instance = new EmailService();
		return instance;
	}

	public void sendEMail(String to,String subject,String message)	{
			try
			{
				log.info(to);
		    	log.info(subject);
		    	log.info(message);
					if (subject != null)
					{
						int n = subject.indexOf('\n');
						if (n > 0)
							subject = subject.substring(0, n);
					}
					 Multipart multipart = new MimeMultipart("alternative");
					
					 MimeBodyPart htmlMsgBody = new MimeBodyPart();
					 htmlMsgBody.setContent(message, "text/html");
					 multipart.addBodyPart(htmlMsgBody);
					
					final MimeMessage mimemsg = new MimeMessage(session); 
					if (to.indexOf(';') != -1)
					{
						StringTokenizer st = new StringTokenizer(to, ";");
						while (st.hasMoreTokens())
							mimemsg.addRecipient(Message.RecipientType.TO, new InternetAddress(st.nextToken()));
					}
					else
						mimemsg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					
					mimemsg.setSubject(subject);
			        mimemsg.setFrom(from); 
			        mimemsg.setRecipient(RecipientType.TO, new InternetAddress(to)); 
			        mimemsg.setSubject(subject); 
			        mimemsg.setContent(multipart);
			        Transport.send(mimemsg); 
			}
			catch (Exception e)
			{
				log.error("Error sending email"+ e);
				
			}
		}
}
