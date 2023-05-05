module com.example.javafxlogin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens com.example.javafxlogin to javafx.fxml;
    exports com.example.javafxlogin;
}