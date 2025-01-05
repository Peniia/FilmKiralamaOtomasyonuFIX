package com.filmkiralama.filmkiralamaotomasyonu.Exception;

/**
 * Kullanıcı bulunamadığında fırlatılan özel hata sınıfı.
 */
public class KullaniciBulunamadiException extends Exception {
    public KullaniciBulunamadiException(String mesaj) {
        super(mesaj);
    }
}
