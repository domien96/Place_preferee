package preferee.data.access.testServer.dao;

import preferee.data.Showing;
import preferee.data.access.DataAccessException;
import preferee.data.access.ShowingDAO;
import preferee.data.access.testServer.testServer.LocalTestServer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by domien on 11/03/2015.
 * Bootst een database na zoals op de bioscoop-website
 */
public class TestShowingDAO extends TestAbstractDAO implements ShowingDAO {
    public TestShowingDAO(LocalTestServer server) {
        super(server);
    }

    @Override
    public Showing getShowing(int id) throws DataAccessException {
        Showing showing = server.getShowings().get(id);
        if (showing != null)
            return showing;
        else
            throw new DataAccessException("Showing met die id werd niet gevonden.");
    }

    @Override
    public Iterable<Showing> listFiltered(Filter... filters) throws DataAccessException {
        List<Showing> filtered = new LinkedList<>(server.getShowings().values()); // hier zullen we elementen uit verwijderen.
        List<Showing> showings = new ArrayList<>(filtered); // itereren , voorkomt concurrentmodificationexception
        for (Showing showing : showings ) {
            for (Filter filter : filters) {
                if (!((ShowingFilter) filter).isValid(showing)) {
                    filtered.remove(showing);
                }
            }
        }
        return filtered;
    }

    /**
     * Filter die kan gebruikt worden door {@link #listFiltered} om showings te filteren. Dergelijke filters
     * worden aangemaakt met de {@code by...} methoden
     */
    private interface ShowingFilter extends Filter{
        boolean isValid(Showing showing);
    }



    @Override
    public Filter byScreen(int screenId) {
        ShowingFilter filter = showing -> showing.getScreenId() == screenId;
        return filter;
    }

    @Override
    public Filter byDay(LocalDate date) {
        ShowingFilter filter = showing -> showing.getTime().toLocalDate().equals(date);
        return filter;
    }

    @Override
    public Filter byTimeOfDay(LocalTime time) {
        ShowingFilter filter = showing -> showing.getTime().toLocalTime().equals(time);
        return filter;
    }

    @Override
    public Filter fromTimeOfDay(LocalTime time) {
        ShowingFilter filter = showing -> showing.getTime().toLocalTime().toSecondOfDay() - time.toSecondOfDay() >= 0;
        return filter;
    }

    @Override
    public Filter byMovie(int movieId) {
        ShowingFilter filter = showing -> showing.getMovieId() == movieId;
        return filter;
    }
}
