package preferee;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import preferee.data.access.DataAccessProvider;
import preferee.data.access.Providers;

import java.net.URL;

/**
 * JavaFX-hoofdprogramma waarmee de toepassing wordt opgestart,
 *
 * Created by domien Van Steendam
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Dit zijn de opdrachtlijnparameters:
        Parameters parameters = getParameters();

        DataAccessProvider provider;
        if (parameters.getUnnamed().size() == 0) {
            provider = Providers.createTestProvider();
        } else { // >= 1
            provider = Providers.createProvider(new URL("http://" +  parameters.getUnnamed().get(0)));
        }

        stage.setTitle("Place Préférée");
        stage.setScene(new Scene(new Label("Hello world")));
        stage.show();
    }

    /**
     * Wanneer het hoofdprogramma geen enkel argument heeft, wordt de 'test'provider
     * gebruikt (zie {@link Providers}. Is er één argument, dan gebruikt het programma
     * de 'productie'provider en stelt het argument de URL voor van de server die moet
     * gecontacteerd worden.
     */
    public static void main(String[] args) { launch(args);
        // na de uitvoering de provider sluiten
        try {
            Providers.provider.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}