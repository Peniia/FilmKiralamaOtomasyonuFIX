package com.filmkiralama.filmkiralamaotomasyonu.Controller;

import com.filmkiralama.filmkiralamaotomasyonu.Model.Film;
import com.filmkiralama.filmkiralamaotomasyonu.Service.FilmService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;


/**
 * Film listesini görüntüledigimiz, film kiralama işlemini gerçekleştirdiğimiz
 * ve izleme işlemlerini yönettigimiz controller sınıfı.
 */

public class FilmListesiController {

    @FXML
    private TableView<Film> filmTable; // Film tablosu

    @FXML
    private TableColumn<Film, Integer> idColumn; // Film ID'si sütunu

    @FXML
    private TableColumn<Film, String> titleColumn; // Film adı sütunu

    @FXML
    private TableColumn<Film, String> genreColumn; // Film türü sütunu

    @FXML
    private TableColumn<Film, Boolean> kiradaMiColumn; // Kirada olup olmadığını gösteren sütun

    @FXML
    private TableColumn<Film, Integer> izlenmeSayisiColumn; // İzlenme sayısını gösteren sütun


    private final ObservableList<Film> filmData = FXCollections.observableArrayList(); // Film verilerini tutan liste
    private final FilmService filmService = new FilmService(); // FilmService
    private String currentKullanici; // Şu anki  giriş yapan kullanıcı adı


    /**
     * Giriş yapan kullanıcının adını ayarlar.
     */
    public void setCurrentKullanici(String currentKullanici) {
        this.currentKullanici = currentKullanici;
    }

    /**
     * Controller ilk yüklendiğinde çalışır. Tabloyu hazırlar ve veritabanından filmleri çeker.
     */
    @FXML
    public void initialize() {
        try {
            // Tablo sütunlarını burda baglıyoruz.
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("ad"));
            genreColumn.setCellValueFactory(new PropertyValueFactory<>("tur"));
            kiradaMiColumn.setCellValueFactory(new PropertyValueFactory<>("kiradaMi"));
            izlenmeSayisiColumn.setCellValueFactory(new PropertyValueFactory<>("izlenmeSayisi"));

            // Filmleri yüklüyoruz
            loadFilmsFromDatabase();

            // Tabloya verileri set ediyoruz.
            filmTable.setItems(filmData);
        } catch (Exception e) {
            showErrorAlert("Hata", "Başlangıçta bir hata oluştu.", e.getMessage());
        }
    }

    /**
     * Veritabanından filmleri yükler ve tabloyu günceller.
     *throws SQLException Veritabanı hatası
     */
    private void loadFilmsFromDatabase() throws SQLException {
        filmData.clear();
        filmData.addAll(filmService.tumFilmleriGetir());
    }

    /**
     * Kullanıcı tarafından film kiralama işlemi gerçekleştirilir.
     */
    @FXML
    public void handleFilmKirala() {
        Film selectedFilm = filmTable.getSelectionModel().getSelectedItem();
        if (selectedFilm != null) {
            try {
                filmService.filmKirala(selectedFilm, currentKullanici); // Kullanıcı adıyla kiralama işlemi
                showInfoAlert("Başarılı", "Film başarıyla kiralandı.", "Film: " + selectedFilm.getAd());
                loadFilmsFromDatabase(); // Tablo güncellenir.
            } catch (IllegalStateException e) {
                showErrorAlert("Hata", "Film kiralama işlemi sırasında bir hata oluştu.", e.getMessage());
            } catch (Exception e) {
                showErrorAlert("Hata", "Film kiralama işlemi sırasında beklenmeyen bir hata oluştu.", e.getMessage());
            }
        } else {
            showInfoAlert("Uyarı", "Bir film seçin.", "Lütfen kiralamak istediğiniz bir filmi seçin.");
        }
    }

    /**
     * Kullanıcı tarafından film izleme işlemi.
     */
    @FXML
    public void handleIzleFilm() {
        Film selectedFilm = filmTable.getSelectionModel().getSelectedItem();
        if (selectedFilm != null) {
            if (!selectedFilm.isKiradaMi()) {
                showInfoAlert("Uyarı", "Bu filmi kiralamadan izleyemezsiniz.", "Lütfen önce filmi kiralayın.");
            } else if (!currentKullanici.equals(selectedFilm.getKiralayanKullanici())) {
                showInfoAlert("Uyarı", "Bu filmi izleyemezsiniz.", "Film başka bir kullanıcı tarafından kiralanmıştır.");
            } else {
                try {

                    filmService.filmIzle(selectedFilm); // İzlenme işlemini güncelle
                    showInfoAlert("Başarılı", "Film başarıyla izlendi.", "İzlenen film: " + selectedFilm.getAd());
                    loadFilmsFromDatabase(); // Tabloyu güncelle
                } catch (SQLException e) {
                    showErrorAlert("Hata", "Film izleme işlemi sırasında bir hata oluştu.", e.getMessage());
                }
            }
        } else {
            showInfoAlert("Uyarı", "Bir film seçin.", "Lütfen izlemek istediğiniz bir filmi seçin.");
        }
    }

    /**
     * Hata durumlarında bir hata penceresi gösterir.
     */
    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Bilgilendirme penceresi gösterir.
  
     */

    private void showInfoAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
