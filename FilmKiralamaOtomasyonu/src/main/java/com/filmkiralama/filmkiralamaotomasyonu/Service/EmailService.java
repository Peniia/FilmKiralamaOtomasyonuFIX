package com.filmkiralama.filmkiralamaotomasyonu.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * E-posta gönderme işlemlerini yöneten servis sınıfı.
 */

public class EmailService {


    /**
     * Belirtilen alıcıya e-posta gönderir.
     */
    public static void sendEmail(String recipient, String subject, String messageText) throws MessagingException {
        // SMTP sunucusu bilgileri
        String host = "smtp.gmail.com"; // Gmail SMTP sunucusu
        String username = "v.emirrr@gmail.com"; // Gmail adresiniz (Bu kısıma kendi e posta adresiniz girilecek)
        String password = "ddgf frtm kxnb glww"; // Uygulama şifresi (Bunu gmail üzerinden Uygulama Şifresi kısmından oluşturup buraya girmeniz gerekli)


        // SMTP bağlantı özellikleri ayarlanır.
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // SMTP kimlik doğrulaması gerekli
        props.put("mail.smtp.starttls.enable", "true"); // TLS şifreleme etkin
        props.put("mail.smtp.host", host); // Sunucu adresi
        props.put("mail.smtp.port", "587"); // SMTP port numarası


        // E-posta oturumu başlatılır
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // Kullanıcı adı ve şifre ile kimlik doğrulama yapılır
                return new PasswordAuthentication(username, password);
            }
        });


        try {
            // E-posta mesajı oluşturulur.
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));  // Gönderici adresi ayarlanır
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient)); ; // Alıcı adresi eklenir
            message.setSubject(subject); // E-postanın konusu ayarlanır
            message.setText(messageText); // E-postanın içeriği eklenir


            // E-posta gönderimi gerçekleştirilir
            Transport.send(message);

            System.out.println("E-posta başarıyla gönderildi."); // Başarı mesajı yazdırılır


        }catch (MessagingException e) {
            // Hata durumunda kullanıcı bilgilendirilir.
            System.err.println("E-posta gönderimi sırasında bir hata oluştu: " + e.getMessage());
        }



    }





}
