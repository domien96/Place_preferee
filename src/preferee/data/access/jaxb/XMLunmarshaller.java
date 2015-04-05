package preferee.data.access.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Haal de XML-files uit een gegeven stream /URL en zet deze vervolgens XML-files om in objecten van type T.
 * Deze klasse werkt deels dus ook als een GET_requester
 * Created by domien on 22/03/2015.
 */
public class XMLunmarshaller<T> {

    /**
     *
     * @param klasseObject : een klasse-object van T. (deze is noodzakelijk om te weten welke objecten de JAXB kan verwachten.)
     */
    public XMLunmarshaller(Class<T> klasseObject) {
        this.klasseObject = klasseObject;
    }

    /**
     * deze is noodzakelijk om te weten welke objecten de JAXB kan verwachten. !!!
     */
    Class<T> klasseObject;

    /**
     * Zet objecten om met gegeven inputstream.
     * @param xmlStream : Stream met het xml-bestand
     * @return object van type T of null indien het bestand niet leesbaar of niet het verwachte formaat heeft..
     */
    public T unmarshall(InputStream xmlStream) {
        JAXBContext jc;
        T item;
        try {
            jc = JAXBContext.newInstance(klasseObject);
            item = (T) jc.createUnmarshaller().unmarshal(xmlStream); // negeer cast-warning hier
        } catch (JAXBException e) {
            return null;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
            // dit zou niet mogen gebeuren, de programmeur zou deze fout moeten voorkomen.
        }
        return item;
    }

    /**
     * Opent inputstream met een online XML file om vervolgens in een object van klasse T om te zetten.
     * @param url: url-object naar het bestand, kan online zijn (http) of lokaal (file).
     * @return zie (@code unmarshall(Inputstream))
     */
    public T unmarshall(URL url) throws IOException {
        try (InputStream XML_Stream = url.openStream())
        {
            // opgehaald xml-bestand omzetten in een Java-object.
            return unmarshall(XML_Stream);
        }
    }

    /**
     * Maakt van de string-url een URL-object.
     * @param url: string-url naar het bestand, kan online zijn (http) of lokaal (file).
     * @return zie (@code unmarshall(Inputstream))
     */
    public T unmarshall(String url) throws IOException {
            return unmarshall(new URL(url));
    }

}
