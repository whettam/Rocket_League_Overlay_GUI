module com.example.rocketleagueoverlaygui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires com.sun.jna;
    requires com.sun.jna.platform;


    opens com.example.rocketleagueoverlaygui to javafx.fxml;
    exports com.example.rocketleagueoverlaygui;
}