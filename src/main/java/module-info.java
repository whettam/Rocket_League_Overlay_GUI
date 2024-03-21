module com.example.rocketleagueoverlaygui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.rocketleagueoverlaygui to javafx.fxml;
    exports com.example.rocketleagueoverlaygui;
}