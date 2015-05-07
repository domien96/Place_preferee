package preferee.GUI;

/**
 * Omvat de bewerkingen met de vensters.
 * Bv. volgend scherm, vorig scherm, extra scherm
 *
 * Created by Domien on 29/04/2015.
 */
public class ScreenController {

    private static ScreenManager manager; // hoofdscherm

    public static void init(ScreenName beginScreenName) {
        if (manager== null) {
            manager = new ScreenManager(beginScreenName);
        } else
            throw new RuntimeException("ScreenController is al ge-init");
    }

    /**
     * Verandert het hoofd-venster naar de nieuwe layout.
     * @param screenName : interne key voor de stage
     * @param pastCheckpoint : Te gebruiken om de geschiedenis te legen indien men zeker weet dat er niet meer
     *                         terug zal gekeerd worden vanaf dit scherm.
     */
    public static void nextScreen(ScreenName screenName, boolean pastCheckpoint) {
        manager.changeScreen(screenName);
        if (pastCheckpoint) {
            manager.resetScreenHistory();
        }
    }

    /**
     * Verandert het hoofd-venster van de layout.
     * De nieuwe layout(scene) wordt gecontroleerd door de meegegeven companion.
     * @param screenName
     * @param pastCheckpoint
     * @param companion : controleert de acties op het venster
     */
    public static void nextScreen(ScreenName screenName, boolean pastCheckpoint, AbstractCompanion companion) {
        manager.changeScreen(screenName, companion);
        if (pastCheckpoint) {
            manager.resetScreenHistory();
        }
    }

    /**
     * Keer terug
     */
    public static void goToPreviousScreen() {
        manager.goToPreviousScreen();
    }
}
