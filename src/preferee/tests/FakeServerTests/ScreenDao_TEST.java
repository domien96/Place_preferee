package preferee.tests.FakeServerTests;

import preferee.data.Screen;
import preferee.data.access.DataAccessException;
import preferee.data.access.DataAccessProvider;
import preferee.data.access.Providers;
import preferee.data.access.ScreenDAO;

/**
 * Created by domien on 19/03/2015.
 */
public class ScreenDao_TEST {
    public static void main(String[] args) {
        try(
        DataAccessProvider provider = Providers.createTestProvider();

        ) {
            ScreenDAO dao = provider.getScreenDAO();
            System.out.println("Test1");
            System.out.println(dao.getScreen(4).getName());
            System.out.println(dao.getScreen(3).getName());
            System.out.println("Test2");
            for (Screen screen : dao.listAll()) {
                System.out.println(screen.getName());
            }
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage() + "exception");
        }

    }
}
