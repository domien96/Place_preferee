package preferee.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Bevat configuratieinformatie voor verschillende movies. Deze informatie wordt
 * met behulp van JAXB ingelezen vanuit een corresponderend XML-bestand.
 *
 * Created by domien Van Steendam on 18/03/2015.
 */

@XmlRootElement(name="screens")
public class ScreenCollection extends ResourceCollection<Screen> {
    @XmlElement(name = "screen")
    public Screen[] getItems() {
        return items;
    }

    public void setItems(Screen[] items) {
        this.items = items;
    }
}