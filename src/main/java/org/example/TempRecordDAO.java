package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TempRecordDAO {

    public int insert(TempRecord record) throws SQLException {
        Connection conn = DBConnection.get();
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO temp_record (from_unit_id, to_unit_id, input_value, result_value, distance_km, time_hours, speed_kmh) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, record.getFromUnitId());
        ps.setInt(2, record.getToUnitId());
        ps.setDouble(3, record.getInputValue());
        ps.setDouble(4, record.getResultValue());
        ps.setDouble(5, record.getDistanceKm());
        ps.setDouble(6, record.getTimeHours());
        ps.setDouble(7, record.getSpeedKmh());
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.next();
        int newId = keys.getInt(1);

        keys.close();
        ps.close();
        conn.close();
        return newId;
    }

    public List<TempRecord> findAll() throws SQLException {
        List<TempRecord> records = new ArrayList<>();

        Connection conn = DBConnection.get();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT r.id, r.from_unit_id, r.to_unit_id, f.symbol AS from_symbol, t.symbol AS to_symbol, "
                        + "r.input_value, r.result_value, r.distance_km, r.time_hours, r.speed_kmh "
                        + "FROM temp_record r "
                        + "JOIN temperature_unit f ON r.from_unit_id = f.id "
                        + "JOIN temperature_unit t ON r.to_unit_id = t.id "
                        + "ORDER BY r.id");

        while (rs.next()) {
            TempRecord record = new TempRecord(
                    rs.getInt("id"),
                    rs.getInt("from_unit_id"),
                    rs.getInt("to_unit_id"),
                    rs.getString("from_symbol"),
                    rs.getString("to_symbol"),
                    rs.getDouble("input_value"),
                    rs.getDouble("result_value"),
                    rs.getDouble("distance_km"),
                    rs.getDouble("time_hours"),
                    rs.getDouble("speed_kmh"));
            records.add(record);
        }

        rs.close();
        stmt.close();
        conn.close();
        return records;
    }

    public boolean delete(int id) throws SQLException {
        Connection conn = DBConnection.get();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM temp_record WHERE id = ?");
        ps.setInt(1, id);
        int rowsDeleted = ps.executeUpdate();

        ps.close();
        conn.close();
        return rowsDeleted > 0;
    }

    public int count() throws SQLException {
        Connection conn = DBConnection.get();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM temp_record");
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        stmt.close();
        conn.close();
        return count;
    }
}
