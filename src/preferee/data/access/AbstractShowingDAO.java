package preferee.data.access;

import preferee.data.Showing;
import preferee.data.ShowingCollection;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Bovenklasse van alle ShowingDAO's
 * Created by Domien on 5/05/2015.
 */
public abstract class AbstractShowingDAO extends AbstractDAO<Showing, ShowingCollection> implements ShowingDAO {
    protected AbstractShowingDAO(String serverURL) {
        super(serverURL, Showing.class, ShowingCollection.class);
    }

    @Override
    public Collection<Showing> listFiltered(Filter... filters) throws DataAccessException {
        List<Showing> filtered = new LinkedList<>(listAll()); // hier zullen we elementen uit verwijderen.
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
     * Filter die films selecteert die vanaf nu vandaag en de komende dagen gespeeld worden.
     * bv. fromTimeOfDay(7) geeft alle showings vanaf nu + de eerste volgende 6 dagen = deze week vanaf nu;
     * @param days : het aantal dagen inclusief vandaag. Waarde 1 geeft alle showings die vandaag nog te bekijken zijn.
     */
    public Iterable<Showing> fromNow(int days) {
        if (days<0){
            throw new IllegalArgumentException("Amount of days can't be negative");
        }
        Collection<Showing> out = listFiltered(byTimeOfDay(LocalTime.now())); // wordt vandaag nog gespeeld
        LocalDate day = LocalDate.now();
        for (int i=1; i<days; i++) { // i=1; want al 1 dag is hierboven al geregeld
            day = day.plusDays(1);
            out.addAll(listFiltered(byDay(day)));
        }
        return out;
    }


    /**
     * Filter die kan gebruikt worden door {@link #listFiltered} om showings te filteren. Dergelijke filters
     * worden aangemaakt met de {@code by...} methoden
     */
    private interface ShowingFilter extends Filter {
        boolean isValid(Showing showing);
    }



    @Override
    public ShowingDAO.Filter byScreen(int screenId) {
        ShowingFilter filter = showing -> showing.getScreenId() == screenId;
        return filter;
    }

    @Override
    public ShowingDAO.Filter byDay(LocalDate date) {
        ShowingFilter filter = showing -> showing.getTime().toLocalDate().equals(date);
        return filter;
    }

    @Override
    public ShowingDAO.Filter byTimeOfDay(LocalTime time) {
        ShowingFilter filter = showing -> showing.getTime().toLocalTime().equals(time);
        return filter;
    }

    @Override
    public ShowingDAO.Filter fromTimeOfDay(LocalTime time) {
        ShowingFilter filter = showing -> showing.getTime().toLocalTime().toSecondOfDay() - time.toSecondOfDay() >= 0;
        return filter;
    }

    @Override
    public ShowingDAO.Filter byMovie(int movieId) {
        ShowingFilter filter = showing -> showing.getMovieId() == movieId;
        return filter;
    }
}
