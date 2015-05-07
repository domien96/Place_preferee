package preferee.data.access.server.dao;

import preferee.data.Movie;
import preferee.data.access.AbstractMovieDAO;
import preferee.data.access.DataAccessException;
import preferee.data.access.MovieDAO;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Domien Van Steendam on 11/03/2015.
 */
public class serverMovieDAO extends AbstractMovieDAO implements MovieDAO {

    public serverMovieDAO(String movieURL) { super(movieURL); }

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

    @Override
    public Collection<Movie> listAll() {
        try {
            return multipleResourceUnmarshaller.unmarshall(itemsLocation+".xml").getItemsAsList();
        } catch (IOException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}


