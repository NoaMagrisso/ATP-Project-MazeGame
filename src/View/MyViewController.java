package View;

import ViewModel.MyViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MyViewController extends AController{

    public Button button;
    public AnchorPane pane;
    private MyViewModel myViewModel;

    public void initialize(Stage primaryStage, MyViewModel viewModel, MediaPlayer startMusic) {
        this.stage = primaryStage;
        this.myViewModel = viewModel;
        this.startMusic = startMusic;

        //button.prefHeightProperty().bind(pane.heightProperty().divide(10));
        //button.prefWidthProperty().bind(pane.widthProperty().divide(10));

    }



    public void Start(ActionEvent actionEvent) {
        try {
//            FXMLLoader chooseCharacterFXMLLoader = new FXMLLoader();
//            //Parent mazeChar = chooseCharacterFXMLLoader.load(getClass().getResource("chooseCharacter.fxml").openStream());
//            Parent mazeChar = chooseCharacterFXMLLoader.load(getClass().getResource("SizeOfMaze.fxml").openStream());
//            Scene mazeCharScene = new Scene(mazeChar);
//
//
//            //button.prefHeightProperty().bind(Bindings.divide(this.stage.widthProperty(), 10.0));
//            //button.prefWidthProperty().bind(Bindings.divide(this.stage.widthProperty(), 10.0));
//
//
////            stage.setScene(mazeCharScene);
////            stage.setX(rectangleSizes.getMinX());
////            stage.setY(rectangleSizes.getMinY());
////            stage.setWidth(rectangleSizes.getWidth());
////            stage.setHeight(rectangleSizes.getHeight());
//
//
//            //button. bind(pane.heightProperty().multiply(2));
//            //button.minHeightProperty().bind(pane.widthProperty().divide(2));
//
//            ChooseCharacterController chooseCharacterController = chooseCharacterFXMLLoader.getController();
//            chooseCharacterController.initialize(this.stage, this.myViewModel, chooseCharacterController.toString());
//            stage.setResizable(false);
//            stage.show();


            FXMLLoader sizeOfMazeFXMLLoader = new FXMLLoader();
            Parent sizeOfMaze = sizeOfMazeFXMLLoader.load(getClass().getResource("SizeOfMaze.fxml").openStream());
            Scene sizeOfMazeScene = new Scene(sizeOfMaze);
            sizeOfMazeScene.getStylesheets().add(getClass().getResource("SizeOfMaze.css").toExternalForm());
            stage.setScene(sizeOfMazeScene);
//            stage.setX(rectangleSizes.getMinX());
//            stage.setY(rectangleSizes.getMinY());
//            stage.setWidth(rectangleSizes.getWidth());
//            stage.setHeight(rectangleSizes.getHeight());

            SizeOfMazeController sizeOfMazeController = sizeOfMazeFXMLLoader.getController();
            sizeOfMazeController.initialize(this.stage, this.myViewModel, null, null, this.startMusic);
            //stage.setResizable(false);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setResizeEvent(Scene scene) {
        //button.prefWidthProperty().bind(pane.widthProperty());
        //button.prefHeightProperty().bind(pane.heightProperty());
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            button.setPrefWidth(scene.getWidth()*0.17);
           // button.setMaxHeight(50);
           // button.setMaxWidth(50);
//            button.setLayoutX(500);
//            button.setLayoutY(350);

        });


        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            button.setPrefHeight(scene.getHeight()*0.0725);

        });
//        button.setTranslateX(10);
//        button.setTranslateY(20);
        button.setLayoutX(button.getLayoutX()*1.05);
        button.setLayoutY(button.getLayoutY()*1.05);
    }



}
