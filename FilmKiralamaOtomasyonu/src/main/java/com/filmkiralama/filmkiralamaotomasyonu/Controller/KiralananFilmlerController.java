package com.filmkiralama.filmkiralamaotomasyonu.Controller;

import com.filmkiralama.filmkiralamaotomasyonu.Model.Film;
import com.filmkiralama.filmkiralamaotomasyonu.Service.FilmService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class KiralananFilmlerController {

    @FXML
    private TableView<Film> kiralananFilmlerTable;

    @FXML
    private TableColumn<Film, Integer> idColumn;

    @FXML
    private TableColumn<Film, String> titleColumn;

    @FXML
    private TableColumn<Film, String> genreColumn;


    private final ObservableList<Film> kiralananFilmlerData = FXCollections.observableArrayList();
    private final FilmService filmService = new FilmService();
    private String currentUser; // Şu anki giriş yapan kullanıcının kullanıcı adı

    /**
     * Tabloyu ve sütunları hazırlanır ve kullanıcıya ait kiralanan filmler bu methodda yüklenir.
     */
    @FXML
    public void initialize() {
        // Tablo sütunlarını model sınıfındaki özelliklere bağlıyoruz.
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("ad"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("tur"));

        // Veritabanından gelen filmleri tabloya bağlarız.
        kiralananFilmlerTable.setItems(kiralananFilmlerData);
    }

    /**
     * Giriş yapan kullanıcının adını ayarlanır ve ilgili filmleri yükler.
     */
    public void setCurrentUser(String currentKullanici) {
        this.currentUser = currentKullanici;
        loadKiralananFilmlerFromDatabase();
    }

    /**
     * Veritabanından şu anki kullanıcıya ait kiralanan filmleri yükler.
     */
    private void loadKiralananFilmlerFromDatabase() {
        try {
            // Kullanıcıya ait kiralanan filmleri veritabanından getirilir.
            List<Film> kiralananFilmler = filmService.kiralananFilmleriGetir(currentUser);

            // Tabloyu temizlenir ve yeni veriler eklenir.
            kiralananFilmlerData.clear();
            kiralananFilmlerData.addAll(kiralananFilmler);
        } catch (Exception e) {
            // Hata durumunda kullanıcıyı bilgilendirilir.
            showErrorAlert("Hata", "Kiralanan filmler yüklenirken bir sorun oluştu.", e.getMessage());
        }
    }



    /**
     * Tablo üzerinden seçilen filmin kiralamasını sonlandırıyoruz.
     */
    @FXML
    public void handleKiralamayiSonlandir() {
        // Önce Tablo üzerinden seçilen filmi al

        Film selectedFilm = kiralananFilmlerTable.getSelectionModel().getSelectedItem();
        // Seçim yapılmadıysa kullanıcıya uyarı gönderir.
        if (selectedFilm != null) {
            if (!currentUser.equals(selectedFilm.getKiralayanKullanici())) {
                showErrorAlert("Hata", "Bu filmi sonlandırma yetkiniz yok.", "Yalnızca kendi kiraladığınız filmleri sonlandırabilirsiniz.");
                return;
            }
            try {
                // Burda kiralamayı sonladırıyoruz.
                filmService.kiralamayiSonlandir(selectedFilm.getId());
                //Başarı Mesasjı gösteriyoruz.
                showInfoAlert("Başarılı", "Kiralama başarıyla sonlandırıldı.", "Film: " + selectedFilm.getAd());
                loadKiralananFilmlerFromDatabase(); // Tabloyu güncelliyoruz.
            } catch (Exception e) {
                // Olası hata durumunda kullanıcıyı bilgilendiriyoruz.
                showErrorAlert("Hata", "Kiralama sonlandırma sırasında bir hata oluştu.", e.getMessage());
            }
        } else {
            showInfoAlert("Uyarı", "Bir film seçin.", "Kiralamanın sonlandırılması için bir film seçmelisiniz.");
        }
    }

    /**
     * Hata mesajını kullanıcıya gösteriyoruz.
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
