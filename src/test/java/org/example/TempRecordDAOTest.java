package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TempRecordDAOTest {

    private TempRecordDAO dao = new TempRecordDAO();
    private int celsiusId;
    private int fahrenheitId;

    @BeforeEach
    void setUp() throws Exception {
        DBTestSupport.freshDb();
        TemperatureUnitDAO unitDao = new TemperatureUnitDAO();
        celsiusId = unitDao.findBySymbol("C").getId();
        fahrenheitId = unitDao.findBySymbol("F").getId();
    }

    @Test
    void insertAndFindAll() throws Exception {
        int id = dao.insert(new TempRecord(celsiusId, fahrenheitId, 100, 212, 150, 3));
        assertTrue(id > 0);

        List<TempRecord> all = dao.findAll();
        assertEquals(1, all.size());

        TempRecord record = all.get(0);
        assertEquals("C", record.getFromSymbol());
        assertEquals("F", record.getToSymbol());
        assertEquals(212, record.getResultValue(), 0.0001);
        assertEquals(50, record.getSpeedKmh(), 0.0001);
    }

    @Test
    void deleteRemovesTheRecord() throws Exception {
        int id = dao.insert(new TempRecord(celsiusId, fahrenheitId, 0, 32, 10, 1));
        assertEquals(1, dao.count());

        assertTrue(dao.delete(id));
        assertEquals(0, dao.count());
    }

    @Test
    void deleteMissingRecordReturnsFalse() throws Exception {
        assertFalse(dao.delete(12345));
    }

    @Test
    void unitThatDoesNotExistIsRejected() {
        TempRecord bad = new TempRecord(999, fahrenheitId, 0, 0, 1, 1);
        assertThrows(SQLException.class, () -> dao.insert(bad));
    }
}
