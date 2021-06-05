package View;

import ViewModel.MyViewModel;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public abstract class AController {

    protected Stage stage;
    protected Rectangle2D rectangleSizes = Screen.getPrimary().getBounds();
    protected MyViewModel myViewModel;

}
