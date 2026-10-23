package org.example;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import org.junit.jupiter.api.Test;

class DBConnectionTest {
    @Test void initCreatesSchemaAndSeeds() throws Exception {
        DBTestSupport.freshDb();
        DBConnection.init(); // idempotent
        assertEquals(3, new TemperatureUnitDAO().findAll().size());
    }
    @Test void getReturnsOpenConnection() throws Exception {
        DBTestSupport.freshDb();
        try (Connection c = DBConnection.get()) { assertFalse(c.isClosed()); }
        assertTrue(DBConnection.getUrl().startsWith("jdbc:h2:mem:"));
    }
}
