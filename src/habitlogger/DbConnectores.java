package habitlogger;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.*;
import java.util.Scanner;

public class DbConnectores {
    public static Scanner scanner = new Scanner(System.in);
    private static final String URL = "jdbc:postgresql://localhost:5432/habitDb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    private static final String TABLE_NAME = "habittable";
    public Connection connectToDb() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Data not loaded!" + e.getMessage());
            return null;
        }

    }

    public void executeUpdate(String query, Object... params) {
        try (Connection conn = connectToDb();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters dynamically
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error executing update: " + e.getMessage());
        }
    }



    public ResultSet executeQuery(Connection conn, String query, Object... params) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query);

        // Set parameters dynamically
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        return stmt.executeQuery();


    }

    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " +
                "(id SERIAL PRIMARY KEY, habitname varchar(255) NOT NULL, habitdate DATE NOT NULL)";
        executeUpdate(query);
    }

    public void insertRow(String habitName, LocalDate date) {
        String query = "insert into "+TABLE_NAME +" (habitname, habitdate) values (?, ?)";
        executeUpdate(query, habitName, date);
    }

    public void viewRows() {
        String query = "SELECT * FROM " + TABLE_NAME;
        try (Connection conn = connectToDb();
             ResultSet rs = executeQuery( conn, query)) {
            System.out.println("ID\t\tHabit\t\tDate");
            while ( rs.next()) {
                System.out.print(rs.getInt("id") + "\t\t");
                System.out.print(rs.getString("habitname") + "\t\t");
                System.out.println(rs.getDate("habitdate"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing habits: " + e.getMessage());
        }
    }

    public void updateHabit(String oldHabit, String newHabit) {
        String query = "UPDATE " + TABLE_NAME + " SET habitname = ? WHERE habitname = ?";
        executeUpdate(query, newHabit, oldHabit);
    }

    public void searchHabitName (String habitName) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE habitname = ?";
        try (Connection conn = connectToDb();
             ResultSet rs = executeQuery(conn, query, habitName)) {
            System.out.println("ID\t\tHabit\t\tDate");
            while (rs != null && rs.next()) {
                System.out.print(rs.getInt("id") + "\t\t");
                System.out.print(rs.getString("habitname") + "\t\t");
                System.out.println(rs.getDate("habitdate"));
            }
        } catch (SQLException e) {
            System.out.println("Error searching habits: " + e.getMessage());
        }

    }

    public void numOccur (String habitName) {
        String query = "SELECT COUNT(habitname) FROM " + TABLE_NAME + " WHERE habitname = ?";
        try (Connection conn = connectToDb();
             ResultSet rs = executeQuery(conn, query, habitName)) {
            if (rs != null && rs.next()) {
                int count = rs.getInt(1);
                System.out.println("The habit occurred " + count + " time(s)");
            }
        } catch (SQLException e) {
            System.out.println("Error counting habit occurrences: " + e.getMessage());
        }
    }

    public void deleteHabit(String habitName) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE habitname = ?";
        executeUpdate(query, habitName);
    }


}
