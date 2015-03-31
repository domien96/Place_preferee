package preferee.data.access.testServer;

import preferee.data.*;
import preferee.data.access.persistent_xml_processing.jaxb.XMLconverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by dvsteend on 18/03/2015.
 * Representeert een lokale testServer die alle XML-files in de constructor leest en de informatie lokaal bijhoudt.
 * Kon ook ResourcesCache genoemd worden.
 */

public class LocalTestServer {

    /**
     * Array van movies, order, reservations, screens en showings.
     */
    private Map<Integer,Movie> movies;
    private Map<Integer,Order>  orders;
    private Map<Integer,Reservation>  reservations;
    private Map<Integer,Screen>  screens;
    private Map<Integer,Showing>  showings;
    /**
     * Maakt een lokale testServer aan.
     * Laadt de testbestanden in en slaat ze lokaal op.
     *
     * @param: properties bestand waarin de locatie naar de testfiles(XML-files) staan.
     * @throws: IOException, properties-bestand kan niet ingelezen worden wat duidt op een programeerfout.
     */
    public LocalTestServer(String propertiesPad) {
        Properties properties = new Properties();
        try {
            properties.load(LocalTestServer.class.getResourceAsStream(propertiesPad));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // data lokaal opslaan en bijhouden
        String prefix = "file:///" + getClass().getClassLoader().getResource(".").getPath(); // protocol + application path
        movies = new XMLconverter<MovieArray>(MovieArray.class).getItemFromURL(prefix +  properties.getProperty("moviesPath")).getItemsAsMap();
        orders = new XMLconverter<OrderArray>(OrderArray.class).getItemFromURL(prefix + properties.getProperty("ordersPath")).getItemsAsMap();
        reservations = new XMLconverter<ReservationArray>(ReservationArray.class).getItemFromURL(prefix + properties.getProperty("reservationsPath")).getItemsAsMap();
        screens = new XMLconverter<ScreenArray>(ScreenArray.class).getItemFromURL(prefix + properties.getProperty("screensPath")).getItemsAsMap();
        showings = new XMLconverter<ShowingArray>(ShowingArray.class).getItemFromURL(prefix + properties.getProperty("showingsPath")).getItemsAsMap();
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
