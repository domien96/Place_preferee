package preferee.tests.FakeServerTests;

import preferee.data.Reservation;
import preferee.data.access.DataAccessException;
import preferee.data.access.DataAccessProvider;
import preferee.data.access.OrderDAO;
import preferee.data.access.Providers;

/**
 * Created by Domien Van Steendam on 19/03/2015.
 */
public class OrderDao_TEST {
    public static void main(String[] args) {
        try(
        DataAccessProvider provider = Providers.createTestProvider()

        ) {
            OrderDAO dao = provider.getOrderDAO();
            /**
            System.out.println(dao.getOrder(5).getName());
            //System.out.println(mdao.getMovie(333).getTitle());
             */

            dao.createReservation(99,4);
            dao.createReservation(99,4);
            for ( Reservation res: dao.listReservations(4) ) {
                System.out.println(res.getOrderId());
                System.out.println(res.getId());
                System.out.println(res.getSeatNumber());
                System.out.println();
            }

        /**
            dao.createOrder("Domien Van Steendam",3);
            dao.createOrder("Dieter",3);

            for ( Order res: dao.listOrders(3) ) {
                System.out.println(res.getName());
                System.out.println(res.getId());
            }
         */




        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage() + "exception");
        }

    }
}
