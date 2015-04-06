package preferee.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bestelling door een persoon van een aantal zitjes voor een bepaalde vertoning.
 * Bij elke bestelling horen 1 of meer reservaties.
 * @see Reservation
 *
 * Created by domien Van Steendam
 */
@XmlRootElement(name="order")
public class Order extends Resource {

    @XmlElement(name="name")
    private String name;

    @XmlElement(name="showing-id")
    private int showingId;

    public Order(int id, String name, int showingId) {
        this.id = id;
        this.name = name;
        this.showingId = showingId;
    }

    public Order () {}

    /**
     * Uniek identificatienummer van deze bestelling (voor intern gebruik).
     */
    public int getId() {
        return id;
    }

    /**
     * Naam van de klant die de bestelling heeft geplaatst.
     */
    public String getName() {
        return name;
    }

    /**
     * Identificatienummer van de vertoning
     */
    public int getShowingId() {
        return showingId;
    }
}
