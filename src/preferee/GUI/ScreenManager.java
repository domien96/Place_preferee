package preferee.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Regelt het uitzicht van 1 stage.
 *
 * Houdt de huidige en vorige scenes bij en beheert voor elk hun scene en venster-eigenschappen.
 * Regels ivm het aantal en soorten scenes worden hier gedefinieerd.
 * Ook nieuwe vensters worden hier toegevoegd.
 * Opmerking: In ons huidig programma mag steeds 1 hoofd-stage geopend zijn en 1 pop-up-venster/stage.
 *            Als dit gegeven ooit veranderd, moeten de aanpassingen enkel hier gebeuren.
 *
 * Created by Domien Van Steendam on 12/04/2015.
 */
public class ScreenManager {

    private Stage mainStage = new Stage(); // het hoofd-venster
    private Stack<Scene> previousScreens = new Stack<>(); // bevat de vorige scenes in volgorde van meest recenste naae oudste
    private Stack<ScreenProperties> previousScreenProperties = new Stack<>(); // synchroon met (@field_previousScreens)

    // hiermee kunnen de eigenschappen voor een bepaald venster opgeroepen worden adhv zijn interne naam.
    private static Map<ScreenName,ScreenProperties> screensProperties = new HashMap<>();

    // VENSTERS TOEVOEGEN DOE JE ENKEL EN ALLEEN HIER
    static {
        try {
            //vensters
            screensProperties.put(ScreenName.Main, new ScreenProperties("scenes/Main.fxml", "Welkom", false));
            screensProperties.put(ScreenName.TestProviderSetup, new ScreenProperties("scenes/TestProviderSetup.fxml", "Films", true));
            screensProperties.put(ScreenName.ProdProviderSetup, new ScreenProperties("scenes/ProdProviderSetup.fxml", "initialisatie", false));
            screensProperties.put(ScreenName.Home, new ScreenProperties("scenes/Home.fxml", "Home", false));
            screensProperties.put(ScreenName.ListMovies, new ScreenProperties("scenes/AllMovies.fxml", "Filmoverzicht", true));
            screensProperties.put(ScreenName.MovieInfo, new ScreenProperties("scenes/MovieInfo.fxml", "Film Informatie", true));
            screensProperties.put(ScreenName.MovieShowings, new ScreenProperties("scenes/MovieShowings.fxml", "Programmatie", false));
        } catch (Exception e) {
            e.printStackTrace();
            // programmeerfout, mag niet gebeuren
        }
    }

    public ScreenManager(ScreenName beginScreenName) {
        ScreenProperties props = screensProperties.get(beginScreenName);
        mainStage = createStage(props.sceneLocation);
        applyProperties(props);
        mainStage.centerOnScreen();
        mainStage.show();
    }

    /**
     * Creert een nieuwe stage a.d.h.v. een gegeven fxml-bestand.
     * @param fxml
     * @return
     * @throws Exception
     */
    private Stage createStage(String fxml) {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(ScreenManager.class.
                    getResource(fxml));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stage;
    }

    /**
     * Verandert het hoofd-venster naar de nieuwe layout.
     * @param screenName : interne key voor de stage
     * @throws Exception
     */
    public void changeScreen(ScreenName screenName) {
        ScreenProperties properties = screensProperties.get(screenName);
        // info in historie plaatsen
        previousScreens.push(mainStage.getScene());
        previousScreenProperties.push(properties);
        //properties toepassen
        applyProperties(properties);
        // nieuwe scene opbouwen en weergeven
        Scene newScene = buildScene(properties.sceneLocation);
        //
        mainStage.setScene(newScene);
        mainStage.centerOnScreen();
    }

    public void changeScreen(ScreenName screenName, AbstractCompanion companion) {
        ScreenProperties properties = screensProperties.get(screenName);
        // info in historie plaatsen
        previousScreens.push(mainStage.getScene());
        previousScreenProperties.push(properties);
        //properties toepassen
        applyProperties(properties);
        // nieuwe scene opbouwen en weergeven
        Scene newScene = buildScene(properties.sceneLocation,companion);
        //
        mainStage.setScene(newScene);
        mainStage.centerOnScreen();
    }

    /**
     * Pas de venster-eigenschappenvan een stage aan a.d.h.v. de gegeven screenproperties.
     * bv. titel, resizable, ...
     * @param properties
     */
    private void applyProperties(ScreenProperties properties) {
        mainStage.setTitle("Place Préférée - " + properties.screenTitle);
        mainStage.setResizable(properties.resizable);
    }

    /**
     * Keert terug naar scene van oorsprong van deze scene.
     */
    public void goToPreviousScreen() {
        mainStage.setScene(previousScreens.pop());
        applyProperties(previousScreenProperties.pop());
        mainStage.centerOnScreen();
    }

    /**
     * Bouwt scene met controller meegeven in het fxml-bestand.
     * @param fxmlLocation
     * @return
     */
    private Scene buildScene(String fxmlLocation) {
        Parent root = null;
        try {
            root = FXMLLoader.load(ScreenManager.class.
                    getResource(fxmlLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Scene(root);
    }

    /**
     * bouwt scene en zet meegegven companion als de controller van de scene.
     * @param fxmlLocation
     * @param companion
     * @return
     */
    private Scene buildScene(String fxmlLocation, AbstractCompanion companion) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.
                    getResource(fxmlLocation));
            loader.setController(companion);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Scene(root);
    }

    /**
     * Voornamelijk voor geheugen niet te belasten.
     */
    public void resetScreenHistory() {
        previousScreens = new Stack<>();
        previousScreenProperties = new Stack<>();
    }
}


/**
 * Houdt de meest voorkomende eigenschappen van een venster/stage bij.
 * Created by Domien on 23/04/2015.
 */
 class ScreenProperties {

    /**
     * De velden staan na initialisatie eenmaal vast.
     * Scene kan dus bij de constructor al gecrëerd worden.
     */
    public final String sceneLocation;
    public final String screenTitle;
    public final boolean resizable;

    /**
     *
     * @param sceneLocation : locatie van *.fxml
     * @param screenTitle : stage-title
     * @param resizable : vergroten/verkleinen is mogelijk?
     */
    public ScreenProperties(String sceneLocation, String screenTitle, boolean resizable) {
        this.sceneLocation = sceneLocation;
        this.screenTitle = screenTitle;
        this.resizable = resizable;
    }
}
