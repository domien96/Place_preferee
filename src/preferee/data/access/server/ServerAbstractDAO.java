package preferee.data.access.server;

import preferee.data.access.persistent_xml_processing.jaxb.XMLconverter;

/**
 * Created by domien on 11/03/2015.
 * Abstracte bovenklasse van alle JDBC-DAO'
 * Typeparameter S (single) : stelt het type van één item.
 * Typeparameter M (multiple) : stelt het type voor van meerdere items (itemArray)
 */
public class ServerAbstractDAO<S,M> {

    protected ServerAbstractDAO(String serverURL, Class singleitem, Class multipleItem) {
        this.itemList_URL = serverURL;
        singleItemConverter = new XMLconverter<>(singleitem);
        multipleItemsConverter = new XMLconverter<>(multipleItem);
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
    XMLconverter<S> singleItemConverter;
    XMLconverter<M> multipleItemsConverter;

}
