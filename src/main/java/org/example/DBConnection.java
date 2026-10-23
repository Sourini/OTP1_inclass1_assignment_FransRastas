package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static String url = "jdbc:h2:./data/tempdb";

    static {
        String fromEnv = System.getenv("DB_URL");
        if (fromEnv != null) {
            url = fromEnv;
        }
    }

    public static void setUrl(String newUrl) {
        url = newUrl;
    }

    public static String getUrl() {
        return url;
    }

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(url, "sa", "");
    }

    public static void init() throws SQLException {
        createTables();
        insertUnits();
    }

    private static void createTables() throws SQLException {
        Connection conn = get();
        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS temperature_unit ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(30) NOT NULL, "
                + "symbol VARCHAR(2) NOT NULL)");

        stmt.execute("CREATE TABLE IF NOT EXISTS temp_record ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "from_unit_id INT NOT NULL, "
                + "to_unit_id INT NOT NULL, "
                + "input_value DOUBLE NOT NULL, "
                + "result_value DOUBLE NOT NULL, "
                + "distance_km DOUBLE NOT NULL, "
                + "time_hours DOUBLE NOT NULL, "
                + "speed_kmh DOUBLE NOT NULL, "
                + "FOREIGN KEY (from_unit_id) REFERENCES temperature_unit(id), "
                + "FOREIGN KEY (to_unit_id) REFERENCES temperature_unit(id))");

        stmt.close();
        conn.close();
    }

    private static void insertUnits() throws SQLException {
        Connection conn = get();
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM temperature_unit");
        rs.next();
        int count = rs.getInt(1);

        if (count == 0) {
            stmt.execute("INSERT INTO temperature_unit (name, symbol) VALUES ('Celsius', 'C')");
            stmt.execute("INSERT INTO temperature_unit (name, symbol) VALUES ('Fahrenheit', 'F')");
            stmt.execute("INSERT INTO temperature_unit (name, symbol) VALUES ('Kelvin', 'K')");
        }

        stmt.close();
        conn.close();
    }
}
