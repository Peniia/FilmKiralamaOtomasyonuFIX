package com.filmkiralama.filmkiralamaotomasyonu.Service;

import com.filmkiralama.filmkiralamaotomasyonu.Model.Film;
import com.filmkiralama.filmkiralamaotomasyonu.Repository.FilmRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Film ile ilgili iş mantığını yöneten servis sınıfı.
 */
public class FilmService {

    private final FilmRepository filmRepository;


    /**
     * FilmService constructor metodu. FilmRepository'yi başlatır.
     */
    public FilmService() {
        this.filmRepository = new FilmRepository();
    }

    /**
     * Tüm filmleri getirilir.
     *Film listesini döndürür.
     * Veritabanı işlemi sırasında hata oluşursa hata mesajı  gönderilir
     */
    public List<Film> tumFilmleriGetir() {
        try {
            return filmRepository.tumFilmleriGetir();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Filmleri getirirken hata oluştu.");
        }
    }


    /**
     * Film kiralama işlemi.
     * @throws IllegalStateException Eğer film zaten kiradaysa Hata mesajı gönderilir.
     * @throws RuntimeException Kiralama işlemi sırasında hata oluşursa
     */
    public void filmKirala(Film film, String kiralayanKullanici) {
        if (film.isKiradaMi()) {
            throw new IllegalStateException("Bu film zaten kirada.");
        }

        film.setKiradaMi(true); // Filmin kirada olduğunu işaretlenir
        film.setKiralayanKullanici(kiralayanKullanici); // Kiralayan kullanıcıyı film nesnesine set et.

        try {
            filmRepository.filmKirala(film.getId(), kiralayanKullanici); // Veritabanını güncelle
            System.out.println(film.getAd() + " başarıyla kiralandı.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Film kiralama işlemi sırasında bir hata oluştu: " + e.getMessage());
        }
    }


    /**
     * Bir filmi izler ve izlenme sayısını artırırız.
     *
     * @param film İzlenen film nesnesi
     * @throws RuntimeException İzleme işlemi sırasında hata oluşursa gönderilecek hata
     */
    public void filmIzle(Film film) {
        film.izlenmeSayisiniArtir(); // İzlenme sayısını artırılır.

        try {
            filmRepository.filmGuncelle(film); // Veritabanında güncellenir
            System.out.println(film.getAd() + " izlendi. Toplam izlenme: " + film.getIzlenmeSayisi());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Film izleme işlemi sırasında hata oluştu.");
        }
    }

    /**
     * Kiralanan filmleri getirir.
     * @throws RuntimeException Veritabanı işlemi sırasında hata oluşursa bu hata gönderilir
     */
    public List<Film> kiralananFilmleriGetir(String kiralayanKullanici) {
        try {
            return filmRepository.kiralananFilmleriGetir(kiralayanKullanici);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Kiralanan filmler getirilirken hata oluştu.");
        }
    }

    /**
     * Kiralama işlemini sonlandırır.
     * @throws RuntimeException Kiralama sonlandırma işlemi sırasında hata oluşursa hata gönderilir.
     */
    public void kiralamayiSonlandir(int filmId) {
        try {
            filmRepository.kiralamayiSonlandir(filmId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Kiralama sonlandırılırken hata oluştu.");
        }
    }

}

