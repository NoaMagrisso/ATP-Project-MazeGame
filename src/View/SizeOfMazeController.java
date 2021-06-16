package View;

import Server.Configurations;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class SizeOfMazeController extends AController{

    //-fx-background-insets: 0 0 -1 0, 0, 1, 2;-fx-padding: 0.333333em 0.666667em 0.333333em 0.666667em; /* 4 8 4 8 */
    public TextField textField_mazeRows;
    public TextField textField_mazeCols;
    public Menu Options;
    public Menu Properties;
    public CheckMenuItem DepthFirstSearch;
    public CheckMenuItem BreathFirstSearch;
    public CheckMenuItem BestFirstSearch;
    public CheckMenuItem EmptyMazeGenerator;
    public CheckMenuItem SimpleMazeGenerator;
    public CheckMenuItem MyMazeGenerator;
    public MenuItem threadPool;
    public RadioMenuItem menu_Options_Mute;
    public ImageView imageAbout;
    public MazeDisplayer ImageDisplayer;
    public Label labelRow;
    public Label labelCol;
    public ToggleButton buttonStartGame;
    private String chooserCharacterPath;
    private String chooserEnvironmentPath;
    private int rows;
    private int cols;

    public void initialize(Stage stage, MyViewModel myViewModel, String chooserCharacterPath, String chooserEnvironmentPath, MediaPlayer startMusic) throws FileNotFoundException {
        this.stage = stage;
        this.myViewModel = myViewModel;
        this.chooserCharacterPath = chooserCharacterPath;
        this.chooserEnvironmentPath = chooserEnvironmentPath;
        this.startMusic = startMusic;
    }

    public void StartGame(ActionEvent actionEvent) {

        try {
            FXMLLoader gameFXMLLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
            Parent game = gameFXMLLoader.load();
            Scene gameScene = new Scene(game);

            stage.setScene(gameScene);
//            stage.setX(rectangleSizes.getMinX());
//            stage.setY(rectangleSizes.getMinY());
//            stage.setWidth(rectangleSizes.getWidth());
//            stage.setHeight(rectangleSizes.getHeight());

            GameController gameController = gameFXMLLoader.getController();
            gameController.initialize(this.stage, this.myViewModel, chooserCharacterPath, chooserEnvironmentPath, Integer.parseInt(textField_mazeRows.getText()), Integer.parseInt(textField_mazeCols.getText()), this.startMusic);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void solverDepthFirstSearch(ActionEvent actionEvent) {
        BestFirstSearch.setSelected(false);
        BreathFirstSearch.setSelected(false);
        Configurations.getConfigurations().setMazeSearchingAlgorithm("DepthFirstSearch");
    }

    public void solverBreadthFirstSearch(ActionEvent actionEvent) {
        BestFirstSearch.setSelected(false);
        DepthFirstSearch.setSelected(false);
        Configurations.getConfigurations().setMazeSearchingAlgorithm("BreadthFirstSearch");
    }

    public void solverBestFirstSearch(ActionEvent actionEvent) {
        BreathFirstSearch.setSelected(false);
        DepthFirstSearch.setSelected(false);
        Configurations.getConfigurations().setMazeSearchingAlgorithm("BestFirstSearch");
    }

    public void generatorEmptyMazeGenerator(ActionEvent actionEvent) {
        SimpleMazeGenerator.setSelected(false);
        MyMazeGenerator.setSelected(false);
        Configurations.getConfigurations().setMazeGeneratingAlgorithm("EmptyMazeGenerator");
    }

    public void generatorSimpleMazeGenerator(ActionEvent actionEvent) {
        EmptyMazeGenerator.setSelected(false);
        MyMazeGenerator.setSelected(false);
        Configurations.getConfigurations().setMazeGeneratingAlgorithm("SimpleMazeGenerator");
    }

    public void generatorMyMazeGenerator(ActionEvent actionEvent) {
        EmptyMazeGenerator.setSelected(false);
        SimpleMazeGenerator.setSelected(false);
        Configurations.getConfigurations().setMazeGeneratingAlgorithm("MyMazeGenerator");
    }

    public void ThreadPool(ActionEvent actionEvent) {
        TextField text = new TextField();
        text.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Configurations.getConfigurations().setThreadPoolSize(text.getText());
            }
        });
        threadPool.setGraphic(text);
    }

    public void ShowSolution(ActionEvent actionEvent) {
    }

    public void Reset(ActionEvent actionEvent) {
    }

    public void About(ActionEvent actionEvent) {
        try {
            Stage newStage = new Stage();
            newStage.setTitle("About");
            FXMLLoader myViewFXMLLoader = new FXMLLoader();
            Parent myViewRoot = myViewFXMLLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(myViewRoot, 550, 400);
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);

            //newStage.setMinWidth();
            newStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
