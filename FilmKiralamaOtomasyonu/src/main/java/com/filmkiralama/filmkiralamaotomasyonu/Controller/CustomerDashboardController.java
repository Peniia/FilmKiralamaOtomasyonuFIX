package com.filmkiralama.filmkiralamaotomasyonu.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Müşteri panelini yöneten  controller'dır.
 * Müşteri panelindeki Film listesi, kiralanan filmler ve çıkış işlemlerini burda yönetiriz.
 */

public class CustomerDashboardController {

    private String currentKullanici; // Mevcut giriş yapan kullanıcının adı

    /**
     * Mevcut kullanıcının adını ayarlar.
     */
    public void setCurrentKullanici(String currentKullanici) {
        this.currentKullanici = currentKullanici;
    }

    /**
     * Film listesi ekranını açar.
     */

    @FXML
    public void handleFilmListesi() {
        System.out.println("Film listesi ekranı açılıyor...");
        try {
            //Film listesi ekranını burda yüklüyoruz.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/filmkiralama/filmkiralamaotomasyonu/view/FilmListesiView.fxml"));
            Parent root = loader.load();

            // Giriş yapan kullanıcı bilgisini FilmListesiController'a aktarıyoruz.
            FilmListesiController controller = loader.getController();
            controller.setCurrentKullanici(currentKullanici);

            Stage stage = new Stage();
            stage.setTitle("Film Listesi");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Hata", "Film listesi ekranı yüklenirken bir hata oluştu.", e.getMessage());
        }
    }


    /**
     * Kiralanan filmler ekranını açar.
     */
    @FXML
    public void handleKiralananFilmler() {
        System.out.println("Kiralanan filmler ekranı açılıyor...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/filmkiralama/filmkiralamaotomasyonu/view/KiralananFilmlerView.fxml"));
            Parent root = loader.load();

            // KiralananFilmlerController'ı alıyoruz ve currentKullanici'yı ayarlıyoruz.
            KiralananFilmlerController controller = loader.getController();
            controller.setCurrentUser(currentKullanici);

            Stage stage = new Stage();
            stage.setTitle("Kiralanan Filmler");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Kiralanan filmler ekranı yüklenirken bir hata oluştu: " + e.getMessage());
        }
    }




    /**
     * Çıkış işlemini gerçekleştirir ve login ekranını açar.
     */
    @FXML
    public void handleCikis() {
        System.out.println("Çıkış yapılıyor...");
        try {
            // Mevcut pencereyi kapatır.
            Stage currentStage = (Stage) ((Stage) Stage.getWindows().stream()
                    .filter(Window::isFocused)
                    .findFirst()
                    .orElse(null));
            if (currentStage != null) {
                currentStage.close();
            }

            // Login ekranını yükler.
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
        e.printStackTrace(); // Konsola hata detayını yazdırır.
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
