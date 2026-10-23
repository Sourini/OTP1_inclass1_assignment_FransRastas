package org.example;

import java.sql.SQLException;
import java.util.UUID;

final class DBTestSupport {
    private DBTestSupport() { }
    static void freshDb() throws SQLException {
        DBConnection.setUrl("jdbc:h2:mem:" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1");
        DBConnection.init();
    }
}
