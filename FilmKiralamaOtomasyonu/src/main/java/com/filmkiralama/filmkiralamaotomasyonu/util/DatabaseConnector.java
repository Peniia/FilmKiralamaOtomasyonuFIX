package com.filmkiralama.filmkiralamaotomasyonu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Veritabanı bağlantısını yöneten yardımcı sınıf.
 */
public class DatabaseConnector {

    //Burdaki bilgiler kendi veritabanı bilgileninize göre doldurularacak.
    private static final String URL = "jdbc:mysql://localhost:3306/film_kiralama"; // Veritabanı adı
    private static final String USERNAME = "root";  // MySQL kullanıcı adı
    private static final String PASSWORD = "admin"; // MySQL şifresi

    /**
     * Veritabanına bağlantı sağlar.
     * @return Connection nesnesi
     * @throws SQLException Bağlantı sırasında hata oluşursa hata gönderir.
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
