package preferee.data.access.server.dao;

import preferee.data.Showing;
import preferee.data.ShowingArray;
import preferee.data.access.DataAccessException;
import preferee.data.access.ShowingDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by domien on 11/03/2015.
 */
public class ServerShowingDAO extends ServerAbstractDAO<Showing,ShowingArray> implements ShowingDAO {

    public ServerShowingDAO(String resourceURL) { super(resourceURL, Showing.class, ShowingArray.class); }


    /**
     * De vertoning terug die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke vertoning niet bestaat.
     *
     * @param id: id
     */
    @Override
    public Showing getShowing(int id) throws DataAccessException {
        String url = this.itemList_URL + "/" + Integer.toString(id) + ".xml";
        return singleResourceUnmarshaller.unmarshall(url);
    }

    /**
     * Lijst van vertoningen die aan de voorwaarden voldoen van <i>alle</i> opgegeven filters.
     * Volgend fragment toont bijvoorbeeld alle vertoningen die vandaag spelen in zaal (met id) 3
     * <pre>
     *     listFiltered( byScreen(3), byDate(LocalDate.now() )
     * </pre>
     *
     * @param filters: actieve filters
     * @return: HashSet<Showing> : bevat alle gewenste showings.
     * @throws: DataAccessException
     */
    @Override
    public Iterable<Showing> listFiltered(Filter... filters) throws DataAccessException {
        // URL samenstellen
        StringBuilder urlBuilder = new StringBuilder(this.itemList_URL + ".xml?"); // hiervoor hoeft geen length>0 controle te zijn, want dit zal nog steeds correct werken.
        for (Filter filter : filters) {
            urlBuilder.append(((ShowingFilter) filter).getQueryComponent());
            urlBuilder.append("&"); // Hier maakt een extra &, eventueel zonder vervolg, ook niets uit.
        }
        String url = urlBuilder.toString(); // einde van string-building.

        // URL opvragen en omzetten in Movie objecten mbv de multipleResourceDownloader en JAXB (singletons zullen ook werken)
        ShowingArray filtered = this.ResourceArrayUnmarshaller.unmarshall(url);
        if (filtered == null)
            return new ArrayList<>();
        return filtered.getItemsAsMap().values();
    }

    /**
     * Filter die kan gebruikt worden door {@link #listFiltered} om showings te filteren. Dergelijke filters
     * worden aangemaakt met de {@code by...} methoden
     */
    private interface ShowingFilter extends Filter{
        String getQueryComponent();
    }

    /**
     * Filter voor een specifieke zaal
     *
     * @param screenId
     */
    @Override
    public Filter byScreen(int screenId) {
        ShowingFilter filter = () -> "screen_id=" + screenId;
        return filter;
    }

    /**
     * Filter voor een specifieke dag
     *
     * @param date
     */
    @Override
    public Filter byDay(LocalDate date) {
        ShowingFilter filter = () -> "date=" + date;
        return filter;
    }

    /**
     * Filter voor een specifiek uur
     *
     * @param time
     */
    @Override
    public Filter byTimeOfDay(LocalTime time) {
        ShowingFilter filter = () -> "time=" + time;
        return filter; // enkel uur voldoet , dus standaard-datum hoeft er niet bij
    }

    /**
     * Filter die films selecteert vanaf een bepaald tijdstip in de dag (inclusief)
     *
     * @param time
     */
    @Override
    public Filter fromTimeOfDay(LocalTime time) {
        ShowingFilter filter = () -> "from_time=" + time;
        return filter; // enkel uur voldoet , dus standaard-datum hoeft er niet bij
    }

    /**
     * Filter voor een specifieke film
     *
     * @param movieId
     */
    @Override
    public Filter byMovie(int movieId) {
        ShowingFilter filter = () -> "movie_id=" + movieId;
        return filter;
    }
}
