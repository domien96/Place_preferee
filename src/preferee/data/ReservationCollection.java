package preferee.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Bevat configuratieinformatie voor verschillende movies. Deze informatie wordt
 * met behulp van JAXB ingelezen vanuit een corresponderend XML-bestand.
 *
 * Created by domien on 18/03/2015.
 */

@XmlRootElement(name="reservations")
public class ReservationCollection extends ResourceCollection<Reservation> {
    @XmlElement(name="reservation")
    public Reservation[] getItems() { return items;}

    public void setItems(Reservation[] items) { this.items = items;}
}
