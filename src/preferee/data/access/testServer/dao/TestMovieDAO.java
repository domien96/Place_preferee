package preferee.data.access.testServer.dao;

import preferee.data.Movie;
import preferee.data.access.DataAccessException;
import preferee.data.access.MovieDAO;
import preferee.data.access.testServer.resourcesCache.LocalTestServer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by domien Van Steendam on 11/03/2015.
 * Bootst een database na zoals op de bioscoop-website
 */
public class TestMovieDAO extends TestAbstractDAO implements MovieDAO{

    public TestMovieDAO(LocalTestServer server) {
        super(server);
    }

    /**
     * Geef de film terug die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke film niet bestaat.
     *
     * @param id
     */
    @Override
    public Movie getMovie(int id) throws DataAccessException {
        Movie movie = server.getMovies().get(id);
        if (movie != null)
            return movie;
        else
            throw new DataAccessException("Film met die id werd niet gevonden.");
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
        List<Movie> filtered = new LinkedList<>(server.getMovies().values()); // hier zullen we elementen uit verwijderen.
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
}
