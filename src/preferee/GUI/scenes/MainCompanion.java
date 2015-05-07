package preferee.GUI.scenes;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import preferee.GUI.AbstractCompanion;
import preferee.GUI.ScreenController;
import preferee.GUI.ScreenName;

/**
 * Created by Domien Van Steendam on 12/04/2015.
 */
public class MainCompanion extends AbstractCompanion {
    public ToggleGroup providers;
    public RadioButton knop_testprovider, knop_prodprovider;

    public void nextWindow() throws Exception {
        ScreenName nextScreen = ((RadioButton) providers.getSelectedToggle()) . equals(knop_testprovider) ? ScreenName.TestProviderSetup : ScreenName.ProdProviderSetup;
        ScreenController.nextScreen(nextScreen, false);
    }

}
