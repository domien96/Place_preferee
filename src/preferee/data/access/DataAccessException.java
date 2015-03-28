package preferee.data.access;

/**
 * Uitzondering die kan worden opgegooid wanneer één van de data access methoden een fout veroorzaakt.
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException() { super(); }

    public DataAccessException(String boodschap) { super(boodschap); }
}
