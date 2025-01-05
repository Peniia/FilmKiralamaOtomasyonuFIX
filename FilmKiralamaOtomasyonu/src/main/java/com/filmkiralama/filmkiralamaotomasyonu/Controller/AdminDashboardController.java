package com.filmkiralama.filmkiralamaotomasyonu.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
/**
 * Admin panelini yöneten controller'dır..
 * Admin panelindeki Kullanıcı yönetimi, film yönetimi ve çıkış işlemleri gibi
 * işlemleri gerçekleştiriyoruz.
 */

public class AdminDashboardController {

    @FXML
    private Button cikisButton;



    /**
     * Admin paneli başlatıldığında çağrılır.
     */
    @FXML
    public void initialize() {


        System.out.println("Admin Paneli Başarıyla Yüklendi.");
    }



    /**
     * Kullanıcı yönetimi ekranını açar.
     */

    @FXML
    public void handleKullaniciYonetimi() {

        System.out.println("Kullanıcı yönetimi ekranı açılıyor...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/filmkiralama/filmkiralamaotomasyonu/view/UserManagement.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Kullanıcı Yönetimi");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Hata", "Kullanıcı yönetimi ekranı yüklenirken bir hata oluştu.", e.getMessage());
        }
    }


    /**
     * Film yönetimi ekranını açar.
     */
    @FXML
    public void handleFilmYonetimi() {

        System.out.println("Film yönetimi ekranı açılıyor...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/filmkiralama/filmkiralamaotomasyonu/view/FilmManagementView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Film Yönetimi");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Hata", "Film yönetimi ekranı yüklenirken bir hata oluştu.", e.getMessage());
        }
    }


    /**
     * Çıkış işlemini gerçekleştirilir ve login ekranını açar.
     */
    @FXML
    public void handleCikis() {
        System.out.println("Çıkış yapılıyor...");
        try {
            // Mevcut pencereyi kapatıyoruz.
            Stage currentStage = (Stage) cikisButton.getScene().getWindow();
            currentStage.close();

            // Login ekranınını burda yüklüyoruz.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/filmkiralama/filmkiralamaotomasyonu/view/LoginView.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            loginStage.setTitle("Giriş Ekranı");
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Hata", "Çıkış işlemi sırasında bir hata oluştu.", e.getMessage());
        }
    }


    /**
     * Hata mesajını konsola yazdırır ve kullanıcıya bir hata uyarısı gösterir.
     */
    private void logAndShowError(String title, String header, Exception e) {
        e.printStackTrace(); // Konsola hata detayını yazdırıyoruz.
        showErrorAlert(title, header, e.getMessage());
    }

    /**
     * Hata uyarısı gösterir.
     */
    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
