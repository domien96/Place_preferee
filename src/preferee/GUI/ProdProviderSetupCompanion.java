package preferee.GUI;

import javafx.scene.control.TextField;
import preferee.Main;
import preferee.data.access.Providers;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Venster voor de initialisatie van de productie provider.
 * Created by Domien on 14/04/2015.
 */
public class ProdProviderSetupCompanion {

    public TextField param_url;

    public void initialize() {
        try {
            Properties properties = new Properties();
            properties.load(Main.class.getResourceAsStream("preferee.properties"));
            param_url.setText(properties.getProperty("defaultServerURL"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goBack() throws Exception{
        ScreenManager.switchToScreen("Main");
    }

    public void start() throws Exception {
        Providers.createProvider(new URL("http://" +  param_url.getText()));
        ScreenManager.switchToScreen("Home");
    }
}
