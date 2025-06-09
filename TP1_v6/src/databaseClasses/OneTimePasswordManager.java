package databaseClasses;

import java.sql.*;

/**
 * Utility class to manage one-time passwords for users. It maintains a
 * separate table that stores a temporary password for a user. When the user
 * logs in with the temporary password, the entry is removed.
 */
public class OneTimePasswordManager {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/FoundationDatabase";
    private static final String USER = "sa";
    private static final String PASS = "";

    private Connection connection;
    private Statement statement;

    public OneTimePasswordManager() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        String table = "CREATE TABLE IF NOT EXISTS oneTimePasswords(" +
                "userName VARCHAR(255) PRIMARY KEY, " +
                "otp VARCHAR(255))";
        statement.execute(table);
    }

    /**
     * Store or replace a one-time password for a user.
     */
    public void setOneTimePassword(String username, String otp) {
        String query = "MERGE INTO oneTimePasswords KEY(userName) VALUES(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, otp);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve the one-time password for the specified user or null if none
     * exists.
     */
    public String getOneTimePassword(String username) {
        String query = "SELECT otp FROM oneTimePasswords WHERE userName = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("otp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Remove the one-time password for the user so it cannot be reused.
     */
    public void clearOneTimePassword(String username) {
        String query = "DELETE FROM oneTimePasswords WHERE userName = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}