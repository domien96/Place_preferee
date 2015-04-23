package preferee.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * Created by Domien Van Steendam on 12/04/2015.
 */
public class MainCompanion extends AbstractCompanion {
    public ToggleGroup providers;
    public RadioButton knop_testprovider, knop_prodprovider;

    public void nextWindow() throws Exception{
        ScreenManager.switchToScreen(((RadioButton) providers.getSelectedToggle()).getId());
        //ScreenManager.switchToScreen("confirmation_initialisation"); todo
    }

}
