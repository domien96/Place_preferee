package preferee.data.access.testServer.dao;

import preferee.data.Screen;
import preferee.data.access.DataAccessException;
import preferee.data.access.ScreenDAO;
import preferee.data.access.testServer.testServer.LocalTestServer;

import java.util.ArrayList;

/**
 * Created by domien on 11/03/2015.
 * Bootst een database na zoals op de bioscoop-website
 */
public class TestScreenDAO extends TestAbstractDAO implements ScreenDAO {
    public TestScreenDAO(LocalTestServer server) {
        super(server);
    }

    @Override
    public Screen getScreen(int id) throws DataAccessException {
        Screen screen = server.getScreens().get(id);
        if (screen != null)
            return screen;
        else
            throw new DataAccessException("Screen met die id werd niet gevonden.");
    }

    @Override
    public Iterable<Screen> listAll() throws DataAccessException {
        return new ArrayList<Screen>(server.getScreens().values()); // defensive copy , arraylist om snel over te itereren.
    }
}
