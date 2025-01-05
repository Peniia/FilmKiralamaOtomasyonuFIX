package com.filmkiralama.filmkiralamaotomasyonu.Controller;

import com.filmkiralama.filmkiralamaotomasyonu.Model.Kullanici;
import com.filmkiralama.filmkiralamaotomasyonu.Repository.KullaniciRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;


/**
 * Kullanıcı yönetimi işlemlerini yöneten Controller sınıfı.
 */
public class UserManagementController {

    @FXML
    private TableView<Kullanici> userTable;

    @FXML
    private TableColumn<Kullanici, Integer> idColumn;

    @FXML
    private TableColumn<Kullanici, String> usernameColumn;

    @FXML
    private TableColumn<Kullanici, String> emailColumn;

    @FXML
    private TableColumn<Kullanici, String> roleColumn;

    private final ObservableList<Kullanici> userData = FXCollections.observableArrayList();
    private final KullaniciRepository kullaniciRepository = new KullaniciRepository();



    /**
     * Controller başlatıldığında çağrılır ve tabloyu hazırlar.
     */
    @FXML
    public void initialize() {
        // TableView kolonlarını bağlama
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("kullaniciAdi"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("rol"));

        // Veritabanından kullanıcıları çekeriz
        loadUsersFromDatabase();

        // TableView'e veri ekleme
        userTable.setItems(userData);
    }

    /**
     * Veritabanından kullanıcıları yükler ve tabloya eklenir.
     */
    private void loadUsersFromDatabase() {
        try {
            userData.clear(); // Önce listeyi temizle
            userData.addAll(kullaniciRepository.tumKullanicilariGetir()); // Veritabanından kullanıcıları çek
        } catch (SQLException e) {
            showErrorAlert("Veritabanı Hatası", "Kullanıcılar yüklenirken bir hata oluştu.", e.getMessage());
        }
    }


    /**
     * Kullanıcı ekleme ekranını açar.
     */
    @FXML
    public void handleAddUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/filmkiralama/filmkiralamaotomasyonu/view/UserAddView.fxml"));
            Parent root = loader.load();

            // Kullanıcı ekleme kontrolcüsüne ana kontrolcüyü gönder.
            //Yani userAddController ve UserManagementController arasında bağlantı kur.
            UserAddController controller = loader.getController();
            controller.setUserManagementController(this);

            Stage stage = new Stage();
            stage.setTitle("Kullanıcı Ekleme");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            // Ekran yüklenirken hata oluştuğunda kullanıcıyı bilgilendir
            e.printStackTrace();
            System.out.println("Kullanıcı ekleme ekranı yüklenirken bir hata oluştu.");
        }
    }


    /**
     * Seçili kullanıcıyı siler.
     */
    @FXML
    public void handleDeleteUser() {
        Kullanici selectedUser = userTable.getSelectionModel().getSelectedItem();
        // Eğer bir kullanıcı seçilmediyse uyarı verir.
        if (selectedUser == null) {
            showInfoAlert("Uyarı", "Silinecek bir kullanıcı seçin.", "Tablodan bir kullanıcı seçmelisiniz.");
            return;
        }

        try {
            // Seçili kullanıcıyı veritabanından siler ve tabloyu güncellenir
            kullaniciRepository.kullaniciSil(selectedUser.getEmail());
            userData.remove(selectedUser);
            showInfoAlert("Başarılı", "Kullanıcı başarıyla silindi.", "Silinen kullanıcı: " + selectedUser.getKullaniciAdi());
        } catch (SQLException e) {
            // Veritabanı hatası durumunda kullanıcıyı bilgilendirir
            showErrorAlert("Veritabanı Hatası", "Kullanıcı silinirken bir hata oluştu.", e.getMessage());
        }
    }

    /**
     * Tabloyu yeniler.
     */
    public void refreshTable() {
        userData.clear();
        try {
            userData.addAll(kullaniciRepository.tumKullanicilariGetir());
        } catch (SQLException e) {
            // Tablo yenileme sırasında hata oluşursa kullanıcıyı bilgilendiririz.
            e.printStackTrace();
            System.out.println("Tablo güncellenirken bir hata oluştu.");
        }
    }
    /**
     * Hata mesajını göstermek için bir uyarı oluşturur.
     */
    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Bilgilendirme mesajını göstermek için bir uyarı oluşturur.
     */
    private void showInfoAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
