package preferee.GUI.scenes;

import preferee.GUI.AbstractCompanion;
import preferee.GUI.ScreenController;
import preferee.GUI.ScreenName;

/**
 * Scherm na setup
 * Created by Domien on 6/05/2015.
 */
public class HomeCompanion extends AbstractCompanion {

    public void goToShowings(){
        ScreenController.nextScreen(ScreenName.MovieInfo, false);
    }

    public void goToMovies(){
        ScreenController.nextScreen(ScreenName.ListMovies, false);
    }
}
