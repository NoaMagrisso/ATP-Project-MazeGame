package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LightOrDarkController extends AController{

    private String chooserCharacterPath;
    private String chooserEnvironmentPath;

    public void initialize(Stage stage, MyViewModel myViewModel, String chooserCharacterPath) {
        this.stage = stage;
        this.myViewModel = myViewModel;
        this.chooserCharacterPath = chooserCharacterPath;
    }

    public void Light(ActionEvent actionEvent) {
        chooserEnvironmentPath ="Light";
        chooseEnvironment(actionEvent);
    }

    public void Dark(ActionEvent actionEvent) {
        chooserEnvironmentPath ="Dark";
        chooseEnvironment(actionEvent);
    }

    public void chooseEnvironment(ActionEvent actionEvent) {

        try {
            FXMLLoader sizeOfMazeFXMLLoader = new FXMLLoader();
            Parent sizeOfMaze = sizeOfMazeFXMLLoader.load(getClass().getResource("SizeOfMaze.fxml").openStream());
            Scene sizeOfMazeScene = new Scene(sizeOfMaze);

            stage.setScene(sizeOfMazeScene);
//            stage.setX(rectangleSizes.getMinX());
//            stage.setY(rectangleSizes.getMinY());
//            stage.setWidth(rectangleSizes.getWidth());
//            stage.setHeight(rectangleSizes.getHeight());

//            SizeOfMazeController sizeOfMazeController = sizeOfMazeFXMLLoader.getController();
//            sizeOfMazeController.initialize(this.stage, this.myViewModel, chooserCharacterPath, chooserEnvironmentPath);
//            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
