package preferee.tests.RealServerTests;

import preferee.data.Screen;
import preferee.data.access.DataAccessException;
import preferee.data.access.DataAccessProvider;
import preferee.data.access.Providers;
import preferee.data.access.ScreenDAO;

import java.net.URL;

/**
 * Created by domien on 24/03/2015.
 */
public class ScreenDao_TEST {
    DataAccessProvider dap;
    public static void main(String[] args) {
        new ScreenDao_TEST().run();
    }


    public void run() {
        try(
                DataAccessProvider dap = Providers.createProvider(new URL("http://esari.ugent.be"));

        ) {
            System.out.println(this.getClass().getName());
            this.dap = dap;
            testGetters();
            testGetAll();

        } catch (DataAccessException e) {
            System.err.println("DataAccesException opgevangen in TEST: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(e.getClass() + ":exception" +" opgevangen in TEST" + e.getMessage());
        }

    }

    public void testGetters() {
        System.out.println("----------------\ntestGetters \n----------------");
        ScreenDAO dao = dap.getScreenDAO();
        System.out.println(dao.getScreen(2).getName());
        //System.out.println(dao.getScreen(333).getName());

    }

    public void testGetAll() {
        System.out.println("----------------\ntestGetAll \n----------------");
        ScreenDAO dao = dap.getScreenDAO();
        int count = 1;
        for ( Screen screen : dao.listAll() ) {
            System.out.println(count + " : " + screen.getName());
            count++;
        }
    }
}
