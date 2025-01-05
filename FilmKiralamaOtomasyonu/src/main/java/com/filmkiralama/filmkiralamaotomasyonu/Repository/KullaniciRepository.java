package com.filmkiralama.filmkiralamaotomasyonu.Repository;

import com.filmkiralama.filmkiralamaotomasyonu.Exception.KullaniciBulunamadiException;
import com.filmkiralama.filmkiralamaotomasyonu.Model.Admin;
import com.filmkiralama.filmkiralamaotomasyonu.Model.Kullanici;
import com.filmkiralama.filmkiralamaotomasyonu.Model.Musteri;
import com.filmkiralama.filmkiralamaotomasyonu.util.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Kullanıcı ile ilgili veritabanı işlemlerini gerçekleştiren repository sınıfı.
 */
public class KullaniciRepository {

    /**
     * Kullanıcıyı kullanıcı adına göre bulur.
     */
    public Kullanici kullaniciBul(String kullaniciAdi) throws SQLException {
        String sql = "SELECT * FROM kullanici WHERE kullanici_adi = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kullaniciAdi);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String email = rs.getString("email");
                    String sifre = rs.getString("sifre");
                    String rol = rs.getString("rol");

                    // Kullanıcının rolüne göre nesne döndürür
                    if ("admin".equalsIgnoreCase(rol)) {
                        return new Admin(id, kullaniciAdi, email, sifre);
                    } else if ("musteri".equalsIgnoreCase(rol)) {
                        return new Musteri(id, kullaniciAdi, email, sifre);
                    }
                }
            }
        }

        return null; // Kullanıcı bulunamazsa null döner
    }

    /**
     * Tüm kullanıcıları veritabanından getirir.
     * Hata durumunda hata döner
     */
    public List<Kullanici> tumKullanicilariGetir() throws SQLException {
        List<Kullanici> kullanicilar = new ArrayList<>();
        String sql = "SELECT * FROM kullanici";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String kullaniciAdi = rs.getString("kullanici_adi");
                String email = rs.getString("email");
                String sifre = rs.getString("sifre");
                String rol = rs.getString("rol");

                // Kullanıcının rolüne göre listeye ekler
                if ("admin".equalsIgnoreCase(rol)) {
                    kullanicilar.add(new Admin(id, kullaniciAdi, email, sifre));
                } else if ("musteri".equalsIgnoreCase(rol)) {
                    kullanicilar.add(new Musteri(id, kullaniciAdi, email, sifre));
                }
            }
        }

        return kullanicilar;
    }

    /**
     * Yeni bir kullanıcı ekler.
     * Veritabanı Hatası için SQLException Kullanıldı.
     */
    public void kullaniciEkle(Kullanici kullanici) throws SQLException {
        String sql = "INSERT INTO kullanici (kullanici_adi, email, sifre, rol) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kullanici.getKullaniciAdi());
            pstmt.setString(2, kullanici.getEmail());
            pstmt.setString(3, kullanici.getSifre());
            pstmt.setString(4, kullanici.getRol()); // Kullanıcı türüne göre rol verilir

            pstmt.executeUpdate(); //Sorguyu çalıştır.
        }
    }

    /**
     * Kullanıcıyı kullanıcı adına göre siler.
     * Veritabanı Hatası için SQLException Kullanıldı.
     */
    public void kullaniciSil(String kullaniciAdi) throws SQLException {
        String sql = "DELETE FROM kullanici WHERE kullanici_adi = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kullaniciAdi);
            pstmt.executeUpdate();
            System.out.println("Kullanıcı başarıyla silindi: " + kullaniciAdi);
        }
    }

    /**
     * Kullanıcının bilgilerini günceller.
     * Veritabanı Hatası için SQLException Kullanıldı.
     */
    public void kullaniciGuncelle(Kullanici kullanici) throws SQLException {
        String sql = "UPDATE kullanici SET sifre = ?, rol = ? WHERE kullanici_adi = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kullanici.getSifre());
            pstmt.setString(2, kullanici.getRol());
            pstmt.setString(3, kullanici.getKullaniciAdi());
            pstmt.executeUpdate(); // Sorgu çalıştırılır.
            System.out.println("Kullanıcı başarıyla güncellendi: " + kullanici.getKullaniciAdi());
        }
    }
}
