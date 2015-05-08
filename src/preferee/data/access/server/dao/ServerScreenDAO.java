package preferee.data.access.server.dao;

import preferee.data.Screen;
import preferee.data.access.AbstractScreenDAO;
import preferee.data.access.DataAccessException;
import preferee.data.access.ScreenDAO;

import java.io.IOException;
import java.util.Collection;

/**
 * ScreenDAO voor de productie server.
 * Created by Domien Van Steendam on 11/03/2015.
 */
public class serverScreenDAO extends AbstractScreenDAO implements ScreenDAO {

    public serverScreenDAO(String resourceURL) { super(resourceURL); }

    /**
     * De zaal die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke zaal niet bestaat.
     *
     * @param id: id
     */
    @Override
    public Screen getScreen(int id) throws DataAccessException {
        return getResource(id);
    }

    /**
     * Alle zalen.
     */
    @Override
    public Collection<Screen> listAll() throws DataAccessException {
        String url = this.itemsLocation + ".xml";
        try {
            return multipleResourceUnmarshaller.unmarshall(url).getItemsAsList();
        } catch (IOException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
