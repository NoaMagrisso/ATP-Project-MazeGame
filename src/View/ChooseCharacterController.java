package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ChooseCharacterController extends AController{

    private String chooserCharacterPath = "";

    public void initialize(Stage stage, MyViewModel myViewModel, String chooserCharacterPath) {
        this.stage = stage;
        this.myViewModel = myViewModel;
        this.chooserCharacterPath = chooserCharacterPath;
    }

    public void A(ActionEvent actionEvent) {
        chooserCharacterPath = "A";
        chooseChar(actionEvent);
    }

    public void B(ActionEvent actionEvent) {
        chooserCharacterPath = "B";
        chooseChar(actionEvent);
    }

    public void C(ActionEvent actionEvent) {
        chooserCharacterPath = "C";
        chooseChar(actionEvent);
    }

    public void D(ActionEvent actionEvent) {
        chooserCharacterPath = "D";
        chooseChar(actionEvent);
    }

    public void chooseChar(ActionEvent actionEvent) {

        try {
            FXMLLoader lightOrDarkFXMLLoader = new FXMLLoader();
            Parent lightOrDark = lightOrDarkFXMLLoader.load(getClass().getResource("LightOrDark.fxml").openStream());
            Scene lightOrDarkScene = new Scene(lightOrDark);

            stage.setScene(lightOrDarkScene);
//            stage.setX(rectangleSizes.getMinX());
//            stage.setY(rectangleSizes.getMinY());
//            stage.setWidth(rectangleSizes.getWidth());
//            stage.setHeight(rectangleSizes.getHeight());

            LightOrDarkController lightOrDarkController = lightOrDarkFXMLLoader.getController();
            lightOrDarkController.initialize(this.stage, this.myViewModel, chooserCharacterPath);
            stage.setResizable(false);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
