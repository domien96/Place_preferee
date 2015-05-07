package preferee.GUI.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import preferee.GUI.AbstractCompanion;
import preferee.GUI.ScreenController;
import preferee.GUI.ScreenName;
import preferee.GUI.myComponents.PropertyBox;
import preferee.GUI.parameterCompanion;
import preferee.data.Movie;

import java.net.URI;

/**
 * Created by Domien on 6/05/2015.
 */
public class MovieInfoCompanion extends AbstractCompanion implements parameterCompanion {
    private final ParameterContainer parameters; //zie (@code AbstractCompanion)
    //
    @FXML
    private Label title;
    @FXML
    private PropertyBox year;
    @FXML
    private PropertyBox duration;
    @FXML
    private PropertyBox director;
    @FXML
    private PropertyBox genre;
    @FXML
    private PropertyBox language;
    @FXML
    private PropertyBox rating;
    @FXML
    private ImageView poster;
    private String imdb_id;

    private MovieInfoCompanion(ParameterContainer params) {
        this.parameters = params;

    }

    public void initialize() {
        title.setText(parameters.movie.getTitle());
        year.setValue(String.valueOf(parameters.movie.getYear()));
        duration.setValue(String.valueOf(parameters.movie.getDuration()) + "minuten");
        director.setValue(parameters.movie.getDirector());
        genre.setValue(parameters.movie.getGenre());
        language.setValue(parameters.movie.getLanguage());
        rating.setValue(parameters.movie.getRating());
        poster.setImage(new Image(parameters.movie.getPoster()));
        imdb_id = parameters.movie.getImdbId();
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

    public static MovieInfoCompanion of(Movie chosen) {
        return new MovieInfoCompanion(new ParameterContainer(chosen));
    }

    /**
     *
     *  METHODS
     *
     */

    public void goToMovieShowings() {
        AbstractCompanion comp = MovieShowingsCompanion.of(parameters.movie);
        ScreenController.nextScreen(ScreenName.MovieShowings, false, comp);
    }

    public void openIMDb() {
        try {
            UrlOpener.openURL("www.imdb.com/title/" + imdb_id);
        } catch (Exception e) {
            e.printStackTrace(); // todo
        }
    }

    /**
     * Klasse om URL's in de browser te openen.
     */
    private static class UrlOpener {
        public static void openURL(String url) throws Exception {
            java.awt.Desktop.getDesktop().browse(new URI(url));
        }
    }
}
