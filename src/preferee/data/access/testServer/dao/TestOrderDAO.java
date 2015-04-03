package preferee.data.access.testServer.dao;

import preferee.data.Order;
import preferee.data.Reservation;
import preferee.data.access.DataAccessException;
import preferee.data.access.OrderDAO;
import preferee.data.access.testServer.testServer.LocalTestServer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by domien on 11/03/2015.
 * Bootst een database na zoals op de bioscoop-website
 */
public class TestOrderDAO extends TestAbstractDAO implements OrderDAO {
    public TestOrderDAO(LocalTestServer server) { super(server);
        for ( Integer id : server.getOrders().keySet() ) {
            if (id > hoogsteOrderID)
                hoogsteOrderID = id;
        }

        for ( Integer id : server.getReservations().keySet() ) {
            if (id > hoogsteReservationID)
                hoogsteReservationID = id;
        }
    }

    /**
     * Is nuttig voor het toevoegen van orders & reservaties.
     */
    private int hoogsteOrderID = 0;
    private int hoogsteReservationID = 0;

    @Override
    public Order getOrder(int id) throws DataAccessException {
        Order order = server.getOrders().get(id);
        if (order != null)
            return order;
        else
            throw new DataAccessException("Order met die id werd niet gevonden.");
    }

    @Override
    public Iterable<Reservation> listReservations(int orderId) throws DataAccessException {
        Collection<Reservation> reservaties = server.getReservations().values(); // tabel van ALLE reservaties
        List<Reservation> gevraagd = new LinkedList<>(); // hierin komen de gevraagde reservaties
        for (Reservation reservatie : reservaties) {
            if (reservatie.getOrderId() == orderId)
                gevraagd.add(reservatie);
        }
        return gevraagd;
    }

    @Override
    public Order createOrder(String name, int showingId) throws DataAccessException {
        Order nieuwOrder = new Order();
        nieuwOrder.setName(name);
        nieuwOrder.setShowingId(showingId);
        nieuwOrder.setId(this.hoogsteOrderID + 1); // vorige +1
        hoogsteOrderID += 1;

        server.addOrder(nieuwOrder);
        return nieuwOrder;
    }

    @Override
    public Reservation createReservation(int seatNumber, int orderId) throws DataAccessException {
        // zitjes-controle: is dit zitje al bezet?
        for ( Reservation reservation : server.getReservations().values() ) {
            if ( reservation.getSeatNumber() == seatNumber )
                return null;
        }
        // onbezet zitje
        Reservation nieuwReservation = new Reservation();
        nieuwReservation.setSeatNumber(seatNumber);
        nieuwReservation.setOrderId(orderId);
        nieuwReservation.setId(this.hoogsteReservationID + 1);
        hoogsteReservationID += 1;

        server.addReservation(nieuwReservation);
        return nieuwReservation;
    }

    @Override
    public Iterable<Order> listOrders(int showingId) {
        List<Order> orders = new LinkedList<>();
        for (Order order : server.getOrders().values()) {
            if (showingId == order.getShowingId()) // int comparision is OK, primitive type
                orders.add(order);
        }
        return orders;
    }
}
