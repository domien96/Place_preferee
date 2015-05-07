package preferee.data.access;

import preferee.data.Movie;
import preferee.data.MovieCollection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Bovenklasse van alle movieDAO's
 * Created by Domien on 5/05/2015.
 */
public abstract class AbstractMovieDAO extends AbstractDAO<Movie,MovieCollection> implements MovieDAO {

    protected AbstractMovieDAO(String serverURL) {
        super(serverURL, Movie.class, MovieCollection.class);
    }



    /**
     * Lijst van films die aan de voorwaarden voldoen van <i>alle</i> opgegeven filters.
     * Volgend fragment toont bijvoorbeeld alle films met rating PG in het Engels
     * <pre>
     *     listFiltered( byRating("PG"), byLanguage("English") )
     * </pre>
     *
     * @param filters
     */
    @Override
    public Iterable<Movie> listFiltered(Filter... filters) throws DataAccessException {
        List<Movie> filtered = new LinkedList<>(listAll()); // hier zullen we elementen uit verwijderen.
        List<Movie> movies = new ArrayList<>(filtered); // itereren , voorkomt concurrentmodificationexception
        for (Movie movie : movies ) {
            for (Filter filter : filters) {
                if (!((MovieFilter) filter).isValid(movie)) {
                    filtered.remove(movie);
                }
            }
        }
        return filtered;
    }

    /**
     * Filter die kan gebruikt worden door {@link #listFiltered} om movies te filteren. Dergelijke filters
     * worden aangemaakt met de {@code by...} methoden
     */
    private interface MovieFilter extends Filter {
        boolean isValid(Movie movie);
    }

    /**
     * Filter voor een specifieke rating
     *
     * @param rating
     */
    @Override
    public Filter byRating(String rating) {
        MovieFilter filter = (Movie movie) -> movie.getRating().equals(rating);
        return filter;
    }

    /**
     * Filter voor een specifiek jaar van uitgave
     *
     * @param year
     */
    @Override
    public Filter byYear(String year) {
        MovieFilter filter = (Movie movie) -> Integer.toString(movie.getYear()).equals(year);
        return filter;
    }

    /**
     * Filter voor een specifiek genre
     *
     * @param genre
     */
    @Override
    public Filter byGenre(String genre) {
        MovieFilter filter = (Movie movie) -> movie.getGenre().equals(genre);
        return filter;
    }

    /**
     * Filter voor een specifieke taal
     *
     * @param language
     */
    @Override
    public Filter byLanguage(String language) {
        MovieFilter filter = (Movie movie) -> movie.getLanguage().equals(language);
        return filter;
    }

    @Override
    public Filter byTitle(String title) {
        MovieFilter filter = (Movie movie) -> movie.getTitle().toLowerCase().contains(title.trim().replace(" +", " ").toLowerCase());
        return filter;
    }

    @Override
    public Filter byDirector(String director) {
        MovieFilter filter = (Movie movie) -> movie.getDirector().toLowerCase().contains(director.trim().replace(" +", " ").toLowerCase());
        return filter;
    }

    @Override
    public Filter byMaxRuntime(int runtime) {
        MovieFilter filter = (Movie movie) -> movie.getDuration() <= runtime;
        return filter;
    }

    @Override
    public Filter kidsAllowed(boolean allowed) {
        return (MovieFilter) (movie)-> movie.getRating().matches("R|PG") == allowed;
    }
}
