package preferee.tests.RealServerTests;

import preferee.data.Movie;
import preferee.data.access.DataAccessException;
import preferee.data.access.DataAccessProvider;
import preferee.data.access.MovieDAO;
import preferee.data.access.Providers;

import java.net.URL;

/**
 * Created by domien on 22/03/2015.
 */
public class MovieDao_TEST {
    DataAccessProvider dap;
    public static void main(String[] args) {
        MovieDao_TEST test = new MovieDao_TEST();
        test.run();
    }


    public void run() {
        try(
                DataAccessProvider dap = Providers.createProvider(new URL("http://esari.ugent.be"));

        ) {
            System.out.println(this.getClass().getName());
            this.dap = dap;
            testGetters();
            testFilters();

        } catch (DataAccessException e) {
            System.err.println("DataAccesException opgevangen in TEST:" + e.getMessage());
            //e.printStackTrace();
        } catch (Exception e) {
            System.err.println(e.getClass() + ":exception" +" opgevangen in TEST" + e.getMessage());
        }

    }

    public void testGetters() {
        System.out.println("----------------\ntestGetters \n----------------");
        MovieDAO mdao = dap.getMovieDAO();
        System.out.println(mdao.getMovie(3).getTitle());
        //System.out.println(mdao.getMovie(333).getTitle());

        /**for (Movie movie : ((ServerMovieDAO) mdao).getall().values())
            System.out.println(movie.getTitle());*/

    }

    public void testFilters() {
        System.out.println("\n----------------\ntestFilters \n----------------");
        MovieDAO mdao = dap.getMovieDAO();
        Iterable<Movie> lijst = mdao.listFiltered(mdao.byGenre("Anilmation"));
        //Movie[] lijst = ((ServerMovieDAO)mdao).getall();
        int count = 1;
        for (Movie movie : lijst) {
            System.out.println(count +" : "+ movie.getTitle());
            count++;
        }
    }
}
