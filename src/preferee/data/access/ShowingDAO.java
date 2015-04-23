package preferee.data.access;

import preferee.data.Showing;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data access object voor het opvragen van vertoningen.
 *
 * Created by Domien Van Steendam
 */
public interface ShowingDAO {

    /**
     * De vertoning terug die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke vertoning niet bestaat.
     */
    public Showing getShowing(int id) throws DataAccessException;

    /**
     * Lijst van vertoningen die aan de voorwaarden voldoen van <i>alle</i> opgegeven filters.
     * Volgend fragment toont bijvoorbeeld alle vertoningen die vandaag spelen in zaal (met id) 3
     * <pre>
     *     listFiltered( byScreen(3), byDate(LocalDate.now() )
     * </pre>
     *
     * @return: Set
     */
    public Iterable<Showing> listFiltered (Filter... filters)  throws DataAccessException;

    /**
     * Filter die kan gebruikt worden door {@link #listFiltered}. Dergelijke filters
     * worden aangemaakt met de {@code by...} methoden
     */
    public interface Filter {

    }

    /**
     * Filter voor een specifieke zaal
     */
    public Filter byScreen(int screenId);

    /**
     * Filter voor een specifieke dag
     */
    public Filter byDay(LocalDate date);
    
    /**
     * Filter voor een specifiek uur
     */
    public Filter byTimeOfDay(LocalTime time);

    /**
     * Filter die films selecteert vanaf een bepaald tijdstip in de dag (inclusief)
     */
    public Filter fromTimeOfDay(LocalTime time);

    /**
     * Filter voor een specifieke film
     */
    public Filter byMovie(int movieId);

}
