package preferee.data.access.server;

import preferee.data.access.DataAccessException;
import preferee.data.access.OrderDAO;
import preferee.data.Order;
import preferee.data.OrderArray;
import preferee.data.Reservation;
import preferee.data.ReservationArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by domien on 11/03/2015.
 */
public class ServerOrderDAO extends ServerAbstractDAO implements OrderDAO{

    public ServerOrderDAO(String resourceURL, String reservation_URL) { super(resourceURL); this.reservation_URL = reservation_URL; }

    /**
     * OrderDAO communiceert naast de Order-url ook nog met een extra url, namelijk de "Reservation-url".
     */
    String reservation_URL;

    /**
     *  Kan een online XML-bestand omzetten naar respectievelijk één movie-object, meerdere movie-objecten, meerdere Reservation-objecten.
     *  Zo'n downloader moet wel weten welke klasse objecten hij moet vertalen aangezien de downloader met type variabelen werkt.
     */
    ResourceWebReader<Order> singleResourceWebReader = new ResourceWebReader<>(Order.class);
    ResourceWebReader<OrderArray> multipleResourceWebReader = new ResourceWebReader<>(OrderArray.class);
    ResourceWebReader<Reservation> singleReservationWebReader = new ResourceWebReader<>(Reservation.class);//singleReservationDownloader is momenteel enkel nodig in (@code createReservation).
    ResourceWebReader<ReservationArray> multipleReservationDownloader = new ResourceWebReader<>(ReservationArray.class);


    /**
     * De bestelling terug die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke bestelling niet bestaat.
     *
     * @param id
     */
    @Override
    public Order getOrder(int id) throws DataAccessException {
        String url = this.orders_URL + "/" + Integer.toString(id) + ".xml";
        return singleResourceWebReader.getItemFromURL(url);
    }

    /**
     * Alle reservaties voor een gegeven bestelling. Leeg wanneer de bestelling niet bestaat.
     *
     * @param orderId
     */
    @Override
    public Iterable<Reservation> listReservations(int orderId) throws DataAccessException { // uit reservations halen
        String url = this.reservation_URL + ".xml?order_id=" + orderId ;
        return multipleReservationDownloader.getItemFromURL(url).getItemsAsMap().values();
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
            connection = HTTP_Requester.executePOSTRequest(this.orders_URL + ".xml", query);

        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }

        // 2) response omzetten in Order-object.
        try (InputStream response = connection.getInputStream();) {

            return singleResourceWebReader.getItemFromStream(response);
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
            connection = HTTP_Requester.executePOSTRequest(this.reservation_URL + ".xml", query);

        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }

        // 2) response omzetten in Order-object.
        try (InputStream response = connection.getInputStream();) {

            return singleReservationWebReader.getItemFromStream(response);
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
        String url = this.orders_URL + ".xml?showing_id=" + showingId ;
        return multipleResourceWebReader.getItemFromURL(url).getItemsAsMap().values();
    }
}

/**
 * Hulpklasse bij POST_requesten
 *
 */
class HTTP_Requester {

    /**
     * Verzend post-request
     * @param url
     * @param query
     * @return connection van post request
     * @throws IOException
     */
    public static URLConnection executePOSTRequest(String url, String query) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(true); // Triggers POST.
        connection.setRequestProperty("Accept-Charset", StandardCharsets.UTF_8.name());
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + StandardCharsets.UTF_8.name());

        // connection openen
        try (OutputStream output = connection.getOutputStream()) {
            output.write(query.getBytes(StandardCharsets.UTF_8.name()));
        }

        return connection;
        }
}