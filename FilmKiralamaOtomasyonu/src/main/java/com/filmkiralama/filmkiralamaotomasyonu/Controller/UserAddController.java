package com.filmkiralama.filmkiralamaotomasyonu.Controller;

import com.filmkiralama.filmkiralamaotomasyonu.Model.Admin;
import com.filmkiralama.filmkiralamaotomasyonu.Model.Musteri;
import com.filmkiralama.filmkiralamaotomasyonu.Model.Kullanici;
import com.filmkiralama.filmkiralamaotomasyonu.Repository.KullaniciRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Kullanıcı ekleme işlemlerini yöneten Controller sınıfı.
 */

public class UserAddController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    private final KullaniciRepository kullaniciRepository = new KullaniciRepository();

    private UserManagementController userManagementController;


    /**
     *  Controller başlatıldığında bu method ve
     *  admin panelindeki kullanıcı ekleme işlevind rol seçim kutusunu hazırlar.
     */
    @FXML
    public void initialize() {
        // Rol seçim kutusuna seçenekler eklenir.
        roleChoiceBox.setItems(FXCollections.observableArrayList("Admin", "Musteri"));
    }

    /**
     * Kullanıcı ekleme işlemini yönetilir.
     */
    @FXML
    public void handleAddUser() {
        // Giriş alanlarındaki değerleri alıyoruz.
        String kullaniciAdi = usernameField.getText();
        String email = emailField.getText();
        String sifre = passwordField.getText();
        String rol = roleChoiceBox.getValue();

        // Girdi doğrulama kontrolleri
        if (kullaniciAdi.isEmpty() ||
                email.isEmpty() ||
                sifre.isEmpty() || rol == null) {
            System.out.println("Tüm alanlar doldurulmalıdır.");
            return;
        }

        try {
            Kullanici yeniKullanici;
            // Rol bilgisine göre kullanıcı türünü oluşturulur.
            if ("Admin".equalsIgnoreCase(rol)) {
                yeniKullanici = new Admin(0, kullaniciAdi, email, sifre); // ID otomatik oluşturulacak
            } else {
                yeniKullanici = new Musteri(0, kullaniciAdi, email, sifre);
            }

            // Kullanıcıyı veritabanına ekle
            kullaniciRepository.kullaniciEkle(yeniKullanici);

            // Kullanıcı yönetim tablosunu güncellenir.
            if (userManagementController != null) {
                userManagementController.refreshTable();
            }

            System.out.println("Kullanıcı başarıyla eklendi: " + kullaniciAdi);
            closeWindow();
        }  catch (SQLException e) {
            // Veritabanı hatası durumunda kullanıcıyı bilgilendir
            showErrorAlert("Hata", "Kullanıcı eklenirken bir hata oluştu: " + e.getMessage());
        } catch (Exception e) {
            // Beklenmeyen bir hata durumunda kullanıcıyı bilgilendir
            showErrorAlert("Hata", "Beklenmeyen bir hata oluştu: " + e.getMessage());
        }
    }

    /**
     * Pencereyi kapatır.
     */
    private void closeWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
    /**
     * İptal işlemini yönetir.
     */
    @FXML
    public void handleCancel() {
        closeWindow();
    }



    /**
     * Kullanıcı yönetim ekranı kontrolcüsü atanır.
     */
    public void setUserManagementController(UserManagementController controller) {
        this.userManagementController = controller;
    }

    /**
     * Hata mesajını kullanıcıya gösterir.

     */
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    /**
     * Bilgilendirme mesajını kullanıcıya gösterir.
     */
    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
