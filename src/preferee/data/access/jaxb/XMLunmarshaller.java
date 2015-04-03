package preferee.data.access.jaxb;

import preferee.data.access.DataAccessException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
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
     * @return object van type T of null indien de xml-root van type "nil-classes" is.
     */
    public T unmarshall(InputStream xmlStream) {
        JAXBContext jc;
        T item;
        try {
            jc = JAXBContext.newInstance(klasseObject);
            item = (T) jc.createUnmarshaller().unmarshal(xmlStream);
        } catch (JAXBException e) {
            if (isNilClasses(xmlStream)) // jaxb mogelijks door nil-classes
                return null;
            throw new DataAccessException(e.getMessage());
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new Error(e.getClass() + ": programeerfout");
            // dit zou niet mogen gebeuren, de programmeur zou deze fout moeten voorkomen.
        }
        //todo nil controle
        return item;
    }

    /**
     * Opent inputstream met een online XML file om vervolgens in een object van klasse T om te zetten.
     * @param url: url-object naar het bestand, kan online zijn (http) of lokaal (file).
     * @return converted object from XML, can be casted.
     */
    public T unmarshall(URL url) throws DataAccessException {
        try (InputStream XML_Stream = url.openStream())
        {
            // opgehaald xml-bestand omzetten in een Java-object.
            return unmarshall(XML_Stream);
        } catch (IOException e) {
            throw new DataAccessException("Kon Bestand niet ophalen of lezen." );
        }
    }

    /**
     * Maakt van de string-url een URL-object.
     * @param url: string-url naar het bestand, kan online zijn (http) of lokaal (file).
     * @return converted object from XML, can be casted.
     */
    public T unmarshall(String url) throws DataAccessException {
        try {
            return unmarshall(new URL(url));
        } catch (MalformedURLException e) {
            throw new DataAccessException("geen geldige String-URL");
        }
    }

    /**
     * Kijkt of de gegeven stream (XML) zijn root-element van type nil-classes is.
     * Nil-classes is een lege lijst.
     * @param stream
     * @return
     */
    public boolean isNilClasses(InputStream stream) {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));) {
            buffer.readLine();
            return buffer.readLine().contains("nil-classes");
        } catch (IOException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

}
