package com.macintoshfan.oldmail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * The mail utility class.
 *
 * @author Macintosh-Fan
 */
@SuppressWarnings("unused")
public class Mail {
    private final Properties properties = new Properties();
    private Session session;
    private final String usernameEmail;
    private final String password;

    /**
     * Sets up the email object.
     *
     * @param usernameEmail the email to send from
     * @param password the login password
     * @param host the email host, such as smtp.mail.me.com
     * @param port the email port, such as 587
     * @param auth use authentication
     * @param tls use tls
     */
    public Mail(String usernameEmail, String password, String host, int port, boolean auth, boolean tls) {
        this.usernameEmail = usernameEmail;
        this.password = password;

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", String.valueOf(port));
        properties.put("mail.smtp.auth", String.valueOf(auth));
        properties.put("mail.smtp.starttls.enable", String.valueOf(tls));
    }

    /**
     * Logs in to the email program.
     */
    public void login() {
        session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usernameEmail, password);
            }
        });
    }

    /**
     * Sends the email.
     *
     * @param toList [MINIMUM 1 REQUIRED] email(s) to send to (must be separated by ", ")
     * @param ccList email(s) to send carbon copies to (must be separated by ", ")
     * @param bccList email(s) to send blind carbon copies to (must be separated by ", ")
     * @param subject the subject of the email
     * @param text plain text of the email
     * @throws MessagingException if a problem occurs with email
     */
    public void sendEmail(String toList, String ccList, String bccList, String subject, String text) throws MessagingException {
        if (toList == null || toList.isBlank() || toList.isEmpty()) {
            throw new IgnoredArgumentException("The \"toList\" argument must not be empty!");
        }

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(usernameEmail));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toList));

        if (ccList != null) {
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccList));
        }

        if (bccList != null) {
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bccList));
        }

        if (subject != null) {
            message.setSubject(subject);
        }

        if (text != null) {
            message.setText(text);
        }

        Transport.send(message);
    }

    /**
     * Sends the email.
     *
     * @param toList [MINIMUM 1 REQUIRED] email(s) to send to
     * @param ccList email(s) to send carbon copies to
     * @param bccList email(s) to send blind carbon copies to
     * @param subject the subject of the email
     * @param text plain text of the email
     * @throws MessagingException if a problem occurs with email
     */
    public void sendEmail(String[] toList, String[] ccList, String[] bccList, String subject, String text) throws MessagingException {
        sendEmail(arrayToString(toList), arrayToString(ccList), arrayToString(bccList), subject, text);
    }

    /**
     * Turns a string array into a string, separated by ", ".
     *
     * @param array the array
     * @return the array as a string
     */
    private String arrayToString(String[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : array) {
            stringBuilder.append(s).append(", ");
        }
        return stringBuilder.toString();
    }
}
