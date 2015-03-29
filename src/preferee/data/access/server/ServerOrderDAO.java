package preferee.data.access.server;

import preferee.data.Order;
import preferee.data.OrderArray;
import preferee.data.Reservation;
import preferee.data.ReservationArray;
import preferee.data.access.DataAccessException;
import preferee.data.access.persistent_xml_processing.POST_requester;
import preferee.data.access.persistent_xml_processing.jaxb.XMLconverter;
import preferee.data.access.OrderDAO;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by domien on 11/03/2015.
 */
public class ServerOrderDAO extends ServerAbstractDAO<Order,OrderArray> implements OrderDAO{

    public ServerOrderDAO(String resourceURL, String reservation_URL) { super(resourceURL, Order.class, OrderArray.class); this.reservation_URL = reservation_URL; }

    /**
     * OrderDAO communiceert naast de Order-url ook nog met een extra url, namelijk de "Reservation-url".
     * M.a.w. deze klasse communiceert met nog 1 extra url, 2 dus in totaal.
     */
    String reservation_URL;

    /**
     *  OrderDAO kan bovenop de 2 order-converters ook nog één reservation-object of meerdere reservation-objecten uit een XML bestand halen.
     *  M.a.w. deze klasse bevat nog 2 extra converters, 4 dus in totaal.
     */
    XMLconverter<Reservation> singleReservationConverter = new XMLconverter<>(Reservation.class);//singleReservationDownloader is momenteel enkel nodig in (@code createReservation).
    XMLconverter<ReservationArray> multipleReservationConverter = new XMLconverter<>(ReservationArray.class);


    /**
     * De bestelling terug die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke bestelling niet bestaat.
     *
     * @param id
     */
    @Override
    public Order getOrder(int id) throws DataAccessException {
        String url = this.itemList_URL + "/" + Integer.toString(id) + ".xml";
        return singleItemConverter.getItemFromURL(url);
    }

    /**
     * Alle reservaties voor een gegeven bestelling. Leeg wanneer de bestelling niet bestaat.
     *
     * @param orderId
     */
    @Override
    public Iterable<Reservation> listReservations(int orderId) throws DataAccessException { // uit reservations halen
        String url = this.reservation_URL + ".xml?order_id=" + orderId ;
        return multipleReservationConverter.getItemFromURL(url).getItemsAsMap().values();
    }

    /**
     * Maak een nieuwe bestelling aan.
     *
     * @param name      Naam van de persoon die de bestelling heeft gemaakt.
     * @param showingId identificatie van de gewenste vertoning
     * @return De nieuwe bestelling
     */
    @Override
    public Order createOrder(String name, int showingId) throws DataAccessException {
        // 1) http-request uitvoeren
        String charset = StandardCharsets.UTF_8.name();
        String query;
        URLConnection connection;
        try {
            // query samenstellen
            query = String.format("name=%s&showing_id=%s",
                    URLEncoder.encode(name, charset),
                    URLEncoder.encode(Integer.toString(showingId), charset));

            // HTTP-request verzenden en connectie opslaan.
            connection = POST_requester.executePOSTRequest(this.itemList_URL + ".xml", query);

        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }

        // 2) response omzetten in Order-object.
        try (InputStream response = connection.getInputStream();) {

            return singleItemConverter.getItemFromStream(response);
        } catch (IOException e) {
            throw new DataAccessException(e.getMessage());
        }

    }

    /**
     * Reserveer een zitje.
     *
     * @param seatNumber volgnummer van het zitje
     * @param orderId    identificatie van de overeenkomstige bestelling
     * @return De overeenkomstige reservatie, of null als het zitje ondertussen niet meer vrij blijkt te zijn
     */
    @Override
    public Reservation createReservation(int seatNumber, int orderId) throws DataAccessException { //todo
        // 1) http-request uitvoeren
        String charset = StandardCharsets.UTF_8.name();
        String query;
        URLConnection connection;
        try {
            // query samenstellen
            query = String.format("seat_number=%s&order_id=%s",
                    URLEncoder.encode(Integer.toString(seatNumber), charset),
                    URLEncoder.encode(Integer.toString(orderId), charset));

            // HTTP-request verzenden en connectie opslaan.
            connection = POST_requester.executePOSTRequest(this.reservation_URL + ".xml", query);

        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }

        // 2) response omzetten in Order-object.
        try (InputStream response = connection.getInputStream();) {

            return singleReservationConverter.getItemFromStream(response);
        } catch (IOException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Alle bestellingen voor een gegeven vertoning. Leeg wanneer de vertoning niet bestaat.
     *
     * @param showingId
     */
    @Override
    public Iterable<Order> listOrders(int showingId) { // uit orders halen
        String url = this.itemList_URL + ".xml?showing_id=" + showingId ;
        return multipleItemsConverter.getItemFromURL(url).getItemsAsMap().values();
    }
}