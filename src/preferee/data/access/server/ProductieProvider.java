package preferee.data.access.server;

import preferee.data.access.DataAccessException;
import preferee.data.access.DataAccessProvider;
import preferee.data.access.server.dao.serverMovieDAO;
import preferee.data.access.server.dao.serverOrderDAO;
import preferee.data.access.server.dao.serverScreenDAO;
import preferee.data.access.server.dao.serverShowingDAO;

import java.net.URL;

/**
 * Created by Domien Van Steendam on 9/03/2015.
 */
public class ProductieProvider implements DataAccessProvider {

    URL basic_url;

    /**
     * Maken van testServer
     */
    public ProductieProvider(URL url) {
        // basis-url
        this.basic_url = url;
    }


    private preferee.data.access.MovieDAO movieDAO;

    /**
     * Een data access object voor films
     */
    @Override
    public preferee.data.access.MovieDAO getMovieDAO() {
        if (movieDAO == null) {
            movieDAO = new serverMovieDAO(basic_url + "/movies");
        }
        return movieDAO;
    }

    private preferee.data.access.ScreenDAO screenDAO;

    /**
     * Een data access object voor zalen
     */
    @Override
    public preferee.data.access.ScreenDAO getScreenDAO() {
        if (screenDAO == null) {
            screenDAO = new serverScreenDAO(basic_url + "/screens");
        }
        return screenDAO;
    }

    private preferee.data.access.ShowingDAO showingDAO;

    /**
     * Een preferee.data access object voor vertoningen
     */
    @Override
    public preferee.data.access.ShowingDAO getShowingDAO() {
        if (showingDAO == null) {
            showingDAO = new serverShowingDAO(basic_url + "/showings");
        }
        return showingDAO;
    }

    private preferee.data.access.OrderDAO orderDAO;

    /**
     * Een data access object voor bestellingen en reservaties
     */
    @Override
    public preferee.data.access.OrderDAO getOrderDAO() {
        if (orderDAO == null) {
            orderDAO = new serverOrderDAO(basic_url + "/orders",basic_url + "/reservations");
        }
        return orderDAO;
    }

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     * <p>
     * <p>While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to
     * declare concrete implementations of the {@code close} method to
     * throw more specific exceptions, or to throw no exception at all
     * if the close operation cannot fail.
     * <p>
     * <p> Cases where the close operation may fail require careful
     * attention by implementers. It is strongly advised to relinquish
     * the underlying resources and to internally <em>mark</em> the
     * resource as closed, prior to throwing the exception. The {@code
     * close} method is unlikely to be invoked more than once and so
     * this ensures that the resources are released in a timely manner.
     * Furthermore it reduces problems that could arise when the resource
     * wraps, or is wrapped, by another resource.
     * <p>
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link
     * InterruptedException}.</em>
     * <p>
     * This exception interacts with a thread's interrupted status,
     * and runtime misbehavior is likely to occur if an {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     * <p>
     * More generally, if it would cause problems for an
     * exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     * <p>
     * <p>Note that unlike the {@link java.io.Closeable#close close}
     * method of {@link java.io.Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     * <p>
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
     *
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws DataAccessException {
        System.out.print("< Provider close >");
    }
}
