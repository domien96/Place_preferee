package preferee.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bestelling door een persoon van een aantal zitjes voor een bepaalde vertoning.
 * Bij elke bestelling horen 1 of meer reservaties.
 * @see Reservation
 */
@XmlRootElement(name="order")
public class Order implements Resource {


    private int id;

    private String name;

    private int showingId;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name="showing-id")
    public void setShowingId(int showingId) {
        this.showingId = showingId;
    }
}
