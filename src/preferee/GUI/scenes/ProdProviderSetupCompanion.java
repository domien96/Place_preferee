package preferee.GUI.scenes;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import preferee.GUI.AbstractCompanion;
import preferee.GUI.ScreenController;
import preferee.GUI.ScreenName;
import preferee.Main;
import preferee.data.access.Providers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Venster voor de initialisatie van de productie provider.
 * Created by Domien on 14/04/2015.
 */
public class ProdProviderSetupCompanion extends AbstractCompanion {

    public TextField param_url;
    public Label errorLabel;

    public void initialize() {
        try {
            Properties properties = new Properties();
            properties.load(Main.class.getResourceAsStream("preferee.prop"));
            param_url.setText(properties.getProperty("defaultServerURL"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (param_url.getText().replace(" ","").isEmpty()) {
            param_url.getStyleClass().add("highlight-error");
            return;
        } else if ( isOffline()) {
            errorLabel.setText("Kon geen verbinding maken. \nControleer eventueel uw internetverbinding.");
            param_url.getStyleClass().add("highlight-error");
            return;
        }

        try {
            Providers.createProvider(new URL("http://" + param_url.getText()));
            ScreenController.nextScreen(ScreenName.Home, true);
        } catch (MalformedURLException e) {
            errorLabel.setText("Ongeldige URL! \n voorbeeld: esariu.ugent.be");
        }
    }

    /**
     * Controleert of er al dan niet internet verbinding is.
     */
    public boolean isOffline() {
        try {
            return "127.0.0.1".equals(InetAddress.getLocalHost().getHostAddress().toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return true;
        }
    }
}

