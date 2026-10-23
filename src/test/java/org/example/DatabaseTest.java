package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatabaseTest {

    @BeforeEach
    void createDatabase() throws Exception {
        DBConnection.setUrl(
                "jdbc:h2:mem:"
                        + UUID.randomUUID()
                        + ";DB_CLOSE_DELAY=-1"
        );

        DBConnection.init();
    }

    @Test
    void createsRelatedTablesAndStoresARecord()
            throws Exception {

        List<TemperatureUnit> units =
                new TemperatureUnitDAO().findAll();

        assertEquals(3, units.size());

        TemperatureUnit celsius = units.get(0);
        TemperatureUnit fahrenheit = units.get(1);

        TempRecord record = new TempRecord(
                celsius.id(),
                fahrenheit.id(),
                "C",
                "F",
                100,
                212,
                150,
                3
        );

        TempRecordDAO dao = new TempRecordDAO();
        dao.insert(record);

        List<TempRecord> saved = dao.findAll();

        assertEquals(1, saved.size());
        assertEquals("C", saved.get(0).fromSymbol());
        assertEquals("F", saved.get(0).toSymbol());

        assertEquals(
                50,
                saved.get(0).speedKmh(),
                0.001
        );
    }
}