package com.filmkiralama.filmkiralamaotomasyonu.Model;

/**
 * Kullanıcıların temel özelliklerini temsil eder.
 * Admin ve Musteri sınıfları bu sınıftan türetilir.
 */
public abstract class Kullanici {
    private int id;              // Kullanıcı ID'si (otomatik oluşturulan)
    private String kullaniciAdi; // Kullanıcı adı
    private String email;        // Kullanıcı e-posta
    private String sifre;        // Kullanıcı şifre
    private String rol;          // Kullanıcı rolü (Admin veya Musteri)

    // Constructor metot (id dahil)
    public Kullanici(int id, String kullaniciAdi, String email, String sifre, String rol) {
        this.id = id;
        this.kullaniciAdi = kullaniciAdi;
        this.email = email;
        this.sifre = sifre;
        this.rol = rol;
    }

    // Getter ve Setter'lar
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public String getEmail() {
        return email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public abstract void yetkileriGoster(); // Her alt sınıfın yetkilerini gösterecek
}
