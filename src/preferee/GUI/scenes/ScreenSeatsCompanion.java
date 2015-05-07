package preferee.GUI.scenes;

import javafx.application.Platform;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import preferee.GUI.AbstractCompanion;
import preferee.GUI.Utils;
import preferee.GUI.parameterCompanion;
import preferee.data.Screen;
import preferee.data.Showing;
import preferee.data.access.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Domien on 7/05/2015.
 */
public class ScreenSeatsCompanion extends AbstractCompanion implements parameterCompanion {
    private final ParameterContainer parameters;
    private final ScreenDAO scrDAO;
    private final OrderDAO ordDAO;
    //
    public FlowPane seatsMap;


    /**
     *
     * CREATION
     *
     */
    private ScreenSeatsCompanion(ParameterContainer params) {
        this.parameters = params;
        scrDAO = Providers.getProvider().getScreenDAO();
        ordDAO = Providers.getProvider().getOrderDAO();
    }

    public void initialize() {

        Utils.task(() -> fillWithSeats()).run();
    }

    public Void fillWithSeats() {
        Screen screen = scrDAO.getScreen(parameters.showing.getScreenId());
        int rows = screen.getNumberOfRows(), cols = screen.getNumberOfColumns();
        Collection<ToggleButton> screenSeats = new ArrayList<>(); // hierin worden alle zitjes toegevoegd (IN VOLGORDE)
        Collection<Integer> takenSeats = ordDAO.listTakenSeats(parameters.showing.getId()); // die van de gebruiker horen hier niet bij.

        for ( int i = 0; i< rows*cols; i++) { // zitjes toevoegen
            screenSeats.add(seatButtonFactory.createSeat(i, ! takenSeats.contains(i)));
        }

        Platform.runLater(() -> { // deze zaken moet op java FX draad gebeuren
            seatsMap.setMinWidth(cols * seatButtonFactory.seatWidth);
            seatsMap.setMaxWidth(cols * seatButtonFactory.seatWidth);
                                seatsMap.getChildren().addAll(screenSeats);});
        return null;
    }

    private static class seatButtonFactory {
        private static double seatWidth = 60;

        /**
         * Creert een togglebutton met meegegeven nummer.
         * De boolean bepaalt of het zitje vrij is.
         */
        public static ToggleButton createSeat(int number, boolean isFree) {
            ToggleButton seat = new ToggleButton(String.valueOf(number));
            seat.setPrefWidth(seatWidth);
            seat.setSelected(! isFree);
            seat.setDisable(! isFree);
            return seat;
        }
    }


    /**
     *
     *  COMPANION PARAMETER
     *
     */
    private static class ParameterContainer {
        private final Showing showing;

        public ParameterContainer(Showing showing) {
            this.showing = showing;
        }
    }

    public static ScreenSeatsCompanion of(Showing chosen) {
        return new ScreenSeatsCompanion(new ParameterContainer(chosen));
    }

    /**
     *
     * METHODS
     *
     */

}
