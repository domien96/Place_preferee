package preferee.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Een gereserveerd zitje voor een bepaalde vertoning. Elke reservatie hoort bij een bepaalde bestelling. Dezelfde
 * bestelling kan meerdere reservaties hebben.
 * @see Order
 */
@XmlRootElement(name="reservation")
public class Reservation implements Resource {

    private int id;

    private int seatNumber;

    private int orderId;

    /**
     * Uniek identificatienummer van deze reservatie (voor intern gebruik).
     */
    public int getId() {
        return id;
    }

    /**
     * Nummer van het reserveerde zitje. Zitjes worden genummerd vanaf 0, laagste rijnummer eerst
     * en binnen dezelfde rij, laagste kolomnummer eerst.
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Identificatie van de bestelling waarin dit zitje werd gereserveerd.
     */
    public int getOrderId() {
        return orderId;
    }

    public void setId(int id) {this.id = id;}

    @XmlElement(name="seat-number")
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    @XmlElement(name="order-id")
    public void setOrderId(int orderId) {this.orderId = orderId;}
}
