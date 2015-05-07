package preferee.GUI.scenes;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import preferee.GUI.AbstractCompanion;
import preferee.GUI.ScreenController;
import preferee.GUI.ScreenName;
import preferee.GUI.Utils;
import preferee.GUI.tableView.AlignedAndWrappedTableCellFactory;
import preferee.GUI.tableView.MoviePosterTableCellFactory;
import preferee.GUI.tableView.MoviePosterTableCellValueFactory;
import preferee.data.Movie;
import preferee.data.access.MovieDAO;
import preferee.data.access.Providers;

import java.util.*;

/**
 * Created by Domien on 1/05/2015.
 */
public class AllMoviesCompanion extends AbstractCompanion {
    private final MovieDAO movieDAO = Providers.getProvider().getMovieDAO();

    public Label label;
    public TableView<Movie> tabel;
    public TableColumn<Movie,String> titleColumn;
    public TableColumn<Movie,String> genreColumn;
    public TableColumn<Movie,Image> posterColumn;
    public ImageView poster;
    // filters
    public TextField prefTitle;
    public ComboBox<String> prefRuntime;
    public TextField prefDirector;
    public ComboBox<String> prefGenre;
    public ComboBox<String> prefLanguage;
    public ComboBox<String> prefRating;
    public Label title;
    public Label director;
    public Label runtime;
    public Button goToMovieInfo;
    @FXML
    public ProgressBar progressBar;
    private static Map<String,String> talen = new HashMap<>();
    static {
        talen.put("Engels","English");
        talen.put("Nederlands","Dutch");
        talen.put("Duits","German");
        talen.put("Frans","French");
    }



    public void initialize() {
        /** View (GUI) */
        goToMovieInfo.disableProperty().bind(Bindings.isNull(tabel.getSelectionModel().selectedItemProperty()));
        // tabel
        tabel.setPlaceholder(new Label("Films worden geladen..."));

        //
        titleColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        posterColumn.setCellValueFactory(new MoviePosterTableCellValueFactory());
        genreColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("genre"));

        //
        posterColumn.setCellFactory(new MoviePosterTableCellFactory());
        titleColumn.setCellFactory(new AlignedAndWrappedTableCellFactory<>(Pos.CENTER));
        genreColumn.setCellFactory(new AlignedAndWrappedTableCellFactory<>(Pos.CENTER));

        /** model */
        tabel.getSelectionModel().selectedItemProperty().addListener(new SelectionListener());
        //
        filter(); // opvullen is hetzelfde als filteren zonder parameters.
    }

    /**
     *
     *  FILTERING
     *
     */
    public void filter() {
        tabel.getItems().clear();
        tabel.setPlaceholder(new Label("Even geduld..."));
        //
        Task<Iterable<Movie>> task = Utils.task(() -> {
                Collection<MovieDAO.Filter> filters = makeFilters();
                return movieDAO.listFiltered(filters.toArray(new MovieDAO.Filter[filters.size()])); });
        // succeeded : ververs tabel, gefaald : toon foutmelding in gui
        task.stateProperty().addListener((Observable o) ->
        {
            if (task.getState() == Worker.State.SUCCEEDED) {
                tabel.setPlaceholder(new Label("Geen films gevonden met die kenmerken.")); // indien niet leeg, wordt spaceholder overlapt.
                updateTabel(task.getValue());
            } else if (task.getState() == Worker.State.FAILED){
                tabel.setPlaceholder(new Label("Kon de bestanden niet ophalen.\nControleer de URL en uw verbinding."));
            }
        });
        new Thread(task).start();
    }

    /**
     * Maakt de groep filters die voldoet aan de eisen van de klant.
     * Filtercriteria toevoegen gebeurt hier. Criteria gelijk aan de default-waarde worden niet toegevoegd.
     * @return lijst van filters
     */
    private Collection<MovieDAO.Filter> makeFilters() {
        Queue<MovieDAO.Filter> filters = new LinkedList<>(); // voorkomt null
        // alle criteria toevoegen, indien default-waarde tijdelijk null-waarde toevoegen.
        // kan code duplicatie niet vermijden vanwege verschillende methodes om filters te maken. Wel lage koppeling voor filter-values.
            String criteria;
         // textfield
        criteria = prefTitle.getText(); // titel
        filters.add(criteria.trim().isEmpty() ? null : movieDAO.byTitle(criteria));
        criteria = prefDirector.getText(); // director
        filters.add(criteria.trim().isEmpty()? null : movieDAO.byDirector(criteria));

         // combobox
        criteria = prefGenre.getSelectionModel().getSelectedItem(); // safe cast, genre
        filters.add(criteria==null || criteria.equals("Alle") ? null : movieDAO.byGenre(criteria));
        criteria = prefLanguage.getSelectionModel().getSelectedItem(); // safe cast, language
        filters.add(criteria==null || criteria.equals("Alle") ? null : movieDAO.byLanguage(talen.get(criteria)));
        criteria = prefRating.getSelectionModel().getSelectedItem(); // safe cast, rating
        filters.add(criteria==null || criteria.equals("Alle") ? null : movieDAO.byRating(criteria));
        criteria = prefRuntime.getSelectionModel().getSelectedItem(); // safe cast,
        filters.add(criteria == null || criteria.equals("Alle") ? null : movieDAO.byMaxRuntime(Integer.parseInt(criteria.substring(2)))); // runtime ("< " weghalen)
        //
        filters.removeAll(Collections.singleton(null));
        return filters;
    }

    private void updateTabel(Iterable<Movie> movies) {
        // we kunnen geen iterable meegeven aan de FXcollections. Dus zullen we een nieuwe lijst manueel opvullen. (= minder koppeling)
        // casten doen we niet omdat we zogezegd niet weten wat het dynamisch type is van movies.
        List<Movie> data = new LinkedList<>();
        for (Movie movie : movies) {
            data.add(movie);
        }
        tabel.setItems(FXCollections.observableArrayList(data));
    }

     /**
     *
     *
     * END FILTERING
     *
     */

    /**
     * Kan geactiveerd worden als er een movie gekozen is.
     * Want Button disable property is gebonden.
     */
    public void goToFilmInfo() {
        Movie chosen = tabel.getSelectionModel().getSelectedItem();
        AbstractCompanion companion  = MovieInfoCompanion.of(chosen);
        ScreenController.nextScreen(ScreenName.MovieInfo, false, companion);
    }
    private class SelectionListener implements InvalidationListener {

        @Override
        public void invalidated(Observable observable) {
            Movie selected = tabel.getSelectionModel().getSelectedItem();
            if (selected == null) {
                poster.setImage(null);
                title.setText(null);
                director.setText(null);
                runtime.setText("0");

            } else {
                poster.setImage(new Image(selected.getPoster()));
                title.setText(selected.getTitle());
                director.setText(selected.getDirector());
                runtime.setText(String.valueOf(selected.getDuration()));
            }
        }
    }
}
