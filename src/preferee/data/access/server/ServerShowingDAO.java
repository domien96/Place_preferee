package preferee.data.access.server;

import preferee.data.Showing;
import preferee.data.ShowingArray;
import preferee.data.access.DataAccessException;
import preferee.data.access.ShowingDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.Temporal;

/**
 * Created by domien on 11/03/2015.
 */
public class ServerShowingDAO extends ServerAbstractDAO implements ShowingDAO {

    public ServerShowingDAO(String resourceURL) { super(resourceURL); }

    /**
     *  Kan een online XML-bestand omzetten naar respectievelijk één showing-object, meerdere showing-objecten.
     *  Zo'n downloader moet wel weten welke klasse objecten hij moet vertalen aangezien de downloader met type variabelen werkt.
     */
    ResourceWebReader<Showing> singleResourceWebReader = new ResourceWebReader<>(Showing.class);
    ResourceWebReader<ShowingArray> multipleResourceWebReader = new ResourceWebReader<>(ShowingArray.class);


    /**
     * De vertoning terug die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke vertoning niet bestaat.
     *
     * @param id
     */
    @Override
    public Showing getShowing(int id) throws DataAccessException {
        String url = this.orders_URL + "/" + Integer.toString(id) + ".xml";
        return singleResourceWebReader.getItemFromURL(url);
    }

    // FILTERS

    /**
     * Hiermee wordt er gefilterd op een int-waarde.
     * Hiermee wordt er bovendien gefilterd op een tijd-waarde. bv. LocalTime, LocalDate
     * Toevallig hebben de filters van deze objecten dezelfde implementatie
     */
    private class ShowingFilter extends ServerFilter implements Filter {

        public ShowingFilter(String criteria, Object value) {
            super(criteria, value);
        }
        @Override
        public String setParameters(String criteria, Object value) {
            return criteria + value;
        }

        // deze methode heeft in deze context geen nut aangezien de server al kan gefilterd worden via link.
        // Zonder de server-flters zouden we deze methode dus nuttig kunnen gebruiken. Maw dit is geen slecht ontwerp.
        @Override
        public boolean isValid(Showing showing) {
            return false;
        }

        @Override
        public String getQueryComponent(String param, String value) {
            return null;
        }
    }
    // END FILTERS

    /**
     * Lijst van vertoningen die aan de voorwaarden voldoen van <i>alle</i> opgegeven filters.
     * Volgend fragment toont bijvoorbeeld alle vertoningen die vandaag spelen in zaal (met id) 3
     * <pre>
     *     listFiltered( byScreen(3), byDate(LocalDate.now() )
     * </pre>
     *
     * @param filters
     * @return: Set
     */
    @Override
    public Iterable<Showing> listFiltered(Filter... filters) throws DataAccessException {
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
     * Filter voor een specifieke zaal
     *
     * @param screenId
     */
    @Override
    public Filter byScreen(int screenId) {
        String g = "screen_id=";
        return () -> "screen";
    }

    /**
     * Filter voor een specifieke dag
     *
     * @param date
     */
    @Override
    public Filter byDay(LocalDate date) {
        return new ShowingFilter("date=",date);
    }

    /**
     * Filter voor een specifiek uur
     *
     * @param time
     */
    @Override
    public Filter byTimeOfDay(LocalTime time) {
        return new ShowingFilter("time=",time); // enkel uur voldoet , dus standaard-datum hoeft er niet bij
    }

    /**
     * Filter die films selecteert vanaf een bepaald tijdstip in de dag (inclusief)
     *
     * @param time
     */
    @Override
    public Filter fromTimeOfDay(LocalTime time) {
        return new ShowingFilter("from_time=",time); // enkel uur voldoet , dus standaard-datum hoeft er niet bij
    }

    /**
     * Filter voor een specifieke film
     *
     * @param movieId
     */
    @Override
    public Filter byMovie(int movieId) {
        return new ShowingFilter("movie_id=",movieId);
    }
}
