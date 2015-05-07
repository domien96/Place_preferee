package preferee.data.access;

import preferee.data.Order;
import preferee.data.Reservation;

import java.util.Collection;

/**
 * Data access object voor het behandelen van bestellingen en reservaties.
 *
 * Created by Domien Van Steendam
 */
public interface OrderDAO {

    /**
     * De bestelling terug die overeenkomt met het opgegeven identificatienummer.
     * Gooit een uitzondering op als een dergelijke bestelling niet bestaat.
     */
    public Order getOrder(int id) throws DataAccessException;

    /**
     * Alle reservaties voor een gegeven bestelling. Leeg wanneer de bestelling niet bestaat.
     * @return: Set
     */
    public Iterable<Reservation> listReservations(int orderId) throws DataAccessException;

    /**
     * Maak een nieuwe bestelling aan.
     * @param showingId identificatie van de gewenste vertoning
     * @param name Naam van de persoon die de bestelling heeft gemaakt.
     * @return De nieuwe bestelling
     */
    public Order createOrder(String name, int showingId) throws DataAccessException;

    /**
     * Reserveer een zitje.
     * @param seatNumber volgnummer van het zitje
     * @param orderId identificatie van de overeenkomstige bestelling
     * @return De overeenkomstige reservatie, of null als het zitje ondertussen niet meer vrij blijkt te zijn
     */
    public Reservation createReservation (int seatNumber, int orderId) throws DataAccessException;

    /**
     * Alle bestellingen voor een gegeven vertoning. Leeg wanneer de vertoning niet bestaat.
     * @return: Set
     */
    public Iterable<Order> listOrders (int showingId);

    /**
     * Alle gereserveerde stoelnummers voor een gegeven vertoning. Leeg wanneer alle zitjes nog vrij zijn.
     * @return: Set
     */
    public Collection<Integer> listTakenSeats(int showingId);

}
