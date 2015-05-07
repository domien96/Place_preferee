package preferee.tests.FakeServerTests;

import preferee.data.Movie;
import preferee.data.access.DataAccessException;
import preferee.data.access.DataAccessProvider;
import preferee.data.access.MovieDAO;
import preferee.data.access.Providers;

/**
 * Created by Domien Van Steendam on 19/03/2015.
 */
public class MovieDao_TEST {
    DataAccessProvider dap;
    public static void main(String[] args) {
        MovieDao_TEST test = new MovieDao_TEST();
        test.run();
    }


    public void run() {
        try(
                DataAccessProvider dap = Providers.createTestProvider()
        ) {
            this.dap = dap;
            testGetters();
            testFilters();

        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage() + "exception");
        }

    }

    public void testGetters() {
        System.out.println("----------------\ntestGetters \n----------------");
        MovieDAO mdao = dap.getMovieDAO();
        System.out.println(mdao.getMovie(5).getTitle());
        //System.out.println(mdao.getMovie(333).getTitle());
    }

    public void testFilters() {
        System.out.println("\n----------------\ntestFilters \n----------------");
        MovieDAO mdao = dap.getMovieDAO();
        Iterable<Movie> lijst = mdao.listFiltered(mdao.byRating("R"), mdao.byYear("2014"),mdao.byTitle("  ki ng     "));

        for (Movie movie : lijst) {
            System.out.println(movie.getTitle());
        }
    }
}
