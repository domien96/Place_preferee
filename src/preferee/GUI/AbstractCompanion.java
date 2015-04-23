package preferee.GUI;

import javafx.application.Platform;

/**
 * Created by Domien Van Steendam on 12/04/2015.
 */
public abstract class AbstractCompanion {

    public void exitApplication() {
        Platform.exit();
    }
}
