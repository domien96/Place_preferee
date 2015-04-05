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

@XmlRootElement(name="screens")
public class ScreenCollection implements ResourceCollection {
    private Screen[] items;

    @XmlElement(name = "screen")
    public Screen[] getItems() {
        return items;
    }

    public void setItems(Screen[] items) {
        this.items = items;
    }

    public Map<Integer,Screen> getItemsAsMap() {
        List<Screen> lijst = Arrays.asList(items);
        Map<Integer,Screen> out = new HashMap<>();
        for (Screen screen : lijst) {
            out.put(screen.getId(), screen);
        }
        return out;
    }
}