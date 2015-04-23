package preferee.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Alle gegevens voor een bepaalde film.
 *
 * Created by Domien Van Steendam
 */
@XmlRootElement(name="movie")
public class Movie extends Resource {

    @XmlElement(name="title")
    private String title;

    @XmlElement(name="year")
    private int year;

    @XmlElement(name="runtime")
    private int duration;

    @XmlElement(name="genre")
    private String genre;

    @XmlElement(name="language")
    private String language;

    @XmlElement(name="rating")
    private String rating;

    @XmlElement(name="poster")
    private String poster;

    @XmlElement(name="imdb")
    private String imdbId;


    /**
     * Titel van de film
     */
    public String getTitle() {
        return title;
    }

    /**
     * Jaar van uitgave
     */
    public int getYear() {
        return year;
    }

    /**
     * Hoe lang de film duurt, in minuten
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Taal
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * URL van een afbeelding van de filmposter
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Uniek identificatienummer van deze film (voor intern gebruik).
     */
    public String getImdbId() {
        return imdbId;
    }
}
