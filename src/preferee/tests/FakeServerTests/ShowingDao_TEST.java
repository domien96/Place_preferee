package preferee.tests.FakeServerTests;

import preferee.data.Showing;
import preferee.data.access.DataAccessException;
import preferee.data.access.DataAccessProvider;
import preferee.data.access.Providers;
import preferee.data.access.ShowingDAO;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Domien Van Steendam on 19/03/2015.
 */
public class ShowingDao_TEST {
    public static void main(String[] args) {new ShowingDao_TEST().run();}


    ShowingDAO dao ;

    public void run() {
        try(DataAccessProvider provider = Providers.createTestProvider()) {

            dao = provider.getShowingDAO();
            testGetter();
            testGetAll();
            testFilters();
            testDateFilters();
            testTimeFilters();
            testFromTimeFilters();
            testFromNow();

        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage() + "exception");
        }
    }

    public void testGetter() {
        System.out.println("\n----------------\ntestGetter \n----------------");
        int id = 5;
        System.out.println(dao.getShowing(id).getMovieId());
        System.out.println(  Providers.createTestProvider().getMovieDAO().getMovie(dao.getShowing(id).getMovieId() ).getTitle());
        //System.out.println(dao.getShowing(333));
    }

    public void testGetAll() {
        System.out.println("\n----------------\ntestGetAll \n----------------");
        for (int i = 1; i<19; i++) {
            Showing item = dao.getShowing(i);
            System.out.println(i + " : " + item.getTime());
        }
    }

    public void testFilters() {
        System.out.println("\n----------------\ntestScreenFilters \n----------------");
        Iterable<Showing> lijst = dao.listFiltered(dao.byScreen(4));
        for ( Showing showing : lijst ) {
            System.out.println(showing.getId());
        }

        System.out.println("\n----------------\ntestMovieFilters \n----------------");
        lijst = dao.listFiltered(dao.byMovie(5));
        for ( Showing showing : lijst ) {
            System.out.println(showing.getId());
        }
    }

    public void testDateFilters() {
        System.out.println("\n----------------\ntestDateFilters \n----------------");
        LocalDate date = LocalDate.of(2015, 5, 22);
        Iterable<Showing> lijst = dao.listFiltered(dao.byDay(date));
        for ( Showing showing : lijst ) {
            System.out.println(showing.getId());
        }
    }

    public void testTimeFilters() {
        System.out.println("\n----------------\ntestTimeFilters \n----------------");
        LocalTime time = LocalTime.of(20,0,0);
        Iterable<Showing> lijst = dao.listFiltered(dao.byTimeOfDay(time));
        for ( Showing showing : lijst ) {
            System.out.println(showing.getId());
        }
    }

    public void testFromTimeFilters() {
        System.out.println("\n----------------\ntestFromTimeFilters \n----------------");
        LocalTime time = LocalTime.of(20,0,0);
        Iterable<Showing> lijst = dao.listFiltered(dao.fromTimeOfDay(time));
        for ( Showing showing : lijst ) {
            System.out.println(showing.getId());
        }
    }

    public void testFromNow() {
        System.out.println("\n----------------\ntestFromNow \n----------------");
        Iterable<Showing> lijst = dao.listFiltered(dao.fromNow(Integer.MAX_VALUE));
        for ( Showing showing : lijst ) {
            System.out.println(showing.getId());
        }
    }
}
