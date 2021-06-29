/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.data;


import com.sun.mail.smtp.SMTPTransport;
import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago Ribeiro
 */
public class EmailHandler {
    
    private static final Logger WARNING_LOGGER_EMAIL = Logger.getLogger(EmailHandler.class.getName());
    
    public static void warningError(Exception e) {
        WARNING_LOGGER_EMAIL.log(Level.WARNING, "Error: %s", e.getMessage());
    }

    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "email_from";
    private static final String ACESS = "email_pass";
    private static final String EMAIL_FROM = "email_from";
    private static final String EMAIL_SUBJECT = "Locked Vehicle Notification";
    private static final String EMAIL_TEXT = "<h1 align=\"center\">RIDE SHARING</h1>"
                                           + "<h4 align=\"center\">by: Grupo 25</h4>"
                                           + "<h6 align=\"center\">Do not reply to this e-mail. "
                                           + "This message was generated automatically.</h6>"
                                           + "<p align=\"center\">-------------------------------------------------</p>"
                                           + "<p align=\"center\">Hey there!</p>"
                                           + "<p align=\"center\">Your vehicle was sucessfuly locked.</p>"
                                           + "<h3 align=\"center\"> Thank you for travelling with us! You rock! </h3>";
    
    public static void sendLockedVehicleEmail(String userEmail){
        
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", SMTP_SERVER);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.trust", SMTP_SERVER);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");                               
        
        Session session = Session.getInstance(properties, null);
        Message msg = new MimeMessage(session);
        
        try {
            msg.setFrom(new InternetAddress(EMAIL_FROM));                                            
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail, false));     
            msg.setSubject(EMAIL_SUBJECT);                                                          
            msg.setSentDate(new Date());
            msg.setDataHandler(new DataHandler(new HTMLDataSource(EMAIL_TEXT)));
            
            // Get SMTPTransport
            SMTPTransport transport = (SMTPTransport) session.getTransport("smtp"); 
            // Establish connection
            transport.connect(SMTP_SERVER, USERNAME, ACESS);   
            // Send
            transport.sendMessage(msg, msg.getAllRecipients());
            WARNING_LOGGER_EMAIL.log(Level.INFO, "Response: %s", transport.getLastServerResponse());
            transport.close();
            
        } catch (MessagingException e) {
            warningError(e);                                        
        } 
    }
}
