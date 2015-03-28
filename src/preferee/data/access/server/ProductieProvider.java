package preferee.data.access.server;

import preferee.data.access.*;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by domien on 9/03/2015.
 */
public class ProductieProvider implements DataAccessProvider {

    Properties properties;
    URL basic_url;

    /**
     * Maken van testServer
     */
    public ProductieProvider(URL url, String propertiesResource) {
        // properties
        Properties properties = new Properties();
        try {
            properties.load(ProductieProvider.class.getResourceAsStream(propertiesResource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.properties = properties;
        // basis-url
        this.basic_url = url;
    }


    private MovieDAO movieDAO;

    /**
     * Een data access object voor films
     */
    @Override
    public MovieDAO getMovieDAO() {
        if (movieDAO == null) {
            movieDAO = new ServerMovieDAO(basic_url + properties.getProperty("moviesPath"));
        }
        return movieDAO;
    }

    private ScreenDAO screenDAO;

    /**
     * Een data access object voor zalen
     */
    @Override
    public ScreenDAO getScreenDAO() {
        if (screenDAO == null) {
            screenDAO = new ServerScreenDAO(basic_url + properties.getProperty("screensPath"));
        }
        return screenDAO;
    }

    private ShowingDAO showingDAO;

    /**
     * Een preferee.data access object voor vertoningen
     */
    @Override
    public ShowingDAO getShowingDAO() {
        if (showingDAO == null) {
            showingDAO = new ServerShowingDAO(basic_url + properties.getProperty("showingsPath"));
        }
        return showingDAO;
    }

    private OrderDAO orderDAO;

    /**
     * Een data access object voor bestellingen en reservaties
     */
    @Override
    public OrderDAO getOrderDAO() {
        if (orderDAO == null) {
            orderDAO = new ServerOrderDAO(basic_url + properties.getProperty("ordersPath"),basic_url + properties.getProperty("reservationsPath"));
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
    public void close() throws Exception {
        //TODO
        System.out.print("< Provider close >");
    }
}