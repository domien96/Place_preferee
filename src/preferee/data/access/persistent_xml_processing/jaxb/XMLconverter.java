package preferee.data.access.persistent_xml_processing.jaxb;

import preferee.data.access.DataAccessException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Haal de XML-files uit een gegeven stream /URL en zet deze vervolgens XML-files om in objecten van type T.
 * Created by domien on 22/03/2015.
 */
public class XMLconverter<T> {

    /**
     *
     * @param klasseObject : een klasse-object van T. (deze is noodzakelijk om te weten welke objecten de JAXB kan verwachten.)
     */
    public XMLconverter(Class<T> klasseObject) {
        this.klasseObject = klasseObject;
    }

    /**
     * deze is noodzakelijk om te weten welke objecten de JAXB kan verwachten. !!!
     */
    Class<T> klasseObject;

    /**
     * Zet objecten om met gegeven inputstream.
     * @param xmlStream : Stream met het xml-bestand
     * @return object van type T
     */
    public T getItemFromStream(InputStream xmlStream) {
        JAXBContext jc;
        T item;
        try {
            jc = JAXBContext.newInstance(klasseObject);
            item = (T) jc.createUnmarshaller().unmarshal(xmlStream);
        } catch (JAXBException e) {
            throw new DataAccessException(e.getMessage());
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new Error("programeerfout");
            // dit zou niet mogen gebeuren, de programmeur zou deze fout moeten voorkomen.
        }
        return item;
    }

    /**
     * Opent inputstream met een online XML file om vervolgens in een object van klasse T om te zetten.
     * @param url: url naar het bestand, kan online zijn (http) of lokaal (file).
     * @return converted object from XML, can be casted.
     */
    public T getItemFromURL(String url) throws DataAccessException {
        try (InputStream XML_Stream = new URL(url).openStream())
        {
            // opgehaald xml-bestand omzetten in een Java-object.
            return getItemFromStream(XML_Stream);
        } catch (IOException e) {
            throw new DataAccessException("( originele error: " + e.getClass() +" ) " + e.getMessage() + ": Kon Bestand niet ophalen of lezen." );
        }
    }



}
