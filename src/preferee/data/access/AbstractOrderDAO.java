package preferee.data.access;

import preferee.data.Order;
import preferee.data.OrderCollection;

/**
 * Bovenklasse van alle orderDAO's
 * Created by Domien on 5/05/2015.
 */
public abstract class AbstractOrderDAO extends AbstractDAO<Order, OrderCollection> implements OrderDAO {
    protected AbstractOrderDAO(String serverURL) {
        super(serverURL, Order.class, OrderCollection.class);
    }
}
