package preferee.data.access.server;

/**
 * Een gemeenschappelijke bovenklasse die zou kunnen gebruikt worden voor alle filters bij
 * de Server-implementatie. Er kan gefilterd worden op waardes van type T.
 * Created by domien on 23/03/2015.
 */
public abstract class ServerFilter {
    private String urlAchterVoegsel;

    public String getUrlAchterVoegsel() {
        return urlAchterVoegsel;
    }

    public ServerFilter(String criteria, Object value) {
        urlAchterVoegsel = setParameters(criteria, value);
    }

    /**
     * Vult de gegevens correct in en retourneert wat er uiteindelijk bij de URL moet toegevoegd worden.
     *
     * @param criteria
     * @param value
     * @return
     */
    public abstract String setParameters(String criteria, Object value);

}
