package preferee.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import preferee.data.Movie;
import preferee.data.access.Providers;

import java.util.Observable;

/**
 * Venster na initialisatie.
 * Created by Domien on 14/04/2015.
 */
public class HomeCompanion extends AbstractCompanion {
    public Label label;
    public TableView<Movie> tabel;
    public TableColumn<Movie,String> titleColumn;
    public TableColumn<Movie,String> idColumn;

    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));

        idColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("id"));

        vulOp(); /// todo kijken nzar geslecteede button

    }

    public void vulOp() {
        ObservableList<Movie> model = FXCollections.observableArrayList();
        for ( Movie movie : Providers.getProvider().getMovieDAO().listFiltered()) {
            model.add(movie);
        }
        tabel.setItems(model);
    }
}
