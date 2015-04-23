package preferee.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Houdt de actieve screens bij en beheert hun scene.
 * Regels ivm het aantal en soorten screens worden hier gedefinieerd.
 * Opmerking: In ons huidig programma mag steeds 1 hoofd-stage geopend zijn en eventueel een bevestigings-venster/stage.
 *            Als dit gegeven ooit veranderd, moeten de aanpassingen enkel hier gebeuren.
 * Created by Domien Van Steendam on 12/04/2015.
 */
public class ScreenManager {

    public static Stage mainStage = new Stage(); // het hoofd-venster

    // hiermee kunnen de eigenschappen voor een bepaald venster opgeroepen worden adhv zijn interne naam.
    private static Map<String,ScreenProperties> screensProperties = new HashMap<>();

    static {
        try {
            screensProperties.put("Main", new ScreenProperties("Main.fxml", "Welkom", false));
            screensProperties.put("TestProviderSetup", new ScreenProperties("TestProviderSetup.fxml", "Films", true));
            screensProperties.put("ProdProviderSetup", new ScreenProperties("ProdProviderSetup.fxml", "initialisatie", false));
            screensProperties.put("confirmation_initialisation", new ScreenProperties("confirmation_initialisation.fxml", "Bent u zeker?", false));
            screensProperties.put("Home", new ScreenProperties("Home.fxml", "Filmoverzicht", true));
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // programmeerfout, mag niet gebeuren
        }
    }



    /**
     * Verandert het hoofd-venster naar de nieuwe layout.
     * @param screenName : interne key voor de stage
     * @throws Exception
     */
    public static void switchToScreen(String screenName) throws Exception{
        ScreenProperties properties = screensProperties.get(screenName);
        //properties toepassen
        mainStage.setScene(properties.scene);
        mainStage.setTitle(properties.screenTitle);
        mainStage.setResizable(properties.resizable);
    }

    /**
     * Open 1 nieuw bevestigings-venster. Deze blokkeert invoer op de oude vensters.
     */
    public static void userConfirms() throws Exception {
        switchToScreen("confirmation_initialisation");
        mainStage.initModality(Modality.WINDOW_MODAL);
        mainStage.isAlwaysOnTop();
    }

    /**
     * Creert een nieuwe stage a.d.h.v. een gegeven fxml-bestand.
     * @param fxml
     * @return
     * @throws Exception
     */
    private static Stage createStage(String fxml, String title, boolean resizable) throws Exception {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(ScreenManager.class.
                getResource(fxml));
        stage.setTitle("Place Préférée - " + title);
        stage.setResizable(resizable);
        stage.setScene(new Scene(root));
        return stage;
    }
}

