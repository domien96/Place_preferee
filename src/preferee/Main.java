package preferee;

import javafx.application.Application;
import javafx.stage.Stage;
import preferee.GUI.ScreenController;
import preferee.GUI.ScreenName;
import preferee.data.access.Providers;

/**
 * JavaFX-hoofdprogramma waarmee de toepassing wordt opgestart,
 *
 * Created by Domien Van Steendam
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
         ScreenController.init(ScreenName.Main);
    }

    /**
     * Wanneer het hoofdprogramma geen enkel argument heeft, wordt de 'test'provider
     * gebruikt (zie {@link Providers}. Is er één argument, dan gebruikt het programma
     * de 'productie'provider en stelt het argument de URL voor van de server die moet
     * gecontacteerd worden.
     *
     * UPDATE: De onderscheiding in providers wordt nu in de gui geregeld.
     */
    public static void main(String[] args) { launch(args);
        // na de uitvoering de provider sluiten
        try {
            Providers.getProvider().close();
        } catch (NullPointerException e) {
            // provider is niet geinitialiseerd
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}