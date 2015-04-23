package preferee.tests.RealServerTests;

import preferee.data.Showing;
import preferee.data.access.DataAccessException;
import preferee.data.access.DataAccessProvider;
import preferee.data.access.Providers;
import preferee.data.access.ShowingDAO;

import java.net.URL;
import java.time.LocalTime;

/**
 * Created by Domien Van Steendam on 23/03/2015.
 */
public class ShowingDao_TEST {
    DataAccessProvider dap;
    public static void main(String[] args) {

        new ShowingDao_TEST().run();
    }


    public void run() {
        try(
                DataAccessProvider dap = Providers.createProvider(new URL("http://esari.ugent.be"));

        ) {
            System.out.println(this.getClass().getName());
            this.dap = dap;
            testGetters();
            testFilters();
            testDateFilters();

        } catch (DataAccessException e) {
            System.err.println("DataAccesException opgevangen in TEST:" + e.getMessage());
            //e.printStackTrace();
        } catch (Exception e) {
            System.err.println(e.getClass() + ":exception" +" opgevangen in TEST" + e.getMessage());
        }

    }

    public void testGetters() {
        System.out.println("----------------\ntestGetters \n----------------");
        ShowingDAO dao = dap.getShowingDAO();
        System.out.println(dao.getShowing(1).getId());
        //System.out.println(dao.getMovie(333).getTitle());

        /**for (Movie movie : ((ServerMovieDAO) dao).getall().values())
         System.out.println(movie.getTitle());*/

    }

    public void testFilters() {
        System.out.println("\n----------------\ntestFilters \n----------------");
        ShowingDAO dao = dap.getShowingDAO();
        //Iterable<Showing> lijst = dao.listFiltered(dao.byMovie(4));
        Iterable<Showing> lijst = dao.listFiltered(dao.byScreen(4));
        int count = 1;
        for (Showing showing : lijst) {
            System.out.println(count +" : "+ showing.getId());
            count++;
        }
    }

    public void testDateFilters() {
        System.out.println("\n----------------\ntestDateFilters \n----------------");
        ShowingDAO dao = dap.getShowingDAO();
        //Iterable<Showing> lijst = dao.listFiltered(dao.byTimeOfDay(LocalTime.of(20,00))); // op tijdstip
        //Iterable<Showing> lijst = dao.listFiltered(dao.byDay(LocalDate.of(2015,5,24)));   // op dag
        Iterable<Showing> lijst = dao.listFiltered(dao.fromTimeOfDay(LocalTime.of(20, 00))); // vanaf tijdstip
        int count = 1;
        for (Showing showing : lijst) {
            System.out.println(count +" : "+ showing.getId());
            count++;
        }
    }
}
