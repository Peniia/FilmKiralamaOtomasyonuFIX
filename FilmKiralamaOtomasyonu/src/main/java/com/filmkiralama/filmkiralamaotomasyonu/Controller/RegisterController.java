package com.filmkiralama.filmkiralamaotomasyonu.Controller;

import com.filmkiralama.filmkiralamaotomasyonu.Model.Musteri;
import com.filmkiralama.filmkiralamaotomasyonu.Service.KullaniciService;
import com.filmkiralama.filmkiralamaotomasyonu.Service.EmailService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Kullanıcı kayıt işlemlerini yöneten Controller sınıfı.
 */
public class RegisterController {

    @FXML
    private TextField kullaniciAdiField;

    @FXML
    private PasswordField sifreField;

    @FXML
    private TextField emailField;

    private final KullaniciService kullaniciService = new KullaniciService();


    /**
     * Kullanıcı kayıt işlemini yönetir.
     */
    @FXML
    public void handleRegister() {
        // Kullanıcıdan, Kullanıcı adı, şifre ve e-posta girişlerini alıyoruz.
        String kullaniciAdi = kullaniciAdiField.getText();
        String sifre = sifreField.getText();
        String email = emailField.getText();

        // Alanların boş olup olmadığını kontrol edilir.
        if (kullaniciAdi == null || kullaniciAdi.isEmpty()) {
            showErrorAlert("Hata", "Kullanıcı adı boş bırakılamaz.");
            return;
        }
        if (sifre == null || sifre.isEmpty()) {
            showErrorAlert("Hata", "Şifre boş bırakılamaz.");
            return;
        }
        if (email == null || email.isEmpty() || !email.contains("@")) {
            showErrorAlert("Hata", "Geçerli bir e-posta adresi girin.");
            return;
        }

        try {
            // Yeni bir müşteri oluşturulur ve veritabanına eklenir.
            Musteri musteri = new Musteri(0, kullaniciAdi, email, sifre); // ID veritabanında otomatik atanır
            musteri.setIzlenenFilmSayisi(0); // Default olarak 0 olur
            kullaniciService.kullaniciEkle(musteri);

            // Kayıt başarılı mesajı gönderilir.
            showInfoAlert("Başarılı", "Kullanıcı başarıyla kaydedildi. Hoş geldiniz!");

            // Kullanıcıya hoş geldin e-postası gönderilir.
            sendWelcomeEmail(email, kullaniciAdi);

        } catch (IllegalArgumentException e) {
            // Kullanıcı oluşturma ile ilgili geçersiz giriş hatası gönderilir
            showErrorAlert("Hata", "Geçersiz giriş bilgileri: " + e.getMessage());
        } catch (Exception e) {
            // Beklenmeyen bir hata oluştuğunda hata mesajı gönderilir.
            e.printStackTrace();
            showErrorAlert("Hata", "Kullanıcı kaydı sırasında bir hata oluştu: " + e.getMessage());
        }
    }

    /**
     * Kullanıcıya hoş geldin e-postası gönderir.
     */
    private void sendWelcomeEmail(String email, String kullaniciAdi) {
        try {
            //mail taslağı
            String subject = "Hoş Geldiniz!"; //mail başlığı
            String message = "Merhaba " + kullaniciAdi + ",\n\nFilm Kiralama Otomasyonu'na hoş geldiniz! "
                    + "Sizi aramızda görmekten mutluluk duyuyoruz. Keyifli bir deneyim yaşamanızı dileriz.\n\n"
                    + "Saygılarımızla,\nFilm Kiralama Otomasyonu Ekibi"; // mail içeriği

            EmailService.sendEmail(email, subject, message);
            System.out.println("Hoş geldin e-postası başarıyla gönderildi: " + email);
        } catch (Exception e) {
            // E-posta gönderim hatası durumunda kullanıcıyı bilgilendiriyoruz.
            System.err.println("E-posta gönderimi sırasında bir hata oluştu: " + e.getMessage());
        }
    }

    /**
     * Hata mesajını kullanıcıya gösteriyoruz.
     */
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Kullanıcıya bilgilendirme mesajını  gösterir.
     */
    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
