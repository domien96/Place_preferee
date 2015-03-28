package preferee;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import preferee.data.access.DataAccessProvider;
import preferee.data.access.Providers;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * JavaFX-hoofdprogramma waarmee de toepassing wordt opgestart,
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
            provider = Providers.createProvider(new URL(parameters.getUnnamed().get(0)));
        }

        stage.setTitle("Place Préférée");
        stage.setScene(new Scene(new Label("Hello world")));
        stage.show();

        //TODO close provider
    }

    /**
     * Wanneer het hoofdprogramma geen enkel argument heeft, wordt de 'test'provider
     * gebruikt (zie {@link Providers}. Is er één argument, dan gebruikt het programma
     * de 'productie'provider en stelt het argument de URL voor van de server die moet
     * gecontacteerd worden.
     */
    public static void main(String[] args) { launch(args);
    }

}

//link: http://esari.ugent.be