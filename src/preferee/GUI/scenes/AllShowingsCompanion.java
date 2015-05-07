package preferee.GUI.scenes;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import preferee.GUI.AbstractCompanion;
import preferee.GUI.parameterCompanion;
import preferee.GUI.tableView.AlignedAndWrappedTableCellFactory;
import preferee.data.Movie;
import preferee.data.Showing;
import preferee.data.access.Providers;
import preferee.data.access.ShowingDAO;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Venster na de gewenste film is gekozen.
 * Created by Domien on 14/04/2015.
 */
public class AllShowingsCompanion extends AbstractCompanion implements parameterCompanion {
    private final ParameterContainer parameters; // zie (@code AbstractCompanion)
    //
    public Label label;
    public TableView<Showing> tabel;
    public TableColumn<Showing,LocalDate> dateColumn;
    public TableColumn<Showing,LocalTime> timeColumn;
    public TableColumn<Showing,String> screenColumn;

    public TableColumn<Showing,String> idColumn;
    public ImageView poster;
    private ShowingDAO showingDAO;

    private AllShowingsCompanion(ParameterContainer info) {
        this.parameters = info;
    }

    public void initialize() {
        /** view (GUI) */
        //
        dateColumn.setCellValueFactory( col -> new ReadOnlyObjectWrapper<>(col.getValue().getTime().toLocalDate()));
        timeColumn.setCellValueFactory( col -> new ReadOnlyObjectWrapper<>(col.getValue().getTime().toLocalTime()));
        idColumn.setCellValueFactory(new PropertyValueFactory<Showing, String>("id"));
        screenColumn.setCellValueFactory(new PropertyValueFactory<Showing, String>("screenId"));

        //
        dateColumn.setCellFactory(new AlignedAndWrappedTableCellFactory<>(Pos.CENTER));
        timeColumn.setCellFactory(new AlignedAndWrappedTableCellFactory<>(Pos.CENTER));
        screenColumn.setCellFactory(new AlignedAndWrappedTableCellFactory<>(Pos.CENTER));

        /** model */
        // dao opvragen
        showingDAO = Providers.getProvider().getShowingDAO();
        //
        vulOp(); /// todo kijken nzar geslecteede button
    }

    /**
     *
     * COMPANION PARAMETERS
     *
     */

    /**
     * Houdt de parameters bij die nuttig zijn voor het maken van deze companion.
     */
    private static class ParameterContainer {
        public final Movie movie; // de gekozen film

        public ParameterContainer( Movie movie) {
            this.movie = movie;
        }

    }

    public static AllShowingsCompanion of(Movie movie) {
        return new AllShowingsCompanion(new ParameterContainer(movie));
    }


    /**
     *
     * METHODS
     *
     */
    public void vulOp() {
        ObservableList<Showing> model = FXCollections.observableArrayList();
        for ( Showing showing : showingDAO.listFiltered(showingDAO.byMovie(parameters.movie.getId()))) {
            model.add(showing);
        }
        tabel.setItems(model);
    }

    public void changePeriod(String periodName) {

    }
}
