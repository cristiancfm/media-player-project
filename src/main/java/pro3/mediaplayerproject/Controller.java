package pro3.mediaplayerproject;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

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
    private Button buttonVolume;

    @FXML
    private Label labelCurrentTime;

    @FXML
    private Label labelTotalTime;

    @FXML
    private Button buttonFullscreen;

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
        mvVideo.setMediaPlayer(mpVideo);

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
        buttonVolume.setGraphic(ivMute);
        buttonFullscreen.setGraphic(ivFullscreen);

        mpVideo.play();


        buttonPlayPause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button buttonPlay = (Button) actionEvent.getSource();
                if(isEndOfVideo){
                    sliderTime.setValue(0);
                    isEndOfVideo = false;
                    isPlaying = false;
                }
                if(isPlaying){
                    buttonPlay.setGraphic(ivPlay);
                    mpVideo.pause();
                    isPlaying = false;
                } else{
                    buttonPlay.setGraphic(ivPause);
                    mpVideo.play();
                    isPlaying = true;
                }
            }
        });



        //bind the video volume with the volume slider
        mpVideo.volumeProperty().bindBidirectional(sliderVolume.valueProperty());

        //show muted speaker icon when the video is muted or normal speaker
        //when the video has volume
        sliderVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (mpVideo.getVolume() != 0.0) {
                    buttonVolume.setGraphic(ivVolume);
                    isMuted = false;
                } else {
                    buttonVolume.setGraphic(ivMute);
                    isMuted = true;
                }
            }
        });

        //set volume to 100%
        mpVideo.setVolume(1.0);


        buttonVolume.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(isMuted){
                    buttonVolume.setGraphic(ivVolume);
                    sliderVolume.setValue(1.0);
                    isMuted = false;
                } else{
                    buttonVolume.setGraphic(ivMute);
                    sliderVolume.setValue(0);
                    isMuted = true;
                }
            }
        });

        vBoxParent.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldScene, Scene newScene) {
                if(oldScene == null && newScene != null){
                    mvVideo.fitHeightProperty().bind(newScene.heightProperty().subtract(hBoxControls.heightProperty()));
                    mvVideo.fitWidthProperty().bind(newScene.widthProperty());
                }
            }
        });

        buttonFullscreen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               Button button = (Button) event.getSource();
               Stage stage = (Stage) button.getScene().getWindow();

               if(stage.isFullScreen()){
                   stage.setFullScreen(false);
                   buttonFullscreen.setGraphic(ivFullscreen);
               }
               else{
                   stage.setFullScreen(true);
                   buttonFullscreen.setGraphic(ivExitFS);
               }

               stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if(event.getCode() == KeyCode.ESCAPE){
                            buttonFullscreen.setGraphic(ivFullscreen);
                        }
                    }
               });
            }
        });


        //check how long the video is and change the time slider and time label accordingly
        mpVideo.totalDurationProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldDuration, Duration newDuration) {
                sliderTime.setMax(newDuration.toSeconds());
                labelTotalTime.setText(getTime(newDuration));
            }
        });


        //if the time slider is changed, forward or rewind the video to the new time
        sliderTime.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldTime, Number newTime) {
                double videoTime = mpVideo.getCurrentTime().toSeconds();
                if (Math.abs(videoTime - newTime.doubleValue()) > 0.2) {
                    mpVideo.seek(Duration.seconds(sliderTime.getValue()));
                }
            }
        });

        //as the video plays, move the time slider accordingly
        mpVideo.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldTime, Duration newTime) {
                if(!sliderTime.isValueChanging()){
                    sliderTime.setValue(newTime.toSeconds());
                }
            }
        });


        mpVideo.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                buttonPlayPause.setGraphic(ivReplay);
                isEndOfVideo = true;
            }
        });



        //bind the video current time with the time label
        bindCurrentTimeLabel();


    }

    public void bindCurrentTimeLabel(){
        labelCurrentTime.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getTime(mpVideo.getCurrentTime()) + " / ";
            }
        }, mpVideo.currentTimeProperty()));
    }

    public String getTime(Duration time){
        int hours = (int) time.toHours();
        int minutes = (int) time.toMinutes();
        int seconds = (int) time.toSeconds();

        if(seconds > 59)
            seconds = seconds % 60;
        if(minutes > 59)
            minutes = minutes % 60;

        if(hours > 0)
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        else
            return String.format("%02d:%02d", minutes, seconds);
    }
}