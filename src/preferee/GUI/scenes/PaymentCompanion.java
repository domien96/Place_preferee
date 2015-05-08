package preferee.GUI.scenes;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import preferee.GUI.*;
import preferee.data.Order;
import preferee.data.Showing;
import preferee.data.access.OrderDAO;
import preferee.data.access.Providers;

import java.util.Collection;

/**
 * Created by Domien on 8/05/2015.
 */
public class PaymentCompanion extends AbstractCompanion implements parameterCompanion {
    private final ParameterContainer parameters; // zie (@code AbstractCompanion)
    private OrderDAO oDAO;
    /**
     * Veld bestaande uit ENKEL tekstvelden. Ze vragen informatie over de gebruiker op.
     */
    public VBox UinfoWrapper1;
    public AnchorPane form1;// eerste invul formulier , algemene gegevens
    public TextField Uname;
    public TextField Uprename;
    public AnchorPane form2; // tweede invul formulier , bankgegevens
    public VBox UinfoWrapper2;
    public TextField Ulogin;
    public PasswordField UPass;
    public Pane errorMessage;
    public ProgressBar progressBar;


    /**
     *
     * CREATION
     *
     */

    public PaymentCompanion(ParameterContainer parameterContainer) {
        this.parameters = parameterContainer;
    }

    public void initialize() {
        oDAO = Providers.getProvider().getOrderDAO();
    }

    /**
     *
     * COMPANION PARAMETERS
     *
     */

    private static class ParameterContainer {
        private final Showing showing;
        private final Collection<Integer> seatNumbers;

        public ParameterContainer(Showing showing, Collection<Integer> seatNumbers) {
            this.showing = showing;
            this.seatNumbers = seatNumbers;
        }
    }

    public static PaymentCompanion of(Showing chosen, Collection<Integer> seatNumbers) {
        return new PaymentCompanion(new ParameterContainer(chosen,seatNumbers));
    }

    /**
     *
     * METHODS
     *
     */

    public void goToForm1() {
        form2.setVisible(false);
        form1.setVisible(true);
    }

    public void form1Completed() {
        // alles ingevuld?
        for (Node txtfield : UinfoWrapper1.getChildren()) {
            // deze for each vermijdt koppeling bij het toevoegen van een textfield.
            ((TextField) txtfield).getStyleClass().remove("InvalidParameter");
            if (((TextField) txtfield).getText().isEmpty()) {
                txtfield.getStyleClass().add("InvalidParameter");
                return;
            }
        }
        // ga naar 2de formulier
        form1.setVisible(false);
        form2.setVisible(true);
    }


    public void form2Completed() {
        // alles ingevuld?
        for (Node txtfield : UinfoWrapper2.getChildren()) {
            // deze for each vermijdt koppeling bij het toevoegen van een textfield.
            ((TextField) txtfield).getStyleClass().remove("InvalidParameter");
            if (((TextField) txtfield).getText().isEmpty()) {
                txtfield.getStyleClass().add("InvalidParameter");
                return;
            }
        }
        // controleer bank gegevens
        if ( ! bankDataIsValid()) {
            // doe iets
        }
        // maak de bestelling
        Task<Void> createOrderTask = Utils.task(() -> completeReservation());
        createOrderTask.setOnFailed(o -> {progressBar.setVisible(false);errorMessage.setVisible(true);});
        createOrderTask.setOnSucceeded(o -> {progressBar.setVisible(false);ScreenController.nextScreen(ScreenName.TransactionComplete, true);});
        progressBar.setVisible(true);
        createOrderTask.run();
    }


    /**
     * Controleer of de bank gegevens in orde zijn.
     * @return
     */
    private boolean bankDataIsValid() {
        // voorlopig geen controle
        return true;
    }

    /**
     * Maakt een order aan en koppelt hieraan de zitjes.
     */
    private Void completeReservation() {
        Order order = oDAO.createOrder(Uprename.getText()+Uname.getText(), parameters.showing.getId());
        for (Integer seatNR : parameters.seatNumbers) {
            oDAO.createReservation(seatNR, order.getId());
        }
        return null;
    }
}
