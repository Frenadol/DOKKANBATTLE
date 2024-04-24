module com.github.Frenadol {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;

    opens com.github.Frenadol to javafx.fxml;
    exports com.github.Frenadol;
    exports com.github.Frenadol.model;
    opens com.github.Frenadol.model to javafx.fxml;
}
