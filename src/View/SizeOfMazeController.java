package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SizeOfMazeController extends AController{


    public TextField textField_mazeRows;
    public TextField textField_mazeCols;
    private String chooserCharacterPath;
    private String chooserEnvironmentPath;
    private int rows;
    private int cols;

    public void initialize(Stage stage, MyViewModel myViewModel, String chooserCharacterPath, String chooserEnvironmentPath) {
        this.stage = stage;
        this.myViewModel = myViewModel;
        this.chooserCharacterPath = chooserCharacterPath;
        this.chooserEnvironmentPath = chooserEnvironmentPath;
    }

    public void StartGame(ActionEvent actionEvent) {

        try {
            FXMLLoader gameFXMLLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
            Parent game = gameFXMLLoader.load();
            Scene gameScene = new Scene(game);

            stage.setScene(gameScene);
            stage.setX(rectangleSizes.getMinX());
            stage.setY(rectangleSizes.getMinY());
            stage.setWidth(rectangleSizes.getWidth());
            stage.setHeight(rectangleSizes.getHeight());

            GameController gameController = gameFXMLLoader.getController();
            gameController.initialize(this.stage, this.myViewModel, chooserCharacterPath, chooserEnvironmentPath, Integer.parseInt(textField_mazeRows.getText()), Integer.parseInt(textField_mazeCols.getText()));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
