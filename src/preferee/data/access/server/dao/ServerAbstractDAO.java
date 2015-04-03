package preferee.data.access.server.dao;

import preferee.data.access.jaxb.XMLunmarshaller;

/**
 * Created by domien on 11/03/2015.
 * Abstracte bovenklasse van alle JDBC-DAO'
 * Typeparameter Resource : stelt het type van één item.
 * Typeparameter ResourceArray : stelt het type voor van meerdere items (itemArray)
 */
public class ServerAbstractDAO<Resource, ResourceArray> {

    protected ServerAbstractDAO(String serverURL, Class<Resource> singleitem, Class<ResourceArray> multipleItem) {
        this.itemList_URL = serverURL;
        singleResourceUnmarshaller = new XMLunmarshaller<Resource>(singleitem);
        ResourceArrayUnmarshaller = new XMLunmarshaller<ResourceArray>(multipleItem);
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
    XMLunmarshaller<ResourceArray> ResourceArrayUnmarshaller;


    /**
     * Verkrijg resource op id
     */
    // todo

}
