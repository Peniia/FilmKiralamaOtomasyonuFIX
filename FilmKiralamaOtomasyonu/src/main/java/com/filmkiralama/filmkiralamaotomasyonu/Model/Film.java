package com.filmkiralama.filmkiralamaotomasyonu.Model;

/**
 * Film verilerini temsil eden sınıf.
 */
public class Film {
    private int id;               // Filmin benzersiz ID'si (veritabanı tarafından otomatik atanır)
    private String ad;            // Filmin adı
    private String tur;           // Filmin türü
    private boolean kiradaMi;     // Filmin kirada olup olmadığı
    private String kiralayanKullanici; // Kiralayan kullanıcının kullanıcı adı
    private int izlenmeSayisi;    // Filmin izlenme sayısı

    // Constructor metot (ID olmadan, yeni eklenen filmler için kullanılır)
    public Film(String ad, String tur) {
        this.ad = ad;
        this.tur = tur;
        this.kiradaMi = false;    // Varsayılan olarak kirada değil
        this.kiralayanKullanici = null; // Yeni film kirada değildir, dolayısıyla null
        this.izlenmeSayisi = 0;   // Varsayılan olarak hiç izlenmemiş
    }

    // Constructor metot (ID ile, veritabanından alınan filmler için kullanılır)
    public Film(int id, String ad, String tur, boolean kiradaMi, String kiralayanKullanici, int izlenmeSayisi) {
        this.id = id;
        this.ad = ad;
        this.tur = tur;
        this.kiradaMi = kiradaMi;
        this.kiralayanKullanici = kiralayanKullanici;
        this.izlenmeSayisi = izlenmeSayisi;
    }

    // Constructor metot (ID ve kiralayan olmadan, bazı işlemler için kullanılabilir)
    public Film(int id, String ad, String tur, boolean kiradaMi, int izlenmeSayisi) {
        this.id = id;
        this.ad = ad;
        this.tur = tur;
        this.kiradaMi = kiradaMi;
        this.kiralayanKullanici = null;
        this.izlenmeSayisi = izlenmeSayisi;
    }

    // Getter ve Setter metodları
    public int getId() {
        return id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public boolean isKiradaMi() {
        return kiradaMi;
    }

    public void setKiradaMi(boolean kiradaMi) {
        this.kiradaMi = kiradaMi;
    }

    public String getKiralayanKullanici() {
        return kiralayanKullanici;
    }

    public void setKiralayanKullanici(String kiralayanKullanici) {
        this.kiralayanKullanici = kiralayanKullanici;
    }

    public int getIzlenmeSayisi() {
        return izlenmeSayisi;
    }

    public void setIzlenmeSayisi(int izlenmeSayisi) {
        this.izlenmeSayisi = izlenmeSayisi;
    }

    public void izlenmeSayisiniArtir() {
        this.izlenmeSayisi++;
    }

    // getTitle metodu (tabloda kullanılabilir)
    public String getTitle() {
        return this.ad;
    }
}
