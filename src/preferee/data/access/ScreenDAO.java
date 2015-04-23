package preferee.data.access;

import preferee.data.Screen;

/**
 * Data access object voor het opvragen van zaalgegevens.
 *
 * Created by Domien Van Steendam
 */
public interface ScreenDAO {

    /**
     * De zaal die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke zaal niet bestaat.
     */
    public Screen getScreen(int id) throws DataAccessException;

    /**
     * Alle zalen.
     * @return: Set
     */
    public Iterable<Screen> listAll ()  throws DataAccessException;

}
