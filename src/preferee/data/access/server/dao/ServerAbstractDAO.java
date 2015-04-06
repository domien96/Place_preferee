package preferee.data.access.server.dao;

import preferee.data.access.DataAccessException;
import preferee.data.access.jaxb.XMLunmarshaller;

import java.io.IOException;

/**
 * Created by domien on 11/03/2015.
 * Abstracte bovenklasse van alle JDBC-DAO'
 * Typeparameter Resource : stelt het type van één item.
 * Typeparameter ResourceArray : stelt het type voor van meerdere items (itemArray)
 */
public abstract class ServerAbstractDAO<Resource, ResourceArray> {

    protected ServerAbstractDAO(String serverURL, Class<Resource> singleitem, Class<ResourceArray> multipleItem) {
        this.itemList_URL = serverURL;
        singleResourceUnmarshaller = new XMLunmarshaller<Resource>(singleitem);
        multipleResourceUnmarshaller = new XMLunmarshaller<ResourceArray>(multipleItem);
    }

    /**
     * URL naar de lijst van objecten specifiek voor deze DAO.
     * bv. www.esari.ugent.be/movies (= lijst van alle movies)
     */
    protected String itemList_URL;

    /**
     *  Kan een online XML-bestand omzetten naar respectievelijk één resource-object, meerdere resource-objecten.
     *  Zo'n downloader moet wel weten welke klasse objecten hij moet vertalen aangezien de downloader met type variabelen werkt.
     */
    XMLunmarshaller<Resource> singleResourceUnmarshaller;
    XMLunmarshaller<ResourceArray> multipleResourceUnmarshaller;


    /**
     * Verkrijg resource op id
     */
    protected Resource getResource(int id) throws DataAccessException {
        String url = this.itemList_URL + "/" + Integer.toString(id) + ".xml";
        Resource item = null;
        try {
            item = singleResourceUnmarshaller.unmarshall(url);
            return item;
        } catch (IOException e) { // zou niet mogen gebeuren
            throw new DataAccessException("(Programmeerfout) Item met die id werd niet gevonden. " + e.getMessage());
        }
    }

}
