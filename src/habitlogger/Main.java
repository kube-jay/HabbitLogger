package habitlogger;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.sql.Connection;

public class Main {
    public static Scanner scanner;
    public static DbConnectores db;
    static String tableName = "habittable";


    public static void main(String[] args) {
        db = new DbConnectores();
        Connection conn = db.connectToDb("habitDb", "postgres", "1234");
        scanner = new Scanner(System.in);
        String tableName = "habittable";


        boolean running = true;

        while (running) {
            db.createTable(conn, tableName);
            System.out.println("-- A Habit Logger--");
            System.out.println("1. Add a new habit");
            System.out.println("2. Update a Habit");
            System.out.println("3. view a habit");
            System.out.println("4. Delete a habit");
            System.out.println("5. Search a habit");
            System.out.println("6. Search Number of Occurence of Habit");

            System.out.println("make a choice: ");
            int choice = scanner.nextInt();
            //scanner.nextInt();

            switch (choice) {
                case 1:
                    addHabit();
                    break;

                case 2:
                    updateHabit();
                    break;

                case 3:
                    viewHabits();
                    break;

                case 4:
                    deleteHabit();
                    break;

                case 5:
                    searchHabit();
                    break;

                case 6:
                    numOfOccurence();
                    break;

                default:
                    System.out.println("Invalid option!");
            }


        }
    }

    private static void viewHabits() {
        Connection conn = db.connectToDb("habitDb", "postgres", "1234");
        String tableName = "habitTable";
        db.viewRows(conn, tableName );
    }

    private static void deleteHabit() {
        System.out.println("Input Habit to delete the: ");
        String habit = scanner.next();
        Connection conn = db.connectToDb("habitDb", "postgres", "1234");
        String tableName = "habittable";
        db.deleteHabit(conn, tableName, habit );
    }

    private static  void searchHabit () {
        System.out.println("Input Habit to search: ");
        String habit = scanner.next();
        Connection conn = db.connectToDb("habitDb", "postgres", "1234");
        String tableName = "habitTable";
        db.searchHabitName(conn, tableName, habit );
    }

    public static void updateHabit(){
        System.out.println("What Habit will you change: ");
        String oldHabit = scanner.next();
        System.out.println("whats the new update? : ");
        String newHabit = scanner.next();
        Connection conn = db.connectToDb("habitDb", "postgres", "1234");
        String tableName = "habitTable";
        db.updateHabit(conn, tableName, oldHabit, newHabit);
    }

    public static void addHabit() {
        System.out.println("Enter the  Habit :");
        String habit = scanner.next();
        System.out.println("Enter date (dd/mm/yyyy): ");
        String dateStr = scanner.next();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dateStr, dtf);
        Connection conn = db.connectToDb("habitDb", "postgres", "1234");
        String tableName = "habittable";
        db.insertRow(conn, tableName,  habit, date);
    }

    public static void numOfOccurence() {
        System.out.println("Enter the Habit : ");
        String habit = scanner.next();
        Connection conn = db.connectToDb("habitDb", "postgres", "1234");
        String tableName = "habittable";
        db.numOccur(conn, tableName, habit);
    }
}

