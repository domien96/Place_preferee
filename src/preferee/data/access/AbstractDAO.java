package preferee.data.access;

import preferee.data.Resource;
import preferee.data.access.jaxb.XMLunmarshaller;

import java.util.Collection;

/**
 * Created by Domien Van Steendam on 11/03/2015.
 * Abstracte bovenklasse van alle DAO'
 * Typeparameter singleResource : stelt het type van één item. Moet de Resource-interface implementeren
 * Typeparameter multipleResource : stelt het type voor van meerdere items (itemCollection) Moet de ResourceCollection-interface implementeren
 */
public abstract class AbstractDAO<singleResource, multipleResource> {

    protected AbstractDAO(String location, Class<singleResource> singleitem, Class<multipleResource> multipleItem) {
        this.itemsLocation = location;
        singleResourceUnmarshaller = new XMLunmarshaller<singleResource>(singleitem);
        multipleResourceUnmarshaller = new XMLunmarshaller<multipleResource>(multipleItem);
    }

    /**
     * URL/pad naar de lijst van objecten specifiek voor deze DAO.
     * bv. www.esari.ugent.be/movies (= lijst van alle movies) voor serverDAO's
     * bv. resources/movies.xml voor testDAO's
     */
    protected String itemsLocation;

    /**
     *  Kan een online XML-bestand omzetten naar respectievelijk één resource-object, meerdere resource-objecten.
     *  Zo'n downloader moet wel weten welke klasse objecten hij moet vertalen aangezien de downloader met type variabelen werkt.
     */
    protected XMLunmarshaller<singleResource> singleResourceUnmarshaller;
    protected XMLunmarshaller<multipleResource> multipleResourceUnmarshaller;


    /**
     * Verkrijg resource op id
     */
    protected singleResource getResource(int id) throws DataAccessException {
        for (singleResource item : listAll()){
            if (((Resource) item).getId() == id) {
                return item;
            }
        }
        throw new DataAccessException("(Programmeerfout) Item met die id werd niet gevonden. " );
    }

    /**
     * Geeft alle movies terug. Verschilt voor iedere MovieDao.
     * @return
     */
    public abstract Collection<singleResource> listAll();


}
