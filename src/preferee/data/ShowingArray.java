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

@XmlRootElement(name="showings")
public class ShowingArray {
    private Showing[] items;

    @XmlElement(name = "showing")
    public Showing[] getItems() {
        return items;
    }

    public void setItems(Showing[] items) {
        this.items = items;
    }

    public Map<Integer,Showing> getItemsAsMap() {
        List<Showing> lijst = Arrays.asList(items);
        Map<Integer,Showing> out = new HashMap<>();
        for (Showing showing : lijst) {
            out.put(showing.getId(), showing);
        }
        return out;
    }
}