package preferee.data.access;

/**
 * Moederobject dat gebruikt wordt om toegang te krijgen tot de gegevens. Dit gebeurt indirect: je vraagt
 * eerst een gepast data access object (DAO) op bij de provider en voert dan de betreffende methode uit
 * op die DAO.
 * Er zijn meerdere implementaties van
 * deze interface:
 * <ul>
 *     <li>Een productie-implementatie die toelaat om gegevens op te vragen / op te slaan op de centrale server </li>
 *     <li>Een test-implementatie waarmee de toepassing kan getest worden zonder een netwerkverbinding nodig te hebben</li>
 * </ul>
 * Er wordt één object van dit type aangemaakt wanneer de (GUI)-toepassing opstart en ditzelfde object blijft gebruikt worden
 * zolang de toepassing loopt. Bij het afsluiten van de toepassing moet ook de data access provider
 * worden afgesloten met behulp van {@link #close}.
 *
 * @see Providers
 *
 * Created by domien Van Steendam
 */
public interface DataAccessProvider extends AutoCloseable {

    /**
     * Een data access object voor films
     */
    public MovieDAO getMovieDAO();

    /**
     * Een data access object voor zalen
     */
    public ScreenDAO getScreenDAO();

    /**
     * Een preferee.data access object voor vertoningen
     */
    public ShowingDAO getShowingDAO();

    /**
     * Een data access object voor bestellingen en reservaties
     */
    public OrderDAO getOrderDAO();
}
