package preferee.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Houdt de meest voorkomende eigenschappen van een venster/stage bij.
 * Created by Domien on 23/04/2015.
 */
public class ScreenProperties {

    /**
     * De velden staan na initialisatie eenmaal vast.
     * Scene kan dus bij de constructor al gecrëerd worden.
     */
    public final Scene scene;
    public final String screenTitle;
    public final boolean resizable;

    /**
     *
     * @param sceneLocation : locatie van *.fxml
     * @param screenTitle : stage-title
     * @param resizable : vergroten/verkleinen is mogelijk?
     */
    public ScreenProperties(String sceneLocation, String screenTitle, boolean resizable) throws IOException {
        this.scene = buildScene(sceneLocation);
        this.screenTitle = screenTitle;
        this.resizable = resizable;
    }

    private Scene buildScene(String sceneLocation) throws IOException {
        Parent root = FXMLLoader.load(ScreenManager.class.
                getResource(sceneLocation));
        return new Scene(root);
    }
}
