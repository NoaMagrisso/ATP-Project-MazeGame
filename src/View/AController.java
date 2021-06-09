package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public abstract class AController {

    protected Stage stage;
    protected Rectangle2D rectangleSizes = Screen.getPrimary().getBounds();
    protected MyViewModel myViewModel;

    public void Home(ActionEvent actionEvent) {
        try {
            FXMLLoader myViewFXMLLoader = new FXMLLoader(getClass().getResource("MyView.fxml"));
            Parent myView = myViewFXMLLoader.load();
            Scene myViewScene = new Scene(myView);

            stage.setScene(myViewScene);

            MyViewController myViewController = myViewFXMLLoader.getController();
            myViewController.initialize(this.stage, this.myViewModel);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SetStageCloseEvent(Stage stage, MyModel myModel) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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

}
