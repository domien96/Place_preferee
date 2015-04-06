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

@XmlRootElement(name="showings")
public class ShowingCollection extends ResourceCollection<Showing> {
    @XmlElement(name = "showing")
    public Showing[] getItems() {
        return items;
    }

    public void setItems(Showing[] items) {
        this.items = items;
    }
}