package com.filmkiralama.filmkiralamaotomasyonu.Model;

/**
 * Admin kullanıcı türünü temsil eder.
 */
public class Admin extends Kullanici {

    // Constructor metot
    public Admin(int id, String kullaniciAdi, String email, String sifre) {
        super(id, kullaniciAdi, email, sifre, "Admin"); // Rol otomatik olarak "Admin"
    }

    @Override
    public void yetkileriGoster() {
        System.out.println("Admin Yetkileri: Kullanıcı ve film işlemlerini yönetebilir.");
    }
}
