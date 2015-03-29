package preferee.data.access.server;

import preferee.data.Screen;
import preferee.data.ScreenArray;
import preferee.data.access.DataAccessException;
import preferee.data.access.ScreenDAO;

/**
 * Created by domien on 11/03/2015.
 */
public class ServerScreenDAO extends ServerAbstractDAO<Screen,ScreenArray> implements ScreenDAO {

    public ServerScreenDAO(String resourceURL) { super(resourceURL, Screen.class, ScreenArray.class); }

    /**
     * De zaal die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke zaal niet bestaat.
     *
     * @param id: id
     */
    @Override
    public Screen getScreen(int id) throws DataAccessException {
        String url = this.itemList_URL + "/" + Integer.toString(id) + ".xml";
        return singleItemConverter.getItemFromURL(url);
    }

    /**
     * Alle zalen.
     */
    @Override
    public Iterable<Screen> listAll() throws DataAccessException {
        String url = this.itemList_URL + ".xml";
        return multipleItemsConverter.getItemFromURL(url).getItemsAsMap().values();
    }
}
