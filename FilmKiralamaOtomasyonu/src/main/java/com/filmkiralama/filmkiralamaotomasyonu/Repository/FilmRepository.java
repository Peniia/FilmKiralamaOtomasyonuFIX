package com.filmkiralama.filmkiralamaotomasyonu.Repository;

import com.filmkiralama.filmkiralamaotomasyonu.Model.Film;
import com.filmkiralama.filmkiralamaotomasyonu.util.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Film ile ilgili veritabanı işlemlerini gerçekleştiren repository sınıfı.
 */
public class FilmRepository {

    // Tüm filmleri veritabanından getirir.
    public List<Film> tumFilmleriGetir() throws SQLException {
        List<Film> filmler = new ArrayList<>();
        String sql = "SELECT * FROM film";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Her bir satırı okuyarak film nesnesi oluşturur ve listeye ekler
            while (rs.next()) {
                int id = rs.getInt("id");
                String ad = rs.getString("ad");
                String tur = rs.getString("tur");
                boolean kiradaMi = rs.getBoolean("kirada_mi");
                String kiralayanKullanici = rs.getString("kiralayan_kullanici");
                int izlenmeSayisi = rs.getInt("izlenme_sayisi");

                Film film = new Film(id, ad, tur, kiradaMi, kiralayanKullanici, izlenmeSayisi);
                filmler.add(film);
            }
        }

        return filmler;
    }

    // Yeni bir film ekler.
    public void filmEkle(Film film) throws SQLException {
        String sql = "INSERT INTO film (ad, tur, kirada_mi, kiralayan_kullanici, izlenme_sayisi) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Film nesnesinden verileri alır ve sorguya bağlar
            pstmt.setString(1, film.getAd());
            pstmt.setString(2, film.getTur());
            pstmt.setBoolean(3, film.isKiradaMi());
            pstmt.setString(4, film.getKiralayanKullanici());
            pstmt.setInt(5, film.getIzlenmeSayisi());

            pstmt.executeUpdate(); // Sorguyu çalıştırır.
        }
    }

    // Bir filmi günceller.
    public void filmGuncelle(Film film) throws SQLException {
        String sql = "UPDATE film SET ad = ?, tur = ?, kirada_mi = ?, kiralayan_kullanici = ?, izlenme_sayisi = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Film nesnesinden verileri alır ve sorguya bağlar
            pstmt.setString(1, film.getAd());
            pstmt.setString(2, film.getTur());
            pstmt.setBoolean(3, film.isKiradaMi());
            pstmt.setString(4, film.getKiralayanKullanici());
            pstmt.setInt(5, film.getIzlenmeSayisi());
            pstmt.setInt(6, film.getId());

            pstmt.executeUpdate(); // Sorguyu çalıştır
        }
    }

    // Bir filmi siler.
    public void filmSil(int id) throws SQLException {
        String sql = "DELETE FROM film WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);  // Silinecek filmin ID'sini bağla
            pstmt.executeUpdate(); // Sorguyu çalıştır
        }
    }

    // Bir filmi kiralar.
    public void filmKirala(int filmId, String kiralayanKullanici) throws SQLException {
        String sql = "UPDATE film SET kirada_mi = true, kiralayan_kullanici = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Kullanıcı adı ve film ID'sini sorguya bağlar
            pstmt.setString(1, kiralayanKullanici); // Kiralayan kullanıcının adını bağlar
            pstmt.setInt(2, filmId); // Filmin ID'sini bağlar

            int affectedRows = pstmt.executeUpdate(); // Güncellemeyi çalıştır
            if (affectedRows == 0) {
                System.err.println("Film kiralama sırasında güncelleme yapılamadı. Film ID: " + filmId);
            } else {
                System.out.println("Film başarıyla kiralandı. Film ID: " + filmId + ", Kiralayan Kullanıcı: " + kiralayanKullanici);
            }
        }
    }

    // Bir filmi izler (izlenme sayısını  1 artırır).
    public void filmIzle(int filmId, int yeniIzlenmeSayisi) throws SQLException {
        String sql = "UPDATE film SET izlenme_sayisi = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // İzlenme sayısını ve film ID'sini sorguya bağlar.
            pstmt.setInt(1, yeniIzlenmeSayisi);
            pstmt.setInt(2, filmId);
            pstmt.executeUpdate();  // Sorguyu çalıştır
        }
    }

    // Kiralanan filmleri getirir.
    public List<Film> kiralananFilmleriGetir(String kiralayanKullanici) throws SQLException {
        String sql = "SELECT * FROM film WHERE kirada_mi = true AND kiralayan_kullanici = ?";
        List<Film> kiralananFilmler = new ArrayList<>();

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kiralayanKullanici); // Kullanıcı adına göre sorgu oluştururuz
            ResultSet rs = pstmt.executeQuery();

            // Her bir sonucu okuyarak film nesnesi oluştur ve listeye eklenir.
            while (rs.next()) {
                int id = rs.getInt("id");
                String ad = rs.getString("ad");
                String tur = rs.getString("tur");
                boolean kiradaMi = rs.getBoolean("kirada_mi");
                String kiralayan = rs.getString("kiralayan_kullanici");
                int izlenmeSayisi = rs.getInt("izlenme_sayisi");

                Film film = new Film(id, ad, tur, kiradaMi, kiralayan, izlenmeSayisi);
                kiralananFilmler.add(film);
            }
        }
        return kiralananFilmler;
    }

    // Kiralamayı sonlandırır.
    public void kiralamayiSonlandir(int filmId) throws SQLException {
        String sql = "UPDATE film SET kirada_mi = false, kiralayan_kullanici = NULL WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, filmId); // Filmin ID'sini bağlanır
            int rowsUpdated = pstmt.executeUpdate(); // Sorgu çalışır
            if (rowsUpdated == 0) {
                System.err.println("Kiralama sonlandırma işlemi başarısız. Film ID: " + filmId);
            } else {
                System.out.println("Kiralama başarıyla sonlandırıldı. Film ID: " + filmId);
            }
        }
    }
}
