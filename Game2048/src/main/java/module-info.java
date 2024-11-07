module com.example.game_2048 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;

    opens com.example.game_2048 to javafx.fxml;
    exports com.example.game_2048;
}