package preferee.GUI.scenes;

import javafx.scene.control.Button;
import preferee.GUI.AbstractCompanion;
import preferee.GUI.ScreenController;
import preferee.GUI.ScreenName;
import preferee.data.access.Providers;

/**
 * Venster voor de initialisatie van de test provider.
 * Created by Domien Van Steendam on 12/04/2015.
 */
public class TestProviderSetupCompanion extends AbstractCompanion {

    public Button verdergaanButton;



    public void start() throws Exception {
        Providers.createTestProvider();
        ScreenController.nextScreen(ScreenName.Home, true);
    }
}
