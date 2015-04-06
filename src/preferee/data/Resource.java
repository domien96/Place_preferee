package preferee.data;

import javax.xml.bind.annotation.XmlElement;

/**
 * Markeer-Interface voor alle soorten resources
 * Ik kan geen gemeenshappelijke methodes en velden hier slepen, want het is niet toegelaten om gegeven klassen aan te passen.
 * zoals veld id.
 * Created by domien on 24/03/2015.
 */
public abstract class Resource {
    /**
     * Uniek identificatienummer van deze resource (voor intern gebruik).
     */
    @XmlElement(name="id")
    protected int id;

    public int getId() {
        return id;
    }

}
