package com.filmkiralama.filmkiralamaotomasyonu.Controller;

import com.filmkiralama.filmkiralamaotomasyonu.Model.Film;
import com.filmkiralama.filmkiralamaotomasyonu.Repository.FilmRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

/**
 * Yeni bir film ekleme işlemlerini yönettiğimiz controller.
 */
public class FilmEkleController {

    @FXML
    private TextField filmAdiField; // Film adı için  giriş alanı

    @FXML
    private TextField turField; // Tür için giriş alanı

    private final FilmRepository filmRepository = new FilmRepository(); // Film işlemleri için oluşturduğumuz repository


    /**
     * Kaydet butonuna basıldığında çalışır. Yeni bir film ekleme işlemi gerçekleştirilir.
     */
    @FXML
    public void handleSaveFilm() {
        String filmAdi = filmAdiField.getText(); // Film adını burda alırız.
        String tur = turField.getText(); // Türü burda alırız.

        // Alanların doldurulup doldurulmadığını kontrol ediyoruz.
        if (filmAdi.isEmpty() || tur.isEmpty()) {
            showAlert("Uyarı", "Eksik Bilgi", "Lütfen tüm alanları doldurun.");
            return;
        }

        // Yeni film nesnesi oluşturulur.
        Film yeniFilm = new Film(0, filmAdi, tur, false, 0);

        try {
            filmRepository.filmEkle(yeniFilm);  // Veritabanına filmi ekleriz.
            showAlert("Başarılı", "Film Eklendi", filmAdi + " başarıyla eklendi.");
            clearFields();
        } catch (SQLException e) {
            // Hata detaylarını konsola yazdırırız.
            showAlert("Hata", "Film Eklenemedi", "Veritabanı hatası: " + e.getMessage());
        }
    }


    /**
     * Giriş alanlarını temizler.
     */
    private void clearFields() {
        filmAdiField.clear();
        turField.clear();
    }


    /**
     * Bilgilendirme penceresi gösterir.
     */
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    /**
     * İptal butonuna basıldığında çalışır. Pencereyi kapatır.
     */
    @FXML
    public void handleCancel() {
        filmAdiField.getScene().getWindow().hide(); // Pencereyi kapat
    }
}
