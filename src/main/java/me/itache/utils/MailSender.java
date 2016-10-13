package me.itache.utils;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Sends mails to users from application address.
 *
 * @author karpachev vitaliy
 */
public class MailSender extends Thread {

    private static final Logger LOG = Logger.getLogger(MailSender.class);

    private static final String USERNAME = "vitaliy.karpachev@gmail.com";
    private static final String PASSWORD = "skeptikk88";
    private static final Properties PROPS;

    static {
        PROPS = new Properties();
        PROPS.put("mail.smtp.auth", "true");
        PROPS.put("mail.smtp.starttls.enable", "true");
        PROPS.put("mail.smtp.host", "smtp.gmail.com");
        PROPS.put("mail.smtp.port", "587");
        LOG.debug("MailSender static block has been initialized");
    }

    private Mail mail;
    private String toEmail;
    private String fromEmail;
    private boolean isSent = true;

    /**
     * Creates MailSender using LocalizedMail object and String address parameter.
     *
     * @param mail    LocalizedMail
     * @param toEmail address
     */
    public MailSender(Mail mail, String toEmail) {
        this.mail = mail;
        this.toEmail = toEmail;
    }

    /**
     * Creates MailSender using only String parameters.
     * @param topic mail topic
     * @param message mail message
     * @param fromEmail address
     */
    public MailSender(String topic, String message, String fromEmail) {
        this.mail = new Mail();
        mail.setTopic(topic);
        mail.setMessage(message);
        this.fromEmail = fromEmail;
    }

    /**
     * Sends mail and wait for result.
     *
     * @return true - if mail was sent, false otherwise
     */
    public boolean sendAndWait() {
        run();
        return isSent;
    }

    /**
     * Sends mail in separate thread
     */
    public void send() {
        start();
    }

    /**
     * Thread creates and sends mail
     */
    public void run() {
        LOG.debug("MailSender starts");
        Session session = Session.getInstance(PROPS, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message mess = new MimeMessage(session);
            mess.setFrom(new InternetAddress((fromEmail == null) ? USERNAME : fromEmail));
            mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse((toEmail == null) ? USERNAME : toEmail));
            mess.setSubject(mail.getTopic());
            mess.setText(mail.getMessage());
            Transport.send(mess);
            LOG.info("MailSender sent message --> " + toString());
        } catch (Exception ex) {
            isSent = false;
            LOG.error(me.itache.constant.Message.ERR_CANNOT_SEND_MAIL + " " + toString(), ex);
        }
    }

    public String getToEmail() {
        return toEmail;
    }

    @Override
    public String toString() {
        return "MailSender [topic=" + mail.getTopic() + ", message=" + mail.getMessage() + ", toEmail=" + toEmail + "]";
    }
}
