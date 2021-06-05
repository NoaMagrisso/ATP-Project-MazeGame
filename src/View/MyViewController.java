package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MyViewController extends AController{

    private MyViewModel myViewModel;

    public void initialize(Stage primaryStage) {
        this.stage = primaryStage;
        //this.myViewModel = viewModel;
    }

    public void Start(ActionEvent actionEvent) {
        System.out.println("here");
        try {
            FXMLLoader chooseCharacterFXMLLoader = new FXMLLoader();
            Parent mazeChar = chooseCharacterFXMLLoader.load(getClass().getResource("chooseCharacter.fxml").openStream());
            Scene mazeCharScene = new Scene(mazeChar);

            stage.setScene(mazeCharScene);
            stage.setX(rectangleSizes.getMinX());
            stage.setY(rectangleSizes.getMinY());
            stage.setWidth(rectangleSizes.getWidth());
            stage.setHeight(rectangleSizes.getHeight());

            ChooseCharacterController chooseCharacterController = chooseCharacterFXMLLoader.getController();
            chooseCharacterController.initialize(this.stage, chooseCharacterController.toString());
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
