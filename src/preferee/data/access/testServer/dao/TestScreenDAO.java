package preferee.data.access.testServer.dao;

import preferee.data.Screen;
import preferee.data.access.AbstractScreenDAO;
import preferee.data.access.DataAccessException;
import preferee.data.access.ScreenDAO;

import java.util.Collection;

/**
 * Created by Domien Van Steendam on 11/03/2015.
 * Bootst een database na zoals op de bioscoop-website
 */
public class TestScreenDAO extends AbstractScreenDAO implements ScreenDAO {
    public TestScreenDAO(String screensLocation) {
        super(screensLocation);
    }

    @Override
    public Screen getScreen(int id) throws DataAccessException {
        return getResource(id);
    }

    @Override
    public Collection<Screen> listAll() throws DataAccessException {
        return multipleResourceUnmarshaller.unmarshall(getClass().getResourceAsStream(itemsLocation)).getItemsAsList();
    }
}
