package preferee.data.access.testServer.dao;

import preferee.data.Showing;
import preferee.data.access.AbstractShowingDAO;
import preferee.data.access.DataAccessException;
import preferee.data.access.ShowingDAO;

import java.util.Collection;

/**
 * Created by Domien Van Steendam on 11/03/2015.
 * Bootst een database na zoals op de bioscoop-website
 */
public class TestShowingDAO extends AbstractShowingDAO implements ShowingDAO {
    public TestShowingDAO(String showingsLocation) {
        super(showingsLocation);
    }

    @Override
    public Showing getShowing(int id) throws DataAccessException {
        return getResource(id);
    }

    @Override
    public Collection<Showing> listAll() {
        return multipleResourceUnmarshaller.unmarshall(getClass().getResourceAsStream(itemsLocation)).getItemsAsList();
    }
}
