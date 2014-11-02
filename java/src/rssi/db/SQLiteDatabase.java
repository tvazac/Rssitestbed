package rssi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * SQLite database representation.
 */
public class SQLiteDatabase implements Database {

    Connection connection;

    @Override
    public void prepareDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:rssi.db");
            createRssiTable();
            createRssiNoiseFloorTable();
        } catch (Exception e) {
            System.err.println("Unable to connect to rssi.db: " + e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public void insertRssiRow(int moteId, int sourceId, int rssi) {
        StringBuilder insertQuery = new StringBuilder();
        insertQuery.append("INSERT INTO rssi VALUES(");
        insertQuery.append(moteId);
        insertQuery.append(",");
        insertQuery.append(sourceId);
        insertQuery.append(",");
        insertQuery.append(rssi);
        insertQuery.append(",");
        insertQuery.append(new Date().getTime());
        insertQuery.append(");");

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery.toString());
        } catch (SQLException e) {
            System.err.println("Unable to insert rssi row: " + e.getMessage());
        }
    }

    @Override
    public void insertRssiNoiseFloor(int moteId, int rssiNoiseFloor) {
        StringBuilder insertQuery = new StringBuilder();
        insertQuery.append("INSERT INTO rssiNoiseFloor VALUES(");
        insertQuery.append(moteId);
        insertQuery.append(",");
        insertQuery.append(rssiNoiseFloor);
        insertQuery.append(",");
        insertQuery.append(new Date().getTime());
        insertQuery.append(");");

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery.toString());
        } catch (SQLException e) {
            System.err.println("Unable to insert rssi row: " + e.getMessage());
        }
    }

    private void createRssiTable() {
        executeCreateStatement("CREATE TABLE IF NOT EXISTS rssi(node_id INTEGER, source_id INTEGER, rssi INTEGER,"
                + "timestamp INTEGER);");
    }

    private void createRssiNoiseFloorTable() {
        executeCreateStatement("CREATE TABLE IF NOT EXISTS rssiNoiseFloor(node_id INTEGER, rssi_noise_floor INTEGER,"
                + "timestamp INTEGER);");
    }

    private void executeCreateStatement(String createStatement) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("Can not create SQL statement.");
            System.exit(-1);
        }

        try {
            statement.executeUpdate(createStatement);
        } catch (SQLException e) {
            System.err.println("Can not execute create statement: " + createStatement + ". Error: " + e.getMessage());
            System.exit(-1);
        }
    }

}
