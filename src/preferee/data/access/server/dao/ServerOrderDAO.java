package preferee.data.access.server.dao;

import preferee.data.Order;
import preferee.data.Reservation;
import preferee.data.ReservationCollection;
import preferee.data.access.AbstractOrderDAO;
import preferee.data.access.DataAccessException;
import preferee.data.access.OrderDAO;
import preferee.data.access.jaxb.XMLunmarshaller;
import preferee.data.access.server.http_post_request.POST_requester;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Domien Van Steendam on 11/03/2015.
 */
public class serverOrderDAO extends AbstractOrderDAO implements OrderDAO {

    public serverOrderDAO(String resourceURL, String reservation_URL) { super(resourceURL); this.reservation_URL = reservation_URL; }

    /**
     * OrderDAO communiceert naast de Order-url ook nog met een extra url, namelijk de "Reservation-url".
     * M.a.w. deze klasse communiceert met nog 1 extra url, 2 dus in totaal.
     */
    String reservation_URL;

    /**
     *  OrderDAO kan bovenop de 2 order-converters ook nog één reservation-object of meerdere reservation-objecten uit een XML bestand halen.
     *  M.a.w. deze klasse bevat nog 2 extra converters, 4 dus in totaal.
     */
    XMLunmarshaller<Reservation> singleReservationUnmarshaller = new XMLunmarshaller<>(Reservation.class);//singleReservationDownloader is momenteel enkel nodig in (@code createReservation).
    XMLunmarshaller<ReservationCollection> multipleReservationUnmarshaller = new XMLunmarshaller<>(ReservationCollection.class);


    /**
     * De bestelling terug die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke bestelling niet bestaat.
     *
     * @param id
     */
    @Override
    public Order getOrder(int id) throws DataAccessException {
        return getResource(id);
    }

    /**
     * Alle reservaties voor een gegeven bestelling. Leeg wanneer de bestelling niet bestaat.
     *
     * @param orderId
     */
    @Override
    public Iterable<Reservation> listReservations(int orderId) throws DataAccessException { // uit reservations halen
        String url = this.reservation_URL + ".xml?order_id=" + orderId ;
        try {
            ReservationCollection result;
            if ( (result = multipleReservationUnmarshaller.unmarshall(url)) != null)
                return result.getItemsAsList();
            else
                return new ArrayList<>();
        } catch (IOException e) {
            throw new DataAccessException(e.getMessage());
        }
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
            connection = POST_requester.executePOSTRequest(this.itemsLocation + ".xml", query);

        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }

        // 2) response omzetten in Order-object.
        try (InputStream response = connection.getInputStream()) {

            return singleResourceUnmarshaller.unmarshall(response);
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
    public Reservation createReservation(int seatNumber, int orderId) throws DataAccessException {
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

        } catch (IOException e) {
            throw new DataAccessException(e.getMessage());
        }

        // 2) response omzetten in Order-object.
        try (InputStream response = connection.getInputStream()) {

            return singleReservationUnmarshaller.unmarshall(response);
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
        String url = this.itemsLocation + ".xml?showing_id=" + showingId ;
        try {
            return multipleResourceUnmarshaller.unmarshall(url).getItemsAsList();
        } catch (IOException e) {
            return new ArrayList<>(); // we mogen geen exception verdergooien, zie header.
        }
    }

    @Override
    public Collection<Integer> listTakenSeats(int showingId) {
        String url = this.reservation_URL + ".xml?showing_id=" + showingId ;
        Collection<Integer> seatNumbers = new ArrayList<>();
        try {
             for ( Reservation res : multipleReservationUnmarshaller.unmarshall(url).getItemsAsList()) {
                 seatNumbers.add(res.getSeatNumber());
             }
            return seatNumbers;
        } catch (IOException e) {
            return new ArrayList<>(); // we mogen geen exception verdergooien, zie header.
        }
    }

    @Override
    public Collection<Order> listAll() {
        try {
            return multipleResourceUnmarshaller.unmarshall(itemsLocation+".xml").getItemsAsList();
        } catch (IOException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}