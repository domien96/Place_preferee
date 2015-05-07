package preferee.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import preferee.GUI.ScreenManager;
import preferee.GUI.scenes.ScreenSeatsCompanion;
import preferee.data.access.Providers;
import preferee.data.access.ShowingDAO;

import java.io.IOException;
import java.net.URL;

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
        //Providers.createProvider(new URL("http://esari.ugent.be"));
        Providers.createTestProvider();
        FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("scenes/ScreenSeats.fxml"));
        ShowingDAO sdao = Providers.getProvider().getShowingDAO();
        loader.setController(ScreenSeatsCompanion.of(sdao.getShowing(1)));
        Scene scene = new Scene(loader.load());
         stage.setScene(scene);
         stage.show();
    }
}
