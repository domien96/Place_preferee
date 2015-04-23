package preferee.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import preferee.GUI.ScreenManager;
import preferee.data.access.Providers;

import java.io.IOException;

/**
 * Sandbox voor allerlei testen.
 * Created by Domien on 14/04/2015.
 */
public class Sandbox extends Application{

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Providers.createTestProvider();
        Scene scene = new Scene(FXMLLoader.load(ScreenManager.class.getResource("Home.fxml")));
         stage.setScene(scene);
         stage.show();
    }
}
