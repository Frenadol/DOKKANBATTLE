module com.github.Frenadol {
requires javafx.controls;
requires javafx.fxml;
requires java.xml.bind;
requires java.sql;
    requires javafx.media;
    requires java.desktop;
    opens com.github.Frenadol to javafx.fxml;
opens com.github.Frenadol.model to java.xml.bind;

exports  com.github.Frenadol;
exports com.github.Frenadol.utils;
opens  com.github.Frenadol.utils to javafx.fxml;
opens  com.github.Frenadol.model.connection;
    exports com.github.Frenadol.view.controller;
    opens com.github.Frenadol.view.controller to javafx.fxml;
}
