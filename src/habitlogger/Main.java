package habitlogger;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.sql.Connection;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static DbConnectores db = new DbConnectores();

    public static void main(String[] args) {

        db.createTable();

        boolean running = true;

        while (running) {
            System.out.println("-- A Habit Logger--");
            System.out.println("1. Add a new habit");
            System.out.println("2. Update a Habit");
            System.out.println("3. view a habit");
            System.out.println("4. Delete a habit");
            System.out.println("5. Search a habit");
            System.out.println("6. Search Number of Occurence of Habit");
            System.out.println("7. Exit");

            System.out.println("make a choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 ->addHabit();
                case 2 -> updateHabit();
                case 3 -> db.viewRows();
                case 4 -> deleteHabit();
                case 5 -> searchHabit();
                case 6 -> numOfOccurence();
                case 7 -> running = false;
                default -> System.out.println("Invalid option!");
            }


        }
    }



    private static void deleteHabit() {
        System.out.println("Input Habit to delete:");
        String habit = scanner.next();
        db.deleteHabit(habit);
    }

    private static  void searchHabit () {
        System.out.println("Input Habit to search:");
        String habit = scanner.next();
        db.searchHabitName(habit);
    }

    public static void updateHabit(){
        System.out.println("What Habit will you change?");
        String oldHabit = scanner.next();
        System.out.println("What's the new update?");
        String newHabit = scanner.next();
        db.updateHabit(oldHabit, newHabit);
    }

    public static void addHabit() {
        System.out.println("Enter the  Habit :");
        String habit = scanner.next();
        System.out.println("Enter date (dd/mm/yyyy): ");
        String dateStr = scanner.next();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dateStr, dtf);
        db.insertRow(habit, date);
    }

    public static void numOfOccurence() {
        System.out.println("Enter the Habit:");
        String habit = scanner.next();
        db.numOccur(habit);
    }
}

