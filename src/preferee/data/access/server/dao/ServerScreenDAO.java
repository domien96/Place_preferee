package preferee.data.access.server.dao;

import preferee.data.Screen;
import preferee.data.ScreenCollection;
import preferee.data.access.DataAccessException;
import preferee.data.access.ScreenDAO;

import java.io.IOException;

/**
 * Created by domien Van Steendam on 11/03/2015.
 */
public class ServerScreenDAO extends ServerAbstractDAO<Screen,ScreenCollection> implements ScreenDAO {

    public ServerScreenDAO(String resourceURL) { super(resourceURL, Screen.class, ScreenCollection.class); }

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
    public Iterable<Screen> listAll() throws DataAccessException {
        String url = this.itemList_URL + ".xml";
        try {
            return multipleResourceUnmarshaller.unmarshall(url).getItemsAsList();
        } catch (IOException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
