module pro3.mediaplayerproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;


    opens pro3.mediaplayerproject to javafx.fxml;
    exports pro3.mediaplayerproject;
}