package com.example.tradoid.Business_Logic;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI extends AsyncTask<Void, Void, Void> {

    private Context context;
    private Session session;
    private String email, subject, message;

    public JavaMailAPI(Context context, String email, String subject, String message) {
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    public void sent(){
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.user", Utils.EMAIL);

        properties.put("mail.smtp.starttls.enable", "true");  // needed for gmail
        properties.put("mail.smtp.auth", "true"); // needed for gmail
        properties.put("mail.smtp.port", "587");

        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(Utils.EMAIL));
            mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.user", Utils.EMAIL);
//
//        properties.put("mail.smtp.starttls.enable", "true");  // needed for gmail
//        properties.put("mail.smtp.auth", "true"); // needed for gmail
//        properties.put("mail.smtp.port", "587");
//
//        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
//            protected PasswordAuthentication getPasswordAuthentication(){
//                return new PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD);
//            }
//        });
//
//        MimeMessage mimeMessage = new MimeMessage(session);
//        try {
//            mimeMessage.setFrom(new InternetAddress(Utils.EMAIL));
//            mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));
//            mimeMessage.setSubject(subject);
//            mimeMessage.setText(message);
//            Transport.send(mimeMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }

        return null;
    }
}
