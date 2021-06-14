package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
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
        //primaryStage.setMaximized(true);
        //primaryStage.setResizable(false);

        //resize(scene);

        primaryStage.setScene(scene);


        MyViewController myViewController = myViewFXMLLoader.getController();
        myViewController.setResizeEvent(scene);
        myViewController.initialize(primaryStage,myViewModel);

        try {
            primaryStage.getIcons().add(new Image(new FileInputStream("resources\\images\\tomPic.JPG")));
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public void resize(Scene scene)  {

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

            }
        });
    }
}