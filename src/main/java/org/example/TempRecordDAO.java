package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TempRecordDAO {

    public void insert(TempRecord record) throws SQLException {
        String sql = """
                INSERT INTO temp_record (
                    from_unit_id,
                    to_unit_id,
                    input_value,
                    result_value,
                    distance_km,
                    time_hours,
                    speed_kmh
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.get();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setInt(1, record.fromUnitId());
            statement.setInt(2, record.toUnitId());
            statement.setDouble(3, record.inputValue());
            statement.setDouble(4, record.resultValue());
            statement.setDouble(5, record.distanceKm());
            statement.setDouble(6, record.timeHours());
            statement.setDouble(7, record.speedKmh());

            statement.executeUpdate();
        }
    }

    public List<TempRecord> findAll() throws SQLException {
        List<TempRecord> records = new ArrayList<>();

        String sql = """
                SELECT
                    r.*,
                    from_unit.symbol AS from_symbol,
                    to_unit.symbol AS to_symbol
                FROM temp_record r
                JOIN temperature_unit from_unit
                    ON r.from_unit_id = from_unit.id
                JOIN temperature_unit to_unit
                    ON r.to_unit_id = to_unit.id
                ORDER BY r.id
                """;

        try (
                Connection connection = DBConnection.get();
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(sql)
        ) {
            while (results.next()) {
                TempRecord record = new TempRecord(
                        results.getInt("id"),
                        results.getInt("from_unit_id"),
                        results.getInt("to_unit_id"),
                        results.getString("from_symbol"),
                        results.getString("to_symbol"),
                        results.getDouble("input_value"),
                        results.getDouble("result_value"),
                        results.getDouble("distance_km"),
                        results.getDouble("time_hours"),
                        results.getDouble("speed_kmh")
                );

                records.add(record);
            }
        }

        return records;
    }
}