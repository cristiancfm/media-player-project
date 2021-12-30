package pro3.mediaplayerproject;

import javafx.application.Platform;
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
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class Controller implements Initializable {
    private final String GITHUB_LINK = "https://github.com/cristiancfm/media-player-project";

    @FXML
    private BorderPane borderPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem menuOpenFile;

    @FXML
    private MenuItem menuExit;

    @FXML
    private MenuItem menuAbout;

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


        //MENUS
        //opening files
        menuOpenFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Files", "*.*"),
                        new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.m4a", "*.aiff", "*.aif", "*.m3u8"),
                        new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.m4v", "*.fxm", "*.flv", "*.m3u8"));
                Stage stage = (Stage) borderPane.getScene().getWindow();
                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile != null) {
                    mpVideo.stop();

                    mediaVideo = new Media(selectedFile.toURI().toString());
                    mpVideo = new MediaPlayer(mediaVideo);
                    mvVideo.setMediaPlayer(mpVideo);

                    initializePlayerItems();
                }
            }
        });

        //exiting the application
        menuExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        //about window
        menuAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert aboutWindow = new Alert(Alert.AlertType.INFORMATION);
                aboutWindow.setTitle("About Media Player");
                aboutWindow.setHeaderText("Media Player v.1.0");

                FlowPane flowPane = new FlowPane();
                Label label = new Label("This application was coded by Cristian Ferreiro Montoiro");

                Hyperlink githubLink = new Hyperlink(GITHUB_LINK);
                githubLink.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Desktop.getDesktop().browse(new URI(GITHUB_LINK));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                flowPane.getChildren().addAll(label, githubLink);
                aboutWindow.getDialogPane().contentProperty().setValue(flowPane);

                aboutWindow.show();
            }
        });

        //END MENUS ----------------------------------------------


        //if the window size changes, resize the video accordingly
        borderPane.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldScene, Scene newScene) {
                if(oldScene == null && newScene != null){
                    mvVideo.fitHeightProperty().bind(newScene.heightProperty().subtract(hBoxControls.heightProperty()).subtract(menuBar.heightProperty()).subtract(30));
                    mvVideo.fitWidthProperty().bind(newScene.widthProperty());
                }
            }
        });


        initializePlayerItems();

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


    //initialize all the player controllers
    //this method is executed each time that the user changes the media source
    public void initializePlayerItems(){

        //PLAY-PAUSE BUTTON
        //change the button graphic if the video is playing, paused, or ended
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

        //VOLUME CONTROLS
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

        //volume button
        //change the button graphic if the video has sound or not
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


        //FULLSCREEN BUTTON
        //change the button graphic if the video is in fullscreen or normal
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

                //change the button graphic if the ESC key is pressed
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


        //LISTENERS
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
            public void changed(ObservableValue<? extends Duration> observable, Duration oldDuration, Duration newDuration) {
                if(!sliderTime.isValueChanging()){
                    sliderTime.setValue(newDuration.toSeconds());
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

        //---------------------------------------------

        //play the video
        mpVideo.play();

        //set volume to 100%
        mpVideo.setVolume(1.0);

    }
}