module com.example.library {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.library to javafx.fxml;
    opens com.example.library.controller to javafx.fxml;
    opens com.example.library.model to javafx.base;

    exports com.example.library;
}