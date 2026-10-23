package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TemperatureUnitDAOTest {

    private TemperatureUnitDAO dao = new TemperatureUnitDAO();

    @BeforeEach
    void setUp() throws Exception {
        DBTestSupport.freshDb();
    }

    @Test
    void findAllReturnsTheThreeUnits() throws Exception {
        assertEquals(3, dao.findAll().size());
        assertEquals("C", dao.findAll().get(0).getSymbol());
    }

    @Test
    void findBySymbolFindsKelvin() throws Exception {
        assertEquals("Kelvin", dao.findBySymbol("K").getName());
    }

    @Test
    void findBySymbolReturnsNullWhenMissing() throws Exception {
        assertNull(dao.findBySymbol("Z"));
    }

    @Test
    void findByIdFindsFahrenheit() throws Exception {
        int id = dao.findBySymbol("F").getId();
        assertEquals("Fahrenheit", dao.findById(id).getName());
    }

    @Test
    void findByIdReturnsNullWhenMissing() throws Exception {
        assertNull(dao.findById(9999));
    }
}
