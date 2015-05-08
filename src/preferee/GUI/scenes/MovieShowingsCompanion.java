package preferee.GUI.scenes;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import preferee.GUI.*;
import preferee.GUI.myComponents.PropertyBox;
import preferee.GUI.tableView.AlignedAndWrappedTableCellFactory;
import preferee.data.Movie;
import preferee.data.Showing;
import preferee.data.access.Providers;
import preferee.data.access.ShowingDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Domien on 7/05/2015.
 */
public class MovieShowingsCompanion extends AbstractCompanion implements parameterCompanion {
    private final ParameterContainer parameters; //zie (@code AbstractCompanion)
    private final ShowingDAO sDAO;

    @FXML
    public TableView<Showing> tabel;
    @FXML
    public PropertyBox date;
    @FXML
    public PropertyBox time;
    @FXML
    public PropertyBox screen;
    @FXML
    public PropertyBox headingTitle;
    @FXML
    public TableColumn<Showing,LocalDate> dateColumn;
    @FXML
    public TableColumn<Showing,LocalTime> timeColumn;
    @FXML
    public TableColumn<Showing,String> screenColumn;
    @FXML
    public Button goToSeats;

    private MovieShowingsCompanion(ParameterContainer params) {
        this.parameters = params;
        sDAO = Providers.getProvider().getShowingDAO();
    }

    public void initialize() {
        /** View */
        goToSeats.disableProperty().bind(Bindings.isNull(tabel.getSelectionModel().selectedItemProperty()));
        headingTitle.setValue(parameters.movie.getTitle());

        // tabel
        dateColumn.setCellValueFactory( col -> new ReadOnlyObjectWrapper<>(col.getValue().getTime().toLocalDate()));
        timeColumn.setCellValueFactory( col -> new ReadOnlyObjectWrapper<>(col.getValue().getTime().toLocalTime()));
        screenColumn.setCellValueFactory(new PropertyValueFactory<Showing, String>("screenId"));

        //
        dateColumn.setCellFactory(new AlignedAndWrappedTableCellFactory<>(Pos.CENTER));
        timeColumn.setCellFactory(new AlignedAndWrappedTableCellFactory<>(Pos.CENTER));
        screenColumn.setCellFactory(new AlignedAndWrappedTableCellFactory<>(Pos.CENTER));

        //

        tabel.setPlaceholder(new Label("Voorstellingen worden geladen..."));
        //
        fillTable();
        /** Model */
        tabel.getSelectionModel().selectedItemProperty().addListener(new SelectionListener());
    }

    /**
     *
     *  COMPANION PARAMETERS
     *  bevat de nuttige/nodige eigenschappen van de movie.
     *
     *
     */

    private static class ParameterContainer {
        private final Movie movie;

        public ParameterContainer(Movie movie) {
            this.movie = movie;
        }
    }

    public static MovieShowingsCompanion of(Movie movie) {
        return new MovieShowingsCompanion(new ParameterContainer(movie));
    }

    /**
     *
     *
     *  Methods
     *
     *
     */

    public void fillTable() {
        Task<Iterable<Showing>> task = Utils.task(() -> sDAO.listFiltered(sDAO.fromNow(Integer.MAX_VALUE)));
        // succeeded : ververs tabel, gefaald : toon foutmelding in gui
        task.stateProperty().addListener((Observable o) ->
        {
            if (task.getState() == Worker.State.SUCCEEDED) {
                List<Showing> data = new LinkedList<>();
                for (Showing showing : task.getValue()) {
                    data.add(showing);
                }
                tabel.setItems(FXCollections.observableArrayList(data));
                tabel.setPlaceholder(new Label("Geen toekomstige vertoningen gevonden.")); // indien niet leeg, wordt spaceholder overlapt.
            } else if (task.getState() == Worker.State.FAILED) {
                tabel.setPlaceholder(new Label("Kon de bestanden niet ophalen.\nControleer de URL en uw verbinding."));
            }
        });
        new Thread(task).start();
    }

    public void goToSeats() {
        ScreenSeatsCompanion comp = ScreenSeatsCompanion.of(tabel.getSelectionModel().getSelectedItem()); // nooit null, want button luistert naar selectiemodel
        ScreenController.nextScreen(ScreenName.Seats, false, comp);
    }

    private class SelectionListener implements InvalidationListener {

        @Override
        public void invalidated(Observable observable) {
            Showing selected = tabel.getSelectionModel().getSelectedItem();
            if (selected == null) {
                date.setValue(null);
                time.setValue(null);
                screen.setValue(null);

            } else {
                date.setValue(String.valueOf(selected.getTime().toLocalDate()));
                time.setValue(String.valueOf(selected.getTime().toLocalTime()));
                screen.setValue(String.valueOf(selected.getScreenId()));
            }
        }
    }

}
