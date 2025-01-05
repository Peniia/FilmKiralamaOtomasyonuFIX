package com.filmkiralama.filmkiralamaotomasyonu.Service;

import com.filmkiralama.filmkiralamaotomasyonu.Exception.KullaniciBulunamadiException;
import com.filmkiralama.filmkiralamaotomasyonu.Model.Kullanici;
import com.filmkiralama.filmkiralamaotomasyonu.Repository.KullaniciRepository;

import java.sql.SQLException;

/**
 * Kullanıcı ile ilgili iş mantığını yöneten servis sınıfı.
 */
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;


    /**
     * KullaniciService constructor metodu. KullaniciRepository'yi başlatır.
     */
    public KullaniciService() {
        this.kullaniciRepository = new KullaniciRepository();
    }

    /**
     * Kullanıcıyı kullanıcı adı ve şifresine göre doğrulanır.
     * Eğer kullanıcı adı bulunamaz veya şifre eşleşmezse null döndürür.
     * @throws RuntimeException Veritabanı hatası oluşursa hata gönderilir.
     */
    public Kullanici kullaniciDogrula(String kullaniciAdi, String sifre) {
        try {
            // Veritabanından kullanıcıyı bulunur
            Kullanici kullanici = kullaniciRepository.kullaniciBul(kullaniciAdi);
            if (kullanici != null && kullanici.getSifre().equals(sifre)) {
                return kullanici; // Şifre eşleşiyorsa kullanıcıyı döndür
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Veritabanı hatası oluştu.", e);
        }
        return null; // Kullanıcı bulunamaz veya şifre yanlışsa null döndür
    }

    /**
     * Yeni bir kullanıcı ekler.
     * @throws RuntimeException Kullanıcı ekleme sırasında hata oluşursa hata gönderilir.
     */
    public void kullaniciEkle(Kullanici kullanici) {
        try {
            // Kullanıcıyı veritabanına ekle
            kullaniciRepository.kullaniciEkle(kullanici);
            System.out.println("Kullanıcı başarıyla eklendi: " + kullanici.getKullaniciAdi());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Kullanıcı ekleme sırasında hata oluştu.", e);
        }
    }


}
