package View;

import Server.Configurations;
import ViewModel.MyViewModel;
import algorithms.search.BreadthFirstSearch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GameController extends AController implements Observer {

    public AnchorPane middlePane;
    public Menu Options;
    public Menu Properties;
    public MenuItem threadPool;
    private String chooserCharacterPath;
    private String chooserEnvironmentPath;
    private int rows;
    private int cols;
    @FXML
    public javafx.scene.control.CheckMenuItem BestFirstSearch;
    public javafx.scene.control.CheckMenuItem BreathFirstSearch;
    public javafx.scene.control.CheckMenuItem DepthFirstSearch;
    public javafx.scene.control.CheckMenuItem EmptyMazeGenerator;
    public javafx.scene.control.CheckMenuItem SimpleMazeGenerator;
    public javafx.scene.control.CheckMenuItem MyMazeGenerator;
    @FXML
    private MazeDisplayer mazeDisplayer;
    private DoubleProperty myScale = new SimpleDoubleProperty(1.0);

    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();

    public static int numSaveMaze = 1;

    public void initialize(Stage stage, MyViewModel myViewModel, String chooserCharacterPath, String chooserEnvironmentPath, int rows, int cols) {
        this.stage = stage;
        this.myViewModel = myViewModel;
        this.chooserCharacterPath = chooserCharacterPath;
        this.chooserEnvironmentPath = chooserEnvironmentPath;
        this.rows = rows;
        this.cols = cols;

        myViewModel.addObserver(this);

        System.out.println(this.rows + " " + this.cols);


        myViewModel.generateMaze(this.rows, this.cols);

//        if (myViewModel.getMaze() == null) {
//            System.out.println("hhhhhhhhhhhhhh");
//            myViewModel.generateMaze(this.rows, this.cols);
//        }

//        mazeDisplayer.widthProperty().bind(middlePane.widthProperty());
//        mazeDisplayer.heightProperty().bind(middlePane.heightProperty());
//        mazeDisplayer.cellHeight.bind(middlePane.heightProperty().divide(this.rows));
//        mazeDisplayer.cellWidth.bind(middlePane.widthProperty().divide(this.cols));
        mazeDisplayer.drawMaze(myViewModel.getMaze());

//        middlePane.scaleXProperty().bind(myScale);
//        middlePane.scaleYProperty().bind(myScale);
    }

    public void Home(ActionEvent actionEvent) {

        try {
            FXMLLoader myViewFXMLLoader = new FXMLLoader();
            Parent myView = myViewFXMLLoader.load(getClass().getResource("MyView.fxml").openStream());
            Scene myViewScene = new Scene(myView);

            stage.setScene(myViewScene);
//            stage.setX(rectangleSizes.getMinX());
//            stage.setY(rectangleSizes.getMinY());
//            stage.setWidth(rectangleSizes.getWidth());
//            stage.setHeight(rectangleSizes.getHeight());

            MyViewController myViewController = myViewFXMLLoader.getController();
            myViewController.initialize(this.stage, this.myViewModel);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void Reset(ActionEvent actionEvent) {

        setPlayerPosition(0,0);
        mazeDisplayer.setSolution(null);

        System.out.println("reset the maze...");

    }

    public void ShowSolution(ActionEvent actionEvent) { //TODO to show a solution from where I am

        myViewModel.solveMaze();

    }

    public void update(Observable o, Object arg) {
        String change = (String) arg;
        switch (change) {
            case "maze generated" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "maze solved" -> mazeSolved();
            case "file loaded" -> fileLoaded();
            default -> System.out.println("Not implemented change: " + change);
        }
    }

    private void fileLoaded() {
        mazeDisplayer.setNullSolution();
        mazeDisplayer.setPlayerPositionOnly(myViewModel.getPlayerRow(), myViewModel.getPlayerCol());
        mazeDisplayer.drawMaze(myViewModel.getMaze());
    }

    private void mazeSolved() {
        mazeDisplayer.setSolution(myViewModel.getSolution());
    }

    public void generateMaze(ActionEvent actionEvent) {
    }

    public void solveMaze(ActionEvent actionEvent) {
    }

    public void openFile(ActionEvent actionEvent) {
    }

    public void KeyPressed(KeyEvent keyEvent) {
        myViewModel.updatePlayerLocation(keyEvent);
        keyEvent.consume();
    }

    private void playerMoved() {
        setPlayerPosition(myViewModel.getPlayerRow(), myViewModel.getPlayerCol());
    }


    private void mazeGenerated() {
        mazeDisplayer.drawMaze(myViewModel.getMaze());
    }

    public void setPlayerPosition(int row, int col){
        mazeDisplayer.setPlayerPosition(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);
    }

    public void setUpdatePlayerRow(int updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow + "");
    }

    public void setUpdatePlayerCol(int updatePlayerCol) {
        this.updatePlayerCol.set(updatePlayerCol + "");
    }

    public void New() throws IOException { //TODO to improve?
        this.Home(null);
    }

    public void Save() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Maze Files", "*.maze")
        );
        fileChooser.setInitialFileName("MyMaze" + numSaveMaze);
        File saveFile = fileChooser.showSaveDialog(this.stage);
        if (saveFile != null) {
            myViewModel.SaveGame(saveFile);
        }
        numSaveMaze++;
    }

    public void Load() throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Maze Files", "*.maze")
        );
        File loadFile = fileChooser.showOpenDialog(this.stage);
        if (loadFile != null) {
            myViewModel.loadGame(loadFile);
        } else {
        }
    }


    public void solverDepthFirstSearch(ActionEvent actionEvent) {
        Configurations.getConfigurations().setMazeSearchingAlgorithm("DepthFirstSearch");
    }

    public void solverBreadthFirstSearch(ActionEvent actionEvent) {
        Configurations.getConfigurations().setMazeSearchingAlgorithm("BreadthFirstSearch");
    }

    public void solverBestFirstSearch(ActionEvent actionEvent) {
        Configurations.getConfigurations().setMazeSearchingAlgorithm("BestFirstSearch");
    }

    public void generatorEmptyMazeGenerator(ActionEvent actionEvent) {
        Configurations.getConfigurations().setMazeGeneratingAlgorithm("EmptyMazeGenerator");
    }

    public void generatorSimpleMazeGenerator(ActionEvent actionEvent) {
        Configurations.getConfigurations().setMazeGeneratingAlgorithm("SimpleMazeGenerator");
    }

    public void generatorMyMazeGenerator(ActionEvent actionEvent) {
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
}