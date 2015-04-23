package preferee.GUI;

import preferee.data.access.Providers;

/**
 * Venster voor de initialisatie van de test provider.
 * Created by Domien Van Steendam on 12/04/2015.
 */
public class TestProviderSetupCompanion extends AbstractCompanion {

    public void goBack() throws Exception {
        ScreenManager.switchToScreen("Main");
    }

    public void start() throws Exception {
        Providers.createTestProvider();
        ScreenManager.switchToScreen("Home");
    }
}
