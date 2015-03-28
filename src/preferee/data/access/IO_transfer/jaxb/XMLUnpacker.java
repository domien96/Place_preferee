package preferee.data.access.IO_transfer.jaxb;

import preferee.data.access.DataAccessException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.InputStream;

/**
 * Zet XML-files om in objecten van type T.
 * Created by domien on 22/03/2015.
 */
public class XMLUnpacker<T> {

    /**
     *
     * @param klasseObject : een klasse-object van T. (deze is noodzakelijk om te weten welke objecten de JAXB kan verwachten.)
     */
    public XMLUnpacker(Class<T> klasseObject) {
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
    public T unpack(InputStream xmlStream) {
        JAXBContext jc = null;
        T item = null;
        try {
            jc = JAXBContext.newInstance(klasseObject);
            item = (T) jc.createUnmarshaller().unmarshal(xmlStream);
        } catch (JAXBException e) {
            throw new DataAccessException(e.getMessage());
        }
        return item;
    }
}
