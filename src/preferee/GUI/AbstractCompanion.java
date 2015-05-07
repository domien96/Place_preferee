package preferee.GUI;

import javafx.application.Platform;

/**
 * Bovenklasse van alle companions
 * Created by Domien Van Steendam on 12/04/2015.
 */
public abstract class AbstractCompanion {

    /**
     * Sommige companion bezitten een parameterContainer. Deze houdt de nodige info voor het aanmaken van die companion.
     * Om het overzichtelijk te houden zullen dergelijke soorten companion gemarkeerd worden als parameterCompanion.
     */

    public void exitApplication() {
        Platform.exit();
    }

    /**
     * Keert (indien mogelijk en dus niet na een checkpoint) terug naar het vorige scherm.
     */
    public void goBack() { ScreenController.goToPreviousScreen(); }
}
