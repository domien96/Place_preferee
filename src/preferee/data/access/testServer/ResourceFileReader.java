package preferee.data.access.testServer;

import preferee.data.access.DataAccessException;
import preferee.data.access.IO_transfer.jaxb.XMLUnpacker;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Klasse die lokale XML-bestanden om kan zetten in objecten van klasse T.
 * Voorkomt voornamelijk het meermaals gebruik van deze code in de verschillende dao's.
 * Created by domien on 22/03/2015.
 */
public class ResourceFileReader<T> {
    /**
     * @param klasseObject de unpacker moet weten welke klassen hij kan verwachten van de objecten die hij "unpackt"/maakt.
     */
    public ResourceFileReader(Class<T> klasseObject) {
        unpacker = new XMLUnpacker<T>(klasseObject);
    }

    /**
     *  Kan een inputstream met een XML-bestand omzetten naar een item van klasse T.
     */
    XMLUnpacker<T> unpacker;

    /**
     * Opent inputstream met een lokale XML file en zet deze om in een object van klasse T mbv de unpacker.
     * @param pad
     * @return converted object from XML, can be casted.
     */
    public T getItemFrom(String pad) throws DataAccessException {
        try (InputStream XML_Stream = Files.newInputStream(Paths.get(pad))) // dit is het enige lijntje dat verschilt met de ReaourceWebReader
                                                                            // in een vorige versie gebruikte ik 2 verschillende XMLUnpackers, maar dankzij deze abstracte methode(zie superklasse) kan ik
        {
            // opgehaald xml-bestand omzetten in een Java-object.
            return unpacker.unpack(XML_Stream);
        } catch (IOException e) {
            throw new DataAccessException("( originele error: " + e.getClass() +" ) " + e.getMessage() + ": Kon Bestand niet ophalen of lezen." );
        }
    }
}
