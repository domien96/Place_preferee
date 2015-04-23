package preferee.GUI;

import javafx.event.ActionEvent;
import preferee.data.access.Providers;

import java.net.URL;

/**
 * Created by Domien on 15/04/2015.
 */
public class confirmation_initialisationCompanion {

    public void confirmed() throws Exception {
        Providers.createProvider(new URL("http://" + "esari.ugent.be"));
        ScreenManager.switchToScreen("Home");
    }

    public void goBack(ActionEvent actionEvent) {
    }
}
