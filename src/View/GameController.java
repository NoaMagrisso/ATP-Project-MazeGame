package View;

import ViewModel.MyViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class GameController extends AController implements Observer {

    public AnchorPane middlePane;
    private String chooserCharacterPath;
    private String chooserEnvironmentPath;
    private int rows;
    private int cols;
    @FXML
    private MazeDisplayer mazeDisplayer;
    private DoubleProperty myScale = new SimpleDoubleProperty(1.0);

    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();

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

    public void ShowSolution(ActionEvent actionEvent) {

        myViewModel.solveMaze();

    }

    public void update(Observable o, Object arg) {
        String change = (String) arg;
        switch (change){
            case "maze generated" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "maze solved" -> mazeSolved();
            default -> System.out.println("Not implemented change: " + change);
        }
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


}

































//new bellow =============================================================================================================










