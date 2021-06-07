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

        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);


        MyViewController myViewController = myViewFXMLLoader.getController();
        myViewController.initialize(primaryStage,myViewModel);

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}