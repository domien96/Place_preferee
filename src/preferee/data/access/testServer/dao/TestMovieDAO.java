package preferee.data.access.testServer.dao;

import preferee.data.Movie;
import preferee.data.access.AbstractMovieDAO;
import preferee.data.access.DataAccessException;
import preferee.data.access.MovieDAO;

import java.util.Collection;

/**
 * Created by Domien Van Steendam on 11/03/2015.
 * Bootst een database na zoals op de bioscoop-website
 */
public class TestMovieDAO extends AbstractMovieDAO implements MovieDAO{

    public TestMovieDAO(String movieLocation) {
        super(movieLocation);
    }

    /**
     * Geef de film terug die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke film niet bestaat.
     *
     * @param id
     */
    @Override
    public Movie getMovie(int id) throws DataAccessException {
        return getResource(id);
    }

    /**
     * Geeft alle movies terug. Verschilt voor iedere individuele MovieDao.
     * @return
     */
    public Collection<Movie> listAll() {
        return multipleResourceUnmarshaller.unmarshall(getClass().getResourceAsStream(itemsLocation)).getItemsAsList();
    }

}
