package pro3.mediaplayerproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox vBoxParent;



    @FXML
    private MediaView mvVideo;
    private MediaPlayer mpVideo;
    private Media mediaVideo;

    @FXML
    private HBox hBoxControls;

    @FXML
    private HBox hBoxVolume;

    @FXML
    private Button buttonPlayPause;

    @FXML
    private Label labelVolume;

    @FXML
    private Label labelCurrentTime;

    @FXML
    private Label labelTotalTime;

    @FXML
    private Label labelFullscreen;

    @FXML
    private Slider sliderVolume;

    @FXML
    private Slider sliderTime;


    private ImageView ivPlay;
    private ImageView ivPause;
    private ImageView ivReplay;
    private ImageView ivVolume;
    private ImageView ivMute;
    private ImageView ivFullscreen;
    private ImageView ivExitFS;

    // at the beginning of the video:
    private boolean isEndOfVideo = false;
    private boolean isPlaying = true;
    private boolean isMuted = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaVideo = new Media(new File("src/main/resources/pro3/mediaplayerproject/example.mp4").toURI().toString());
        mpVideo = new MediaPlayer(mediaVideo);
        mvVideo = new MediaView(mpVideo);

        final int IV_SIZE = 25;

        Image imagePlay = new Image(new File("src/main/resources/pro3/mediaplayerproject/icons/control_play_blue.png").toURI().toString());
        ivPlay = new ImageView(imagePlay);
        ivPlay.setFitHeight(IV_SIZE);
        ivPlay.setFitWidth(IV_SIZE);

        Image imagePause = new Image(new File("src/main/resources/pro3/mediaplayerproject/icons/control_pause_blue.png").toURI().toString());
        ivPause = new ImageView(imagePause);
        ivPause.setFitHeight(IV_SIZE);
        ivPause.setFitWidth(IV_SIZE);

        Image imageReplay = new Image(new File("src/main/resources/pro3/mediaplayerproject/icons/control_repeat_blue.png").toURI().toString());
        ivReplay = new ImageView(imageReplay);
        ivReplay.setFitHeight(IV_SIZE);
        ivReplay.setFitWidth(IV_SIZE);

        Image imageVolume = new Image(new File("src/main/resources/pro3/mediaplayerproject/icons/sound.png").toURI().toString());
        ivVolume = new ImageView(imageVolume);
        ivVolume.setFitHeight(IV_SIZE);
        ivVolume.setFitWidth(IV_SIZE);

        Image imageMute = new Image(new File("src/main/resources/pro3/mediaplayerproject/icons/sound_mute.png").toURI().toString());
        ivMute = new ImageView(imageMute);
        ivMute.setFitHeight(IV_SIZE);
        ivMute.setFitWidth(IV_SIZE);

        Image imageFullscreen = new Image(new File("src/main/resources/pro3/mediaplayerproject/icons/arrow_out.png").toURI().toString());
        ivFullscreen = new ImageView(imageFullscreen);
        ivFullscreen.setFitHeight(IV_SIZE);
        ivFullscreen.setFitWidth(IV_SIZE);

        Image imageExitFS = new Image(new File("src/main/resources/pro3/mediaplayerproject/icons/arrow_in.png").toURI().toString());
        ivExitFS = new ImageView(imageExitFS);
        ivExitFS.setFitHeight(IV_SIZE);
        ivExitFS.setFitWidth(IV_SIZE);

        buttonPlayPause.setGraphic(ivPause);
        labelVolume.setGraphic(ivMute);
        labelFullscreen.setGraphic(ivFullscreen);
    }
}