package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;



public class Main extends Application {

    public static MyModel myModel;
    public static Stage stageMain;
    public static Scene scene;
    public static MediaPlayer startMusic;
    public static MyViewController myViewController;
    public static MyViewModel myViewModel;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Media media = new Media(new File("resources\\music\\musicplayer.mp3").toURI().toString());
        startMusic = new MediaPlayer(media);
        startMusic.setVolume(0.2);
        startMusic.setAutoPlay(true);
        startMusic.setCycleCount(MediaPlayer.INDEFINITE);

        //-------------------------------------------------

//        Media media = new Media(new File("resources\\video\\finalVideo.mp4").toURI().toString());
//
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        MediaView mediaView = new MediaView(mediaPlayer);
//        mediaPlayer.setAutoPlay(true);
//
//        //setting group and scene
//        Group root = new Group();
//        root.getChildren().add(mediaView);
//        Scene scene2 = new Scene(root,500,400);
//        primaryStage.setScene(scene2);
//        primaryStage.setTitle("Playing video");
//        primaryStage.show();
//        primaryStage.setMaximized(true);
//
//        javafx.scene.media.MediaPlayer player = new javafx.scene.media.MediaPlayer(media);
//        mediaPlayer.setOnEndOfMedia(new Runnable() {
//            @Override
//            public void run() {
//                mediaPlayer.stop();
//                primaryStage.close();
//            }
//        });

//        while (mediaPlayer.getOnEndOfMedia() != null && mediaPlayer.getStatus() != MediaPlayer.Status.STOPPED) {
//            System.out.println("noa");
//        }

        //-------------------------------------------------

        myModel = new MyModel();
        myViewModel = new MyViewModel(myModel);
        myModel.addObserver(myViewModel);


        FXMLLoader myViewFXMLLoader = new FXMLLoader();
        Parent myViewRoot = myViewFXMLLoader.load(getClass().getResource("MyView.fxml").openStream());
       // myViewRoot.getStylesheets().add(getClass().getResource("MyView.css").toExternalForm());
        primaryStage.setTitle("Maze Game");
<<<<<<< HEAD
        Scene scene = new Scene(myViewRoot);
        scene.getStylesheets().add(getClass().getResource("MyView.css").toExternalForm());
=======
        scene = new Scene(myViewRoot, 600, 400);
>>>>>>> NoaBranch
        //primaryStage.setMaximized(true);
        //primaryStage.setResizable(false);
//
//        //resize(scene);
//
        primaryStage.setScene(scene);


        myViewController = myViewFXMLLoader.getController();
        myViewController.setResizeEvent(scene);
        //myViewController.initialize(primaryStage,myViewModel, startMusic);
        myViewController.initialize(primaryStage,myViewModel, null);


        try {
            primaryStage.getIcons().add(new Image(new FileInputStream("resources\\images\\pack516.jpg")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        SetStageCloseEvent(primaryStage);
        stageMain = primaryStage;
        primaryStage.show();

    }

    public static void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("You can still stay here... Are you sure that you want to exit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    myModel.stop();
                } else {
                    windowEvent.consume();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void resize(Scene scene)  {

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

            }
        });
    }

    public static void restart() {
        //stageMain.close();
        startMusic.setVolume(0.2);
        stageMain.setScene(scene);
        myViewController.initialize(stageMain,myViewModel, null);
        SetStageCloseEvent(stageMain);
        stageMain.setMaximized(true);
        stageMain.show();
    }
}