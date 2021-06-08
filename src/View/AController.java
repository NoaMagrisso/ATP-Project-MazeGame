package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

}
