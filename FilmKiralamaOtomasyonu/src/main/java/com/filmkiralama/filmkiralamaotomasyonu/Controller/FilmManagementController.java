package com.filmkiralama.filmkiralamaotomasyonu.Controller;

import com.filmkiralama.filmkiralamaotomasyonu.Model.Film;
import com.filmkiralama.filmkiralamaotomasyonu.Repository.FilmRepository;
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

/*Admin yönetim panelindeki Film Yönetimini yaptığımız Controller
*/

public class FilmManagementController {

    @FXML
    private TableView<Film> filmTable;

    @FXML
    private TableColumn<Film, Integer> idColumn;

    @FXML
    private TableColumn<Film, String> titleColumn;

    @FXML
    private TableColumn<Film, String> genreColumn;

    @FXML
    private TableColumn<Film, Boolean> rentedColumn;

    @FXML
    private TableColumn<Film, Integer> viewCountColumn;

    private final ObservableList<Film> filmData = FXCollections.observableArrayList();
    private final FilmRepository filmRepository = new FilmRepository();

    @FXML
    public void initialize() {
        // Tablo sütunlarını burda modele  baglıyoruz.
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("ad"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("tur"));
        rentedColumn.setCellValueFactory(new PropertyValueFactory<>("kiradaMi"));
        viewCountColumn.setCellValueFactory(new PropertyValueFactory<>("izlenmeSayisi"));

        // Veritabanından filmleri yüklüyoruz
        loadFilmsFromDatabase();

        // Yüklenen filmleri tabloya ekliyoruz.
        filmTable.setItems(filmData);
    }

    /**
     * Veritabanından filmleri yüklenir ve tablo güncellenir.
     */
    private void loadFilmsFromDatabase() {
        try {
            filmData.clear(); //Mevcut verileri temizlenir.
            filmData.addAll(filmRepository.tumFilmleriGetir()); // yeni veriler eklenir.
        } catch (SQLException e) {
            //Veritabanı hatalarını yakalar ve kullanıya bilgi verir.
            showErrorAlert("Veritabanı Hatası", "Filmler yüklenirken bir hata oluştu.", e.getMessage());
        }
    }

    /**
     * Film ekleme ekranını açar.
     */

    @FXML
    public void handleAddFilm() {
        System.out.println("Film ekleme ekranı açılıyor...");

        try {
            // Film ekleme ekranı için yeni bir pencere oluştururuz.getResource ile de bu yolun konumunu belirtiyoruz.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/filmkiralama/filmkiralamaotomasyonu/view/FilmEkleView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Film Ekle");
            stage.setScene(new Scene(root));
            stage.show();

            // Pencere kapatıldığında tabloyu günceller.
            stage.setOnHiding(event -> loadFilmsFromDatabase());
        } catch (Exception e) {
            // Hata durumunda kullanıcıyı bilgilendiririr.
            e.printStackTrace();
            showErrorAlert("Hata", "Film ekleme ekranı açılırken bir hata oluştu.", e.getMessage());
        }
    }


    /**
     * Seçili filmi siler.
     */
    @FXML
    public void handleDeleteFilm() {
        Film selectedFilm = filmTable.getSelectionModel().getSelectedItem();
        if (selectedFilm != null) {
            try {
                // Seçili filmi veritabanından siler ve tabloyu günceller.
                filmRepository.filmSil(selectedFilm.getId());
                filmData.remove(selectedFilm);
                showInfoAlert("Başarılı", "Film başarıyla silindi.", "Silinen film: " + selectedFilm.getAd());
            } catch (SQLException e) {
                // Veritabanı hatalarını yakalar ve kullanıcıya bilgi verir.
                showErrorAlert("Veritabanı Hatası", "Film silinirken bir hata oluştu.", e.getMessage());
            }
        } else {
            // Kullanıcıya seçim yapması için bilgi verir.
            showInfoAlert("Uyarı", "Bir film seçin.", "Lütfen silmek istediğiniz bir filmi seçin.");
        }
    }

    /**
     * Hata mesajını kullanıcıya gösterir.
     */
    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    /**
     * Bilgilendirme mesajını kullanıcıya gösterir.
     */
    private void showInfoAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
