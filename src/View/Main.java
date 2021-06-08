package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.Optional;

public class Main extends Application {

    public static MyModel myModel;

    @Override
    public void start(Stage primaryStage) throws Exception{

        myModel = new MyModel();
        MyViewModel myViewModel = new MyViewModel(myModel);
        myModel.addObserver(myViewModel);

        FXMLLoader myViewFXMLLoader = new FXMLLoader();
        Parent myViewRoot = myViewFXMLLoader.load(getClass().getResource("MyView.fxml").openStream());
        primaryStage.setTitle("Maze Game");
        Scene scene = new Scene(myViewRoot);

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);


        MyViewController myViewController = myViewFXMLLoader.getController();
        myViewController.initialize(primaryStage,myViewModel);

        SetStageCloseEvent(primaryStage);
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
}