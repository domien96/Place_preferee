package preferee.data.access.server;

import preferee.data.Screen;
import preferee.data.ScreenArray;
import preferee.data.access.DataAccessException;
import preferee.data.access.ScreenDAO;

/**
 * Created by domien on 11/03/2015.
 */
public class ServerScreenDAO extends ServerAbstractDAO implements ScreenDAO {

    public ServerScreenDAO(String resourceURL) { super(resourceURL); }

    /**
     *  Kan een online XML-bestand omzetten naar respectievelijk één screen-object, meerdere screen-objecten.
     *  Zo'n downloader moet wel weten welke klasse objecten hij moet vertalen aangezien de downloader met type variabelen werkt.
     */
    ResourceWebReader<Screen> singleResourceWebReader = new ResourceWebReader<>(Screen.class);
    ResourceWebReader<ScreenArray> multipleResourceWebReader = new ResourceWebReader<>(ScreenArray.class);


    /**
     * De zaal die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke zaal niet bestaat.
     *
     * @param id
     */
    @Override
    public Screen getScreen(int id) throws DataAccessException {
        String url = this.orders_URL + "/" + Integer.toString(id) + ".xml";
        return singleResourceWebReader.getItemFromURL(url);
    }

    /**
     * Alle zalen.
     */
    @Override
    public Iterable<Screen> listAll() throws DataAccessException {
        String url = this.orders_URL + ".xml";
        return multipleResourceWebReader.getItemFromURL(url).getItemsAsMap().values();
    }
}
