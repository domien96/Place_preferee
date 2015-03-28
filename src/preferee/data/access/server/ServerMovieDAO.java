package preferee.data.access.server;

import preferee.data.Movie;
import preferee.data.MovieArray;
import preferee.data.access.DataAccessException;
import preferee.data.access.MovieDAO;

/**
 * Created by domien on 11/03/2015.
 */
public class ServerMovieDAO extends ServerAbstractDAO implements MovieDAO {

    public ServerMovieDAO(String resourceURL) { super(resourceURL); }

    /**
     *  Kan een online XML-bestand omzetten naar respectievelijk één movie-object, meerdere movie-objecten.
     *  Zo'n downloader moet wel weten welke klasse objecten hij moet vertalen aangezien de downloader met type variabelen werkt.
     */
    ResourceWebReader<Movie> singleResourceWebReader = new ResourceWebReader<>(Movie.class);
    ResourceWebReader<MovieArray> multipleResourceWebReader = new ResourceWebReader<>(MovieArray.class);


    /**
     * Geef de film terug die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke film niet best
     *
     * OPMERKING: in elke dao zijn deze 2 lijntjes uniek. Toch zet ik ze niet in een superklasse omdat
     *            ik dan de code moeilijker leesbaar maak om uiteindelijk in deze methode overal 1 lijntje minder te hebben.
     *            Er zal ook 2 keer gecast moeten worden dan.aat.
     *
     * @param id
     */
    @Override
    public Movie getMovie(int id) throws DataAccessException {
        String url = this.orders_URL + "/" + Integer.toString(id) + ".xml";
        return singleResourceWebReader.getItemFromURL(url);
    }

    // FILTERS

    /**
     * Hiermee wordt er gefilterd op een String-waarde.
     * Er hoeft geen bovenklasse te zijn (zoals in ShowingDAO) , want alle criteria zijn voorlopig Strings.
     */
    private class StringFilter extends ServerFilter<String> implements Filter {

        public StringFilter(String criteria, String value) {
            super(criteria, value);
        }

        public String setParameters(String criteria, String value) {
           return criteria + value;
        }

        // deze methode heeft in deze context geen nut aangezien de server al kan gefilterd worden via link.
        // Zonder de server-flters zouden we deze methode dus nuttig kunnen gebruiken. Maw dit is geen slecht ontwerp.
        @Override
        public boolean isValid(Movie movie) {
            return false;
        }
    }

    // END FILTERS

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
            StringBuilder urlBuilder = new StringBuilder(this.orders_URL + ".xml?"); // hiervoor hoeft geen length>0 controle te zijn, want dit zal nog steeds correct werken.
            for (Filter filter : filters) {
                urlBuilder.append(((ServerFilter<String>) filter).getUrlAchterVoegsel());
                urlBuilder.append("&"); // Hier maakt een extra &, eventueel zonder vervolg, ook niets uit.
            }
            String url = urlBuilder.toString(); // einde van string-building.

            // URL opvragen en omzetten in Movie objecten mbv de multipleResourceDownloader en JAXB (singletons zullen ook werken)
            return this.multipleResourceWebReader.getItemFromURL(url).getItemsAsMap().values();
    }

    /**
     * Filter voor een specifieke rating
     *
     * @param rating
     * @return StringFilter
     */
    @Override
    public Filter byRating(String rating) {
        return new StringFilter("rating=",rating);
    }

    /**
     * Filter voor een specifiek jaar van uitgave
     *
     * @param year
     * @return StringFilter
     */
    @Override
    public Filter byYear(String year) {
        return new StringFilter("year=",year);
    }

    /**
     * Filter voor een specifiek genre
     *
     * @param genre
     */
    @Override
    public Filter byGenre(String genre) {
        return new StringFilter("genre=",genre);
    }

    /**
     * Filter voor een specifieke taal
     *
     * @param language
     */
    @Override
    public Filter byLanguage(String language) {
        return new StringFilter("language=",language);
    }

    /** TODO    : dit wegdoen , was voor testing.
    public Movie[] getall() {
        return this.multipleResourceDownloader.getItemFromURL(this.orders_URL+".xml?year=2014&").getItems();
    }*/
}
