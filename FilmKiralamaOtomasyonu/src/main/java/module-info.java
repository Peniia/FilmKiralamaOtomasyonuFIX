module com.filmkiralama.filmkiralamaotomasyonu {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires javax.mail.api;

    // JavaFX'in erişmesi gereken paketler
    opens com.filmkiralama.filmkiralamaotomasyonu.Controller to javafx.fxml;
    opens com.filmkiralama.filmkiralamaotomasyonu.Model to javafx.base;

    // Dışa açılan paketler
    exports com.filmkiralama.filmkiralamaotomasyonu;
    exports com.filmkiralama.filmkiralamaotomasyonu.Model; // Model dışa açılabilir
}
