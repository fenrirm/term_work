module com.example.javafxlogin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens com.example.javafxlogin to javafx.fxml;
    exports com.example.javafxlogin;
    exports com.example.database_utils;
    opens com.example.database_utils to javafx.fxml;
    opens users_utils to javafx.fxml;
    exports com.example.users_utils;
    opens com.example.users_utils to javafx.fxml;
}