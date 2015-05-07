package preferee.data.access;

import preferee.data.Screen;
import preferee.data.ScreenCollection;

/**
 * Bovenklasse van alle screenDAO's
 * Created by Domien on 5/05/2015.
 */
public abstract class AbstractScreenDAO extends AbstractDAO<Screen, ScreenCollection> implements ScreenDAO {
    protected AbstractScreenDAO(String serverURL) {
        super(serverURL, Screen.class, ScreenCollection.class);
    }


}
