package pro3.mediaplayerproject;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);


        stage.getIcons().add(new Image(new File("src/main/resources/pro3/mediaplayerproject/icons/film.png").toURI().toString()));
        stage.setTitle("Media Player");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

//public final class SongModel {
//    private static final String DEFAULT_IMG_URL =
//            SongModel.class.getResource("resources/defaultAlbum.png").toString();
//    private static final Image DEFAULT_ALBUM_COVER =
//            new Image(DEFAULT_IMG_URL);
//    private final StringProperty album =
//            new SimpleStringProperty(this, "album");
//    private final StringProperty artist =
//            new SimpleStringProperty(this,"artist");
//    private final StringProperty title =
//            new SimpleStringProperty(this, "title");
//    private final StringProperty year =
//            new SimpleStringProperty(this, "year");
//    private final ObjectProperty<Image> albumCover =
//            new SimpleObjectProperty<>(this, "albumCover");
//    private final ReadOnlyObjectWrapper<MediaPlayer> mediaPlayer =
//            new ReadOnlyObjectWrapper<>(this, "mediaPlayer");
//    public SongModel() {
//        resetProperties();
//    }
//    public void setURL(String url) {
//        if (mediaPlayer.get() != null) {
//            mediaPlayer.get().stop();
//        }
//        initializeMedia(url);
//    }
//    public String getAlbum() { return album.get(); }
//    public void setAlbum(String value) { album.set(value); }
//    public StringProperty albumProperty() { return album; }
//    // The three methods above are repeated for the artist, title,
//    // year, and albumCover properties...
//    public MediaPlayer getMediaPlayer() { return mediaPlayer.get(); }
//    public ReadOnlyObjectProperty<MediaPlayer> mediaPlayerProperty() {
//        return mediaPlayer.getReadOnlyProperty();
//    }
//    private void resetProperties() {
//        setArtist("");
//        setAlbum("");
//        setTitle("");
//        setYear("");
//        setAlbumCover(DEFAULT_ALBUM_COVER);
//    }
//    private void initializeMedia(String url) {
//        resetProperties();
//        try {
//            final Media media = new Media(url);
//            media.getMetadata().addListener((Change<? extends String, ? extends Object> ch) -> {
//                if (ch.wasAdded()) {
//                    handleMetadata(ch.getKey(), ch.getValueAdded());
//                }
//            });
//            mediaPlayer.setValue(new MediaPlayer(media));
//            mediaPlayer.get().setOnError(() -> {
//                String errorMessage = mediaPlayer.get().getError().getMessage();
//                // Handle errors during playback
//                System.out.println("MediaPlayer Error: " + errorMessage);
//            });
//        } catch (RuntimeException re) {
//            // Handle construction errors
//            System.out.println("Caught Exception: " + re.getMessage());
//        }
//    }
//    private void handleMetadata(String key, Object value) {
//        switch (key) {
//            case "album":
//                setAlbum(value.toString());
//                break;
//            case "artist":
//                setArtist(value.toString());
//                break;
//            case "title":
//                setTitle(value.toString());
//                break;
//            case "year":
//                setYear(value.toString());
//                break;
//            case "image":
//                setAlbumCover((Image)value);
//                break;
//        }
//    }
//}