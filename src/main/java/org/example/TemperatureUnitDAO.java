package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TemperatureUnitDAO {

    public List<TemperatureUnit> findAll() throws SQLException {
        List<TemperatureUnit> units = new ArrayList<>();

        String sql = """
                SELECT id, name, symbol
                FROM temperature_unit
                ORDER BY id
                """;

        try (
                Connection connection = DBConnection.get();
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(sql)
        ) {
            while (results.next()) {
                TemperatureUnit unit = new TemperatureUnit(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getString("symbol")
                );

                units.add(unit);
            }
        }

        return units;
    }
}