package preferee.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Zaal waarin films worden vertoond.
 */
@XmlRootElement(name="screen")
public class Screen implements Resource {

    @XmlElement(name="id")
    private int id;

    @XmlElement(name="name")
    private String name;

    @XmlElement(name="rows")
    private int numberOfRows;

    @XmlElement(name="cols")
    private int numberOfColumns;

    /**
     * Uniek identificatienummer van deze zaal (voor intern gebruik).
     */
    public int getId() {
        return id;
    }

    /**
     * Naam van de zaal
     */
    public String getName() {
        return name;
    }

    /**
     * Aantal rijen
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Aantal zitjes per rij
     */
    public int getNumberOfColumns() {
        return numberOfColumns;
    }
}
