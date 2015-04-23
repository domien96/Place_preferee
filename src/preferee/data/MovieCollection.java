package preferee.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Bevat configuratieinformatie voor verschillende movies. Deze informatie wordt
 * met behulp van JAXB ingelezen vanuit een corresponderend XML-bestand.
 *
 * Created by Domien Van Steendam on 18/03/2015.
 */

@XmlRootElement(name="movies")
public class MovieCollection extends ResourceCollection<Movie> {
    @XmlElement(name="movie")
    public Movie[] getItems() { return items;}

    public void setItems(Movie[] items) { this.items = items;}
}
