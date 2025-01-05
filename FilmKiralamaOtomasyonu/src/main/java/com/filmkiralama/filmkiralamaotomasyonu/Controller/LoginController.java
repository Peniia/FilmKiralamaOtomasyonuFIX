package com.filmkiralama.filmkiralamaotomasyonu.Controller;

import com.filmkiralama.filmkiralamaotomasyonu.Model.Admin;
import com.filmkiralama.filmkiralamaotomasyonu.Model.Kullanici;
import com.filmkiralama.filmkiralamaotomasyonu.Model.Musteri;
import com.filmkiralama.filmkiralamaotomasyonu.Service.KullaniciService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Kullanıcı giriş ve kayıt işlemlerini yöneten Controller sınıfı.
 */
public class LoginController {

    @FXML
    private TextField kullaniciAdiField;

    @FXML
    private PasswordField passwordField;

    private final KullaniciService kullaniciService = new KullaniciService(); // Service kullanımı


    /**
     * Kullanıcı giriş işlemini yönetilir.
     */
    @FXML
    public void handleLogin() {
        // FXML bileşenlerinin doğru yüklendiğinden emin olun
        if (kullaniciAdiField == null || passwordField == null) {
            showErrorAlert("Hata", "FXML bileşenleri yüklenemedi.", "Lütfen FXML dosyasını ve controller bağlantısını kontrol edin.");
            return;
        }

        // Kullanıcı adı ve şifre girişlerini alıyoruz.
        String kullaniciAdi = kullaniciAdiField.getText();
        String password = passwordField.getText();

        //Kullanıcı adı boş mu  kontrolü
        if (kullaniciAdi == null || kullaniciAdi.isEmpty()) {
            showErrorAlert("Hata", "Kullanıcı adı boş bırakılamaz.", "");
            return;
        }

        // Şifre boş mu kontrolü
        if (password == null || password.isEmpty()) {
            showErrorAlert("Hata", "Şifre boş bırakılamaz.", "");
            return;
        }

        try {

            // Kullanıcı bilgilerini doğruluyoruz.
            Kullanici kullanici = kullaniciService.kullaniciDogrula(kullaniciAdi, password); // Doğrulama metodu

            // Doğrulama başarısızsa hata gönder.
            if (kullanici == null) {
                showErrorAlert("Hata", "Giriş başarısız.", "Kullanıcı adı veya şifre hatalı.");
                return;
            }

            System.out.println("Giriş başarılı: " + kullanici.getKullaniciAdi());

            // Kullanıcı türüne göre ilgili panele yönlendirilir.
            if (kullanici instanceof Admin) {
                adminPanelineYukle();
            } else if (kullanici instanceof Musteri) {
                musteriPanelineYukle(kullanici.getKullaniciAdi());
            }
        } catch (IllegalArgumentException e) {
            // Giriş bilgilerinde bir eksiklik veya yanlışlık varsa hata gönderir
            showErrorAlert("Hata", "Geçersiz giriş bilgileri.", e.getMessage());
        } catch (Exception e) {
            // Beklenmeyen bir hata oluştuysa hata mesajı gönderir.
            e.printStackTrace();
            showErrorAlert("Hata", "Giriş sırasında bir hata oluştu.", e.getMessage());
        }
    }


    /**
     * Kullanıcı kayıt ekranını açar.
     */
    @FXML
    public void handleRegister() {
        try {
            // Kayıt ekranı burda yüklenir
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/filmkiralama/filmkiralamaotomasyonu/view/RegisterView.fxml"));
            Parent root = loader.load();

            // Yeni bir sahne oluşturulur.
            Stage stage = new Stage();
            stage.setTitle("Kayıt Ol");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            // Hata durumunda kullanıcıyı bilgilendiren mesaj gönderilir.
            e.printStackTrace();
            showErrorAlert("Hata", "Kayıt ekranı yüklenirken bir hata oluştu.", e.getMessage());
        }
    }


    /**
     * Admin panelini açar ve yükler.
     */
    private void adminPanelineYukle() {
        try {
            // Admin paneli yüklenir
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/filmkiralama/filmkiralamaotomasyonu/view/AdminDashboard.fxml"));
            Parent root = loader.load();

            // Yeni bir sahne oluşturuyoruz ve gösteriyoruz.
            Stage stage = new Stage();
            stage.setTitle("Admin Paneli");
            stage.setScene(new Scene(root));
            stage.show();

            // Login penceresini kapatıyoruz.
            kullaniciAdiField.getScene().getWindow().hide();
        } catch (Exception e) {
            // Hata durumunda kullanıcıyı bilgilendiren  exception mesajı gönderilir.
            e.printStackTrace();
            showErrorAlert("Hata", "Admin paneli yüklenirken hata oluştu.", e.getMessage());
        }
    }

    /**
     * Müşteri panelini açar ve kullanıcı bilgilerini aktarır.
     */
    private void musteriPanelineYukle(String kullaniciAdi) {
        try {
            // Müşteri panelini yüklenir.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/filmkiralama/filmkiralamaotomasyonu/view/CustomerDashboard.fxml"));
            Parent root = loader.load();

            // Controller üzerinden kullanıcı adını aktarılır.
            CustomerDashboardController controller = loader.getController();
            controller.setCurrentKullanici(kullaniciAdi); // Kullanıcı adını müşteri paneline aktarırız.

            // Yeni bir sahne oluştur ve göster
            Stage stage = new Stage();
            stage.setTitle("Müşteri Paneli");
            stage.setScene(new Scene(root));
            stage.show();

            // Login penceresini kapat
            kullaniciAdiField.getScene().getWindow().hide();
        } catch (Exception e) {
            // Hata durumunda kullanıcıyı bilgilendiren hata mesajı gönderilir.
            e.printStackTrace();
            showErrorAlert("Hata", "Müşteri paneli yüklenirken hata oluştu.", e.getMessage());
        }
    }

    /**
     * Hata mesajını kullanıcıya da gösteriyoruz.
     */
    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
