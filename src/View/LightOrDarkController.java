package View;

import ViewModel.MyViewModel;
import javafx.stage.Stage;

public class LightOrDarkController extends AController{

    private String chooserCharacterPath;

    public void initialize(Stage stage, String chooserCharacterPath) {
        this.stage = stage;
        //this.myViewModel = myViewModel;
        this.chooserCharacterPath = chooserCharacterPath;

    }

}
