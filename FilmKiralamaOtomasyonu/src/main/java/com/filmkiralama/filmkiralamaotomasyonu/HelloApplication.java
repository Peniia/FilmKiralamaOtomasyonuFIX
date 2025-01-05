package com.filmkiralama.filmkiralamaotomasyonu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;


import java.util.Objects;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // FXML dosyasını yukle
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/filmkiralama/filmkiralamaotomasyonu/view/LoginView.fxml")));
            System.out.println("FXML dosyasi basariyla yüklendi.");

            // Scene oluştur
            Scene scene = new Scene(root);

            // Stage ayarları
            primaryStage.setTitle("Film Kiralama Otomasyonu");
            primaryStage.setScene(scene);
            primaryStage.show(); // GUI'yi göster
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("FXML dosyası yüklenirken hata oluştu. Hata : " +
             e );
        }


    }



    public static void main(String[] args) {
        launch(args);


    }
}
