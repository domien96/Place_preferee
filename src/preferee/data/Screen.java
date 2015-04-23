package preferee.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Zaal waarin films worden vertoond.
 *
 * Created by Domien Van Steendam
 */
@XmlRootElement(name="screen")
public class Screen extends Resource {

    @XmlElement(name="name")
    private String name;

    @XmlElement(name="rows")
    private int numberOfRows;

    @XmlElement(name="cols")
    private int numberOfColumns;

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
