package preferee.data.access;

import preferee.data.access.server.ProductieProvider;
import preferee.data.access.testServer.TestProvider;

import java.net.URL;

/**
 * Deze klasse geeft toegang tot twee data access providers.
 *
 * <p>In het begin van de toepassing wordt één van de
 * {@code create}-methoden gebruikt om het provider-object aan te maken dat voor de rest van de toepassing zal dienen.
 * Op het einde van de toepassing wordt deze provider gesloten. Binnen één run van de toepassing
 * mag er slechts één van deze methoden worden opgeroepen en dit hoogstens één keer.
 *
 * Created by domien Van Steendam
 */
public final class Providers {

    /**
     *
     */
    public static DataAccessProvider provider = null;

    /**
     * Creëer een provider die kan gebruikt worden om te testen. Deze provider houdt zijn gegevens bij in het
     * intern geheugen. Bij het opstarten worden een reeks vaste standaardgegevens ingeladen.
     */
    public static DataAccessProvider createTestProvider () {
        if (provider == null) {
            provider = new TestProvider();
        }
        return provider;

    }

    /**
     * Creëer een provider die kan gebruikt worden 'in productie'. Deze provider communiceert met een server.
     * @param url basis-url van de server, bijvoorbeeld {@code http://esari.ugent.be/}.
     */
    public static DataAccessProvider createProvider (URL url) {
        if (provider == null) {
            provider = new ProductieProvider(url);
        }
        return provider;
    }
}
