package preferee.data.access.IO_transfer;

import preferee.data.access.DataAccessException;
import preferee.data.access.IO_transfer.jaxb.XMLUnpacker;

/**
 * Abstracte bovenKlasse voor het lezen van XML-bestanden en met behulp van een XMLUnpacker om kan zetten in objecten van klasse T.
 * Voorkomt voornamelijk het meermaals gebruik van deze code in de verschillende dao's.
 * Created by domien on 24/03/2015.
 */
public abstract class ResourceReader<T> {
    /**
     * @param klasseObject de unpacker moet weten welke klassen hij kan verwachten van de objecten die hij "unpackt"/maakt.
     */
    public ResourceReader(Class<T> klasseObject) {
        unpacker = new XMLUnpacker<T>(klasseObject);
    }

    /**
     *  Kan een inputstream met een XML-bestand omzetten naar een item van klasse T.
     */
    XMLUnpacker<T> unpacker;

    /**
     * Opent inputstream met een XML file en zet deze om in een object van klasse T mbv de unpacker.
     * @param locatie (String) van het XML-bestand.
     * @return converted object from XML, can be casted.
     */
    public abstract T getItemFrom(String locatie) throws DataAccessException;
}
