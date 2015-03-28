package preferee.data.access.server;

/**
 * Created by domien on 11/03/2015.
 * Abstracte bovenklasse van alle JDBC-DAO'
 */
public class ServerAbstractDAO {

    /**
     * URL naar de lijst van objecten specifiek voor deze DAO.
     * bv. www.esari.ugent.be/movies (= lijst van alle movies)
     */
    protected String orders_URL;

    protected ServerAbstractDAO(String serverURL) {
        this.orders_URL = serverURL;
    }

    /**
     * TODO
    ResourceWebReader singleResourceWebReader ;
    ResourceWebReader multipleResourceWebReader ;
    */
}
