package preferee.data.access.server.dao;

import preferee.data.Movie;
import preferee.data.MovieArray;
import preferee.data.access.DataAccessException;
import preferee.data.access.MovieDAO;

/**
 * Created by domien on 11/03/2015.
 */
public class ServerMovieDAO extends ServerAbstractDAO<Movie,MovieArray> implements MovieDAO {

    public ServerMovieDAO(String resourceURL) { super(resourceURL, Movie.class, MovieArray.class); }

    /**
     * Geef de film terug die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke film niet best
     *
     * OPMERKING: in elke dao zijn deze 2 lijntjes uniek. Toch zet ik ze niet in een superklasse omdat
     *            ik dan de code moeilijker leesbaar maak om uiteindelijk in deze methode overal 1 lijntje minder te hebben.
     *            (namelijk (Movie) super(id) in dit geval)
     *            Er zal ook 2 keer gecast moeten worden dan.aat.
     *
     * @param id
     */
    @Override
    public Movie getMovie(int id) throws DataAccessException {
        String url = this.itemList_URL + "/" + Integer.toString(id) + ".xml";
        return singleResourceUnmarshaller.unmarshall(url);
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
            // URL samenstellen
            StringBuilder urlBuilder = new StringBuilder(this.itemList_URL + ".xml?"); // hiervoor hoeft geen length>0 controle te zijn, want dit zal nog steeds correct werken.
            for (Filter filter : filters) {
                urlBuilder.append(((MovieFilter) filter).getQueryComponent());
                urlBuilder.append("&"); // Hier maakt een extra &, eventueel zonder vervolg, ook niets uit.
            }
            String url = urlBuilder.toString(); // einde van string-building.

            // URL opvragen en omzetten in Movie objecten mbv de multipleResourceDownloader en JAXB (singletons zullen ook werken)
            return this.ResourceArrayUnmarshaller.unmarshall(url).getItemsAsMap().values();
    }

    /**
     * Filter die kan gebruikt worden door {@link #listFiltered} om movies te filteren. Dergelijke filters
     * worden aangemaakt met de {@code by...} methoden
     */
    private interface MovieFilter extends Filter {
        String getQueryComponent();
    }

    /**
     * Filter voor een specifieke rating
     *
     * @param rating
     * @return StringFilter
     */
    @Override
    public Filter byRating(String rating) {
        MovieFilter filter = () -> "rating=" + rating;
        return filter;
    }

    /**
     * Filter voor een specifiek jaar van uitgave
     *
     * @param year
     * @return StringFilter
     */
    @Override
    public Filter byYear(String year) {
        MovieFilter filter = () -> "year=" + year;
        return filter;
    }

    /**
     * Filter voor een specifiek genre
     *
     * @param genre
     */
    @Override
    public Filter byGenre(String genre) {
        MovieFilter filter = () -> "genre=" + genre;
        return filter;
    }

    /**
     * Filter voor een specifieke taal
     *
     * @param language
     */
    @Override
    public Filter byLanguage(String language) {
        MovieFilter filter = () -> "language=" + language;
        return filter;
    }
}


