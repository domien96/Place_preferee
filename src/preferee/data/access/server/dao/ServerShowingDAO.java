package preferee.data.access.server.dao;

import preferee.data.Showing;
import preferee.data.access.AbstractShowingDAO;
import preferee.data.access.DataAccessException;
import preferee.data.access.ShowingDAO;

import java.io.IOException;
import java.util.Collection;

/**
 * ShowingDAO voor de productie server.
 * Created by Domien Van Steendam on 11/03/2015.
 */
public class serverShowingDAO extends AbstractShowingDAO implements ShowingDAO {

    public serverShowingDAO(String resourceURL) { super(resourceURL); }


    /**
     * De vertoning terug die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke vertoning niet bestaat.
     *
     * @param id: id
     */
    @Override
    public Showing getShowing(int id) throws DataAccessException {
        return getResource(id);
    }

    @Override
    public Collection<Showing> listAll() {
        try {
            return multipleResourceUnmarshaller.unmarshall(itemsLocation+".xml").getItemsAsList();
        } catch (IOException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
