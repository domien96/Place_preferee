package preferee.data;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Vertoning van een film in een bepaalde zaal
 *
 * Created by domien Van Steendam
 */
@XmlRootElement(name="showing")
public class Showing extends Resource {

    @XmlElement(name="movie-id")
    private int movieId;

    @XmlElement(name="screen-id")
    private int screenId;

    private LocalDateTime time;

    /**
     * Identificatienummer van de film die wordt vertoond
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Identificatienummer van de zaal waarin de film wordt vertoond
     */
    public int getScreenId() {
        return screenId;
    }

    /**
     * Aanvangstijdstip
     */
    public LocalDateTime getTime() {return time;}

    // hieronder: merge-proces datetime en date => localdatetime
    private String tijdstip; // 2001-01-01THH:mm:ssZ
    private Date datum; // jjjj-mm-dd

    @XmlElement(name="time")
    private String getTijdstip() {
        return tijdstip;
    }

    private void setTijdstip(String tijdstip) {
        this.tijdstip = tijdstip;
    }

    @XmlElement(name="date")
    private Date getDatum() {
        return datum;
    }

    private void setDatum(Date datum) {
        this.datum = datum;
    }

    /**
     *  Verwerken van velden (@var tijdstip) en (@var datum) naar een LocalDateTime-object na het unmarshallen van een xml met Showing-objecten.
     * @param unm
     * @param parent
     */
    private void afterUnmarshal(Unmarshaller unm, Object parent) {
        // omzetten (@field tijdstip): String => LocaldateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(this.tijdstip, formatter);
        // omzetten (@field datum): Date -> ZonedDateTime
        ZonedDateTime date = datum.toInstant().atZone(ZoneId.systemDefault());
        // Gebruiken van gewenste datum, gewenste tijd van (@var_dateTime) om een LocalDateTime--object te creëren.
        time = LocalDateTime.of( date.toLocalDate(), dateTime.toLocalTime() );
    }
}
