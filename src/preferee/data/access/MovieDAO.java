package preferee.data.access;

import preferee.data.Movie;

/**
 * Data access object voor het opvragen van filmgegevens.
 *
 * Created by Domien Van Steendam
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

    /**
     * Filter voor een volledige titel.
     * Niet hoofdletter-gevoelig.
     */
    public Filter byTitle(String title);

    /**
     * Filter voor een volledige regisseursnaam.
     * Niet hoofdletter-gevoelig.
     */
    public Filter byDirector(String director);

    /**
     * Filter voor de maximale speeltijd. (inclusief bovengrens)
     */
    public Filter byMaxRuntime(int runtime);

    /**
     * Filter of kinderen al dan niet zijn toegelaten.
     * Hiervoor wordt gekeken naar de ratings.
     * Ratings die kinderen toelaten : G , PG
     */
    public Filter kidsAllowed(boolean allowed);

}
