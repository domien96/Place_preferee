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

@XmlRootElement(name="movies")
public class MovieArray implements ResourceArray {
    private Movie[] items;

    @XmlElement(name="movie")
    public Movie[] getItems() { return items;}

    public void setItems(Movie[] items) { this.items = items;}

    public Map<Integer,Movie> getItemsAsMap() {
        List<Movie> lijst = Arrays.asList(items);
        Map<Integer,Movie> out = new HashMap<>();
        for (Movie movie : lijst) {
            out.put(movie.getId() , movie);
        }
        return out;
    }
}
