package preferee.GUI.scenes;

import com.sun.javafx.scene.control.skin.IntegerFieldSkin;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import preferee.GUI.*;
import preferee.data.Screen;
import preferee.data.Showing;
import preferee.data.access.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Zitjes kiezen
 * Created by Domien on 7/05/2015.
 */
public class ScreenSeatsCompanion extends AbstractCompanion implements parameterCompanion {
    private final ParameterContainer parameters;
    private final ScreenDAO scrDAO;
    private final OrderDAO ordDAO;
    //
    public FlowPane seatsMap;
    public ListView<Integer> seatNumbersList;
    public ObservableList<Integer> seatNumbers = FXCollections.observableArrayList();
    public ProgressBar progressBar;


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
        Task<Void> fillTask = Utils.task( () -> fillWithSeats());
        //fill.setOnFailed(() -> return null;);
        new Thread(fillTask).start();
        /** model */
        seatNumbersList.setItems(seatNumbers);
    }

    public Void fillWithSeats() {
        Screen screen = scrDAO.getScreen(parameters.showing.getScreenId());
        int rows = screen.getNumberOfRows(), cols = screen.getNumberOfColumns();
        Collection<ToggleButton> screenSeats = new ArrayList<>(); // hierin worden alle zitjes toegevoegd (IN VOLGORDE)
        Collection<Integer> takenSeats = ordDAO.listTakenSeats(parameters.showing.getId()); // die van de gebruiker horen hier niet bij.
        SeatButtonFactory seatButtonFactory = new SeatButtonFactory(); // maakt zitjes

        for ( int i = 0; i< rows*cols; i++) { // zitjes toevoegen
            screenSeats.add(seatButtonFactory.createSeat(i, ! takenSeats.contains(i)));
        }

        Platform.runLater(() -> { // deze zaken moet op java FX draad gebeuren
            seatsMap.setMinWidth(cols * seatButtonFactory.seatWidth);
            seatsMap.setMaxWidth(cols * seatButtonFactory.seatWidth);
            seatsMap.getChildren().clear();
                                seatsMap.getChildren().addAll(screenSeats);});
        return null;
    }

    private class SeatButtonFactory {
        private double seatWidth = 60;

        /**
         * Creert een togglebutton met meegegeven nummer.
         * De boolean bepaalt of het zitje vrij is.
         */
        public ToggleButton createSeat(int number, boolean isFree) {
            ToggleButton seat = new ToggleButton(String.valueOf(number));
            seat.setPrefWidth(seatWidth);
            seat.setSelected(! isFree);
            seat.setDisable(! isFree);

            //zichzelf toevoegen aan/verwijderen van lijst indien aangeklikt
            seat.setOnMouseClicked(event -> { ToggleButton clickedSeat = ((ToggleButton) event.getSource());
                if (!clickedSeat.isDisabled()) {
                    if (clickedSeat.isSelected())
                        seatNumbers.add(Integer.valueOf(clickedSeat.getText()));
                    else // niet geselecteerd
                        seatNumbers.remove(clickedSeat);
                }
            });
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
    /**
     * Gaat naar betalingsscherm
     */
    public void goToPayment() {
        PaymentCompanion comp = PaymentCompanion.of(parameters.showing, new ArrayList<>(seatNumbers));
        ScreenController.nextScreen(ScreenName.Payment, false, comp);
    }
}
