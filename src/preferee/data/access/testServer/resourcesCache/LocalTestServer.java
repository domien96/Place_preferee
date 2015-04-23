package preferee.data.access.testServer.resourcesCache;

import preferee.data.*;
import preferee.data.access.jaxb.XMLunmarshaller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dvsteend on 18/03/2015.
 * Representeert een lokale testServer die alle XML-files in de constructor leest en de informatie lokaal bijhoudt.
 */

public class LocalTestServer {

    /**
     * Collectie van movies, order, reservations, screens en showings.
     */
    private Map<Integer,Movie> movies;
    private Map<Integer,Order>  orders;
    private Map<Integer,Reservation>  reservations;
    private Map<Integer,Screen>  screens;
    private Map<Integer,Showing>  showings;

    /**
     * Hardcoded locatie van de xml-bestanden
     * (dit ter vervanging van testFiles.properties)
     */
    private final String moviesPath = "/preferee/resources/movies.xml";
    private final String ordersPath = "/preferee/resources/orders.xml";
    private final String reservationsPath = "/preferee/resources/reservations.xml";
    private final String screensPath = "/preferee/resources/screens.xml";
    private final String showingsPath = "/preferee/resources/showings.xml";

    /**
     * Maakt een lokale testServer aan.
     * Laadt de testbestanden in en slaat ze lokaal op.
     *
     * @param: properties bestand waarin de locatie naar de testfiles(XML-files) staan.
     * @throws: IOException, properties-bestand kan niet ingelezen worden wat duidt op een programeerfout.
     */
    public LocalTestServer() {
        // data lokaal opslaan en bijhouden
        // algemeen: resource = new XMLUnmarshaller<ResourceArray>(ResourceArray.class).unmarshall(<stream met pad naar xml-bestand>) . getitemsasmap()
        try {
            movies = new XMLunmarshaller<MovieCollection>(MovieCollection.class).unmarshall(LocalTestServer.class.getResourceAsStream(moviesPath)).getItemsAsMap();
            orders = new XMLunmarshaller<OrderCollection>(OrderCollection.class).unmarshall(LocalTestServer.class.getResourceAsStream(ordersPath)).getItemsAsMap();
            reservations = new XMLunmarshaller<ReservationCollection>(ReservationCollection.class).unmarshall(LocalTestServer.class.getResourceAsStream(reservationsPath)).getItemsAsMap();
            screens = new XMLunmarshaller<ScreenCollection>(ScreenCollection.class).unmarshall(LocalTestServer.class.getResourceAsStream(screensPath)).getItemsAsMap();
            showings = new XMLunmarshaller<ShowingCollection>(ShowingCollection.class).unmarshall(LocalTestServer.class.getResourceAsStream(showingsPath)).getItemsAsMap();
        } catch ( NullPointerException e) { // zou niet mogen gebeuren => stop programma
            e.printStackTrace();
            throw new Error("PROGRAMMEERFOUT : xml's inlezen, foute xml-indeling @ localtestserver");
        }
    }

    // GETTERS
    public Map<Integer,Movie> getMovies() { return new HashMap<>(movies); } // defensive copy (arraylist, want meestal word getter gebruikt bij iteratie

    public Map<Integer,Order> getOrders() {
        return new HashMap<>(orders);
    } // defensive copy

    public Map<Integer,Reservation> getReservations() {
        return new HashMap<>(reservations);
    } // defensive copy

    public Map<Integer,Screen> getScreens() {return new HashMap<>(screens);} // defensive copy

    public Map<Integer,Showing> getShowings() { return new HashMap<>(showings); } // defensive copy

    // ADDERS
    public void addOrder(Order order ) { this.orders.put(order.getId(), order);}

    public void addReservation(Reservation reservation ) { this.reservations.put(reservation.getId(), reservation);}
}
