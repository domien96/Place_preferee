package preferee.data.access.server;

import preferee.data.access.DataAccessException;
import preferee.data.access.IO_transfer.jaxb.XMLUnpacker;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Klasse die XML-bestanden op het world wide web om kan zetten in objecten van klasse T.
 * Voorkomt voornamelijk het meermaals gebruik van deze code in de verschillende dao's.
 * Created by domien on 22/03/2015.
 */
public class ResourceWebReader<T> {
    /**
     * @param klasseObject de unpacker moet weten welke klassen hij kan verwachten van de objecten die hij "unpackt"/maakt.
     */
    public ResourceWebReader(Class<T> klasseObject) {
        unpacker = new XMLUnpacker<T>(klasseObject);
    }

    /**
     *  Kan een inputstream met een XML-bestand omzetten naar een item van klasse T.
     */
    XMLUnpacker<T> unpacker;

    /**
     * Opent inputstream met een online XML file en zet deze om in een object van klasse T mbv de unpacker.
     * @param url
     * @return converted object from XML, can be casted.
     */
    public T getItemFromURL(String url) throws DataAccessException {
        try (InputStream XML_Stream = new URL(url).openStream();)
        {
            // opgehaald xml-bestand omzetten in een Java-object.
            return unpacker.unpack(XML_Stream);
        } catch (IOException e) {
            throw new DataAccessException("( originele error: " + e.getClass() +" ) " + e.getMessage() + ": Kon Bestand niet ophalen of lezen." );
        }
    }

    public T getItemFromStream(InputStream stream) throws DataAccessException {
        return unpacker.unpack(stream);
    }
}
