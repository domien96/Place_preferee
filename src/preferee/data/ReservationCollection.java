package preferee.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Bevat configuratieinformatie voor verschillende movies. Deze informatie wordt
 * met behulp van JAXB ingelezen vanuit een corresponderend XML-bestand.
 *
 * Created by domien on 18/03/2015.
 */

@XmlRootElement(name="reservations")
public class ReservationCollection implements ResourceCollection {
    private Reservation[] items;

    @XmlElement(name="reservation")
    public Reservation[] getItems() { return items;}

    public void setItems(Reservation[] items) { this.items = items;}

    public Map<Integer,Reservation> getItemsAsMap() {
        List<Reservation> lijst = Arrays.asList(items);
        Map<Integer,Reservation> out = new HashMap<>();
        for (Reservation reservation : lijst) {
            out.put(reservation.getId(), reservation);
        }
        return out;
    }
}
