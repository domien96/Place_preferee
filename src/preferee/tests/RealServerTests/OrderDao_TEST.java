package preferee.tests.RealServerTests;

import preferee.data.Order;
import preferee.data.Reservation;
import preferee.data.access.DataAccessException;
import preferee.data.access.DataAccessProvider;
import preferee.data.access.OrderDAO;
import preferee.data.access.Providers;

import java.net.URL;

/**
 * Created by domien on 24/03/2015.
 */
public class OrderDao_TEST {
    DataAccessProvider dap;
    public static void main(String[] args) {
        new OrderDao_TEST().run();
    }


    public void run() {
        try(
                DataAccessProvider dap = Providers.createProvider(new URL("http://esari.ugent.be"));

        ) {
            System.out.println(this.getClass().getName());
            this.dap = dap;
            testGetters();
            testListOrders();
            testListReservations();
            testMakeOrder();
            testMakeReservation();

        } catch (DataAccessException e) {
            System.err.println("DataAccesException opgevangen in TEST: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(e.getClass() + ":exception" +" opgevangen in TEST" + e.getMessage());
        }

    }

    private void testMakeOrder() {
        System.out.println("----------------\ntestCreatorsORDER \n----------------");
        Order newOrder = dap.getOrderDAO().createOrder("mehmet",21);
        System.out.println(newOrder.getName()+" "+newOrder.getId()+" "+newOrder.getShowingId());
    }

    private void testMakeReservation() {
        System.out.println("----------------\ntestCreatorsRESERVATION \n----------------");
        Reservation newReservation = dap.getOrderDAO().createReservation(7, 260);
        System.out.println(newReservation.getSeatNumber()+" "+newReservation.getId()+" "+newReservation.getOrderId());
    }

    public void testGetters() {
        System.out.println("----------------\ntestGetters \n----------------");
        OrderDAO dao = dap.getOrderDAO();
        System.out.println(dao.getOrder(55).getId());
        //System.out.println(dao.getScreen(333).getName());

    }

    public void testListOrders() {
        System.out.println("----------------\ntestListOrders \n----------------");
        OrderDAO dao = dap.getOrderDAO();
        int count = 1;
        for ( Order order : dao.listOrders(5) ) { // met showing id
            System.out.println(count + " : " + order.getShowingId() + " , " + order.getName());
            count++;
        }
    }

    private void testListReservations() {
        System.out.println("----------------\ntestListReservations \n----------------");
        OrderDAO dao = dap.getOrderDAO();
        int count = 1;
        for ( Reservation reservation : dao.listReservations(55) ) { // met order id
            System.out.println(count + " : " + reservation.getOrderId() + " , "+reservation.getId());
            count++;
        }
    }
}
