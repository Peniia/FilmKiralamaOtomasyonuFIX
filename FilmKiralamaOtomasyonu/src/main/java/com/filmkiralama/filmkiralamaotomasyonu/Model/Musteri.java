package com.filmkiralama.filmkiralamaotomasyonu.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Müşteri kullanıcı türünü temsil eder.
 */
public class Musteri extends Kullanici {
    private final IntegerProperty izlenenFilmSayisi = new SimpleIntegerProperty(0); // Müşterinin izlediği toplam film sayısı

    // Constructor metot
    public Musteri(int id, String kullaniciAdi, String email, String sifre) {
        super(id, kullaniciAdi, email, sifre, "Musteri"); // Rol "Musteri" olarak atanır
    }

    // Getter ve Setter
    public int getIzlenenFilmSayisi() {
        return izlenenFilmSayisi.get();
    }

    public void setIzlenenFilmSayisi(int izlenenFilmSayisi) {
        this.izlenenFilmSayisi.set(izlenenFilmSayisi);
    }

    public IntegerProperty izlenenFilmSayisiProperty() {
        return izlenenFilmSayisi;
    }

    // Yetkileri gösterme metodu
    @Override
    public void yetkileriGoster() {
        System.out.println("Müşteri Yetkileri: Film izleyebilir ve kiralayabilir.");
    }
}
