package habitlogger;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class DbConnectores {
    public Connection connectToDb(String dbname, String user, String pass) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if (conn != null) {
                System.out.println("Habit data loaded!");
            }
        } catch (Exception e) {
            System.out.println("Data not loaded!" + e);
        }
        return conn;
    }

    public void createTable(Connection conn, String tableName) {
        Statement statement;
        try {
            String query = "CREATE TABLE IF NOT EXISTS " + tableName + " " +
                    "(id SERIAL PRIMARY KEY, habitname varchar(255) NOT NULL, habitdate DATE NOT NULL)";
            statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertRow(Connection conn, String tableName, String habitName, LocalDate date) {
        Statement statement;
        try {
            String query = "insert into "+tableName +" (habitname, habitdate) values ('"+habitName+"', '"+date+"')";
            statement = conn.createStatement();
            statement.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void viewRows(Connection conn, String tableName) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "Select * from "+tableName+" ";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            System.out.println("ID\t\tHabit\t\tDate ");
            while (rs.next()) {
                System.out.print(rs.getString("id")+"\t\t");
                System.out.print(rs.getString("habitname")+"\t\t");
                System.out.println(rs.getString("habitdate")+"\t");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void updateHabit(Connection conn, String tableName, String oldHabit, String newHabit) {
        Statement statement;
        try {
            String query = "Update  "+tableName+" set habitname = '"+newHabit+"' where habitname = '"+oldHabit+"'  ";
            statement = conn.createStatement();
            statement.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void searchHabitName (Connection conn, String tableName, String habitname) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from "+tableName+" where habitname = '"+habitname+"'";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            System.out.println("ID\t\tHabit\t\tDate ");
            while (rs.next()) {
                System.out.print(rs.getString("id")+"\t\t");
                System.out.print(rs.getString("habitname")+"\t\t");
                System.out.println(rs.getString("habitdate")+"\t");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void numOccur (Connection conn, String tableName, String habitname) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select count(habitname) from "+tableName+" where habitname = '"+habitname+"'";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
            int count = rs.getInt(1);
                System.out.println("The habit occured "+count+" time(s)");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteHabit(Connection conn, String tableName, String habitname) {
        Statement statement;
        try {
            String query = "delete from "+tableName+" where habitname= '"+habitname+"'";
            statement = conn.createStatement();
            statement.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
