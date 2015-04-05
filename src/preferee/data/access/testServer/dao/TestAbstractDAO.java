package preferee.data.access.testServer.dao;

import preferee.data.access.testServer.resourcesCache.LocalTestServer;

/**
 * Created by domien on 19/03/2015.
 */
public class TestAbstractDAO {

    protected LocalTestServer server;

    public TestAbstractDAO (LocalTestServer server) {
        this.server = server;
    }
}
