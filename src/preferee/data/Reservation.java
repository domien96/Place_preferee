package preferee.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Een gereserveerd zitje voor een bepaalde vertoning. Elke reservatie hoort bij een bepaalde bestelling. Dezelfde
 * bestelling kan meerdere reservaties hebben.
 * @see Order
 *
 * Created by domien
 */
@XmlRootElement(name="reservation")
public class Reservation extends Resource {

    @XmlElement(name="seat-number")
    private int seatNumber;

    @XmlElement(name="order-id")
    private int orderId;

    public Reservation(int id, int seatNumber, int orderId) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.orderId = orderId;
    }

    public Reservation () {}

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
}
