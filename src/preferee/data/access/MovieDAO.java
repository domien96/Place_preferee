package preferee.data.access;

import preferee.data.Movie;

/**
 * Data access object voor het opvragen van filmgegevens.
 *
 * Created by domien Van Steendam
 */
public interface MovieDAO {

    /**
     * Geef de film terug die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke film niet bestaat.
     */
    public Movie getMovie(int id) throws DataAccessException;

    /**
     * Lijst van films die aan de voorwaarden voldoen van <i>alle</i> opgegeven filters.
     * Volgend fragment toont bijvoorbeeld alle films met rating PG in het Engels
     * <pre>
     *     listFiltered( byRating("PG"), byLanguage("English") )
     * </pre>
     *
     * @return: Set
     */
    public Iterable<Movie> listFiltered (Filter... filters)  throws DataAccessException;

    /**
     * Filter die kan gebruikt worden door {@link #listFiltered}. Dergelijke filters
     * worden aangemaakt met de {@code by...} methoden
     */
    public interface Filter {

    }

    /**
     * Filter voor een specifieke rating
     */
    public Filter byRating(String rating);
    
    /**
     * Filter voor een specifiek jaar van uitgave
     */
    public Filter byYear(String year);
    
    /**
     * Filter voor een specifiek genre
     */
    public Filter byGenre(String genre);

    /**
     * Filter voor een specifieke taal
     */
    public Filter byLanguage(String language);

}
