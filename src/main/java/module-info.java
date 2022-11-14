module com.example.wolfenstein3d {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wolfenstein3d to javafx.fxml;
    exports com.example.wolfenstein3d;
}