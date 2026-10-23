package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TemperatureUnitDAO {

    public List<TemperatureUnit> findAll() throws SQLException {
        List<TemperatureUnit> units = new ArrayList<>();

        Connection conn = DBConnection.get();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, name, symbol FROM temperature_unit ORDER BY id");

        while (rs.next()) {
            TemperatureUnit unit = new TemperatureUnit(rs.getInt("id"), rs.getString("name"), rs.getString("symbol"));
            units.add(unit);
        }

        rs.close();
        stmt.close();
        conn.close();
        return units;
    }

    public TemperatureUnit findBySymbol(String symbol) throws SQLException {
        TemperatureUnit unit = null;

        Connection conn = DBConnection.get();
        PreparedStatement ps = conn.prepareStatement("SELECT id, name, symbol FROM temperature_unit WHERE symbol = ?");
        ps.setString(1, symbol);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            unit = new TemperatureUnit(rs.getInt("id"), rs.getString("name"), rs.getString("symbol"));
        }

        rs.close();
        ps.close();
        conn.close();
        return unit;
    }

    public TemperatureUnit findById(int id) throws SQLException {
        TemperatureUnit unit = null;

        Connection conn = DBConnection.get();
        PreparedStatement ps = conn.prepareStatement("SELECT id, name, symbol FROM temperature_unit WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            unit = new TemperatureUnit(rs.getInt("id"), rs.getString("name"), rs.getString("symbol"));
        }

        rs.close();
        ps.close();
        conn.close();
        return unit;
    }
}
