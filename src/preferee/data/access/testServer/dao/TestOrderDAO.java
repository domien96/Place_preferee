package preferee.data.access.testServer.dao;

import preferee.data.Order;
import preferee.data.Reservation;
import preferee.data.ReservationCollection;
import preferee.data.access.AbstractOrderDAO;
import preferee.data.access.DataAccessException;
import preferee.data.access.OrderDAO;
import preferee.data.access.jaxb.XMLunmarshaller;

import java.util.*;

/**
 * Created by Domien Van Steendam on 11/03/2015.
 * Houdt zowel orders als reservations bij. Deze DAO onderhoudt dus 2 soorten resources.
 */
public class TestOrderDAO extends AbstractOrderDAO implements OrderDAO {
    /**
     * OrderDAO communiceert naast de Order-url ook nog met een extra url, namelijk de "Reservation-url".
     * In deze context is het niet echt een url.
     * M.a.w. deze klasse communiceert met nog 1 extra url, 2 dus in totaal.
     */
    String reservationsLocation;

    /**
     *  OrderDAO kan bovenop de 2 order-converters ook nog één reservation-object of meerdere reservation-objecten uit een XML bestand halen.
     *  M.a.w. deze klasse bevat nog 2 extra converters, 4 dus in totaal.
     */
    XMLunmarshaller<Reservation> singleReservationUnmarshaller = new XMLunmarshaller<>(Reservation.class);//singleReservationDownloader is momenteel enkel nodig in (@code createReservation).
    XMLunmarshaller<ReservationCollection> multipleReservationUnmarshaller = new XMLunmarshaller<>(ReservationCollection.class);

    /**
     * Er kunnen order en reservations toegevoegd worden. Na het uitvoeren van het programma willen we nog steeds onze originele testbestanden hebben.
     * We moeten de nieuwe orders en reservaties dus ergens anders opslaan.
     */
    List<Order> added_orders = new LinkedList<>();
    List<Reservation> added_reservations = new LinkedList<>();

    /**
     * Heeft in vergelijking met de andere DAO's nog een tweede locatie nodig.
     */
    public TestOrderDAO(String ordersLocation, String reservationsLocation) { super(ordersLocation);
        this.reservationsLocation = reservationsLocation;
        for ( Order order : listAll() ) {
            if (order.getId() > hoogsteOrderID)
                hoogsteOrderID = order.getId();
        }

        for ( Reservation res: getReservations() ) {
            if (res.getId() > hoogsteReservationID)
                hoogsteReservationID = res.getId();
        }
    }

    /**
     * Is nuttig voor het toevoegen van orders & reservaties.
     */
    private int hoogsteOrderID = 0;
    private int hoogsteReservationID = 0;

    @Override
    public Order getOrder(int id) throws DataAccessException {
        return getResource(id);
    }

    @Override
    public Iterable<Reservation> listReservations(int orderId) throws DataAccessException {
        Collection<Reservation> reservaties = getReservations(); // tabel van ALLE reservaties
        List<Reservation> gevraagd = new LinkedList<>(); // hierin komen de gevraagde reservaties
        for (Reservation reservatie : reservaties) {
            if (reservatie.getOrderId() == orderId)
                gevraagd.add(reservatie);
        }
        return gevraagd;
    }

    @Override
    public Order createOrder(String name, int showingId) throws DataAccessException {
        Order nieuwOrder = new Order(this.hoogsteOrderID+1 , name , showingId); // vorige id +1
        hoogsteOrderID += 1;

        addOrder(nieuwOrder);
        return nieuwOrder;
    }

    /**
     * Voegt nieuwe reservatie toe. Voert geen controle uit.
     * @param nieuwOrder : nieuwe order
     */
    private void addOrder(Order nieuwOrder) {
        added_orders.add(nieuwOrder);
    }

    @Override
    public Reservation createReservation(int seatNumber, int orderId) throws DataAccessException {
        // zitjes-controle: is dit zitje al bezet?
        for ( Reservation reservation : getReservations() ) {
            if ( reservation.getSeatNumber() == seatNumber )
                return null;
        }
        // onbezet zitje
        Reservation nieuwReservation = new Reservation(this.hoogsteReservationID + 1 , seatNumber , orderId);
        hoogsteReservationID += 1;

        addReservation(nieuwReservation);
        return nieuwReservation;
    }

    /**
     * Voegt nieuwe reservatie toe. Voert geen controle uit.
     * @param nieuwReservation : nieuwe reservatie
     */
    private void addReservation(Reservation nieuwReservation) {
        added_reservations.add(nieuwReservation);
    }

    @Override
    public Iterable<Order> listOrders(int showingId) {
        List<Order> orders = new LinkedList<>();
        for (Order order : listAll()) {
            if (showingId == order.getShowingId()) // int comparision is OK, primitive type
                orders.add(order);
        }
        return orders;
    }

    @Override
    public Collection<Integer> listTakenSeats(int showingId) {
        Set<Integer> seatumbers = new HashSet<>();
        for (Order order : listOrders(showingId)) {
            for (Reservation reservation : listReservations(order.getId())) {
                seatumbers.add(reservation.getSeatNumber());
            }
        }
        return seatumbers;
    }


    /**
     * Lijst van alle orders
     */
    @Override
    public Collection<Order> listAll() {
        List<Order> out = new LinkedList<>(added_orders);
        out.addAll(multipleResourceUnmarshaller.unmarshall(getClass().getResourceAsStream(itemsLocation)).getItemsAsList());
        return out;
    }

    /**
     * Lijst van alle Reservations
     */
    public Collection<Reservation> getReservations() {
        List<Reservation> out = new LinkedList<>(added_reservations);
        out.addAll(multipleReservationUnmarshaller.unmarshall(getClass().getResourceAsStream(reservationsLocation)).getItemsAsList());
        return out;
    }
}
