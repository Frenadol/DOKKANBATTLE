module com.github.Frenadol {
requires javafx.controls;
requires javafx.fxml;
requires java.xml.bind;
requires java.sql;
    requires javafx.media;
    requires java.desktop;
    requires javafx.web;
    opens com.github.Frenadol to javafx.fxml;


exports  com.github.Frenadol;
exports com.github.Frenadol.utils;
opens  com.github.Frenadol.utils to javafx.fxml;
opens  com.github.Frenadol.model.connection;
    exports com.github.Frenadol.view;
    opens com.github.Frenadol.view to javafx.fxml;
}
