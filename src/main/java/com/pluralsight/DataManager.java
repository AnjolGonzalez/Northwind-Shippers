package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DataManager {

    public static String url = "jdbc:mysql://localhost:3306/northwind";
    public static String user = "root";
    public static String myPassword = System.getenv("MY_DB_PASSWORD");
    public static String password = "password!";
    static String query = "SELECT * FROM Shippers";
    static String query2 = "INSERT INTO Shippers(CompanyName, Phone) VALUES (?,?)";

    static BasicDataSource dataSource = new BasicDataSource();
    static Scanner scanner = new Scanner(System.in);

    public static void setConnection() {
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
    }

    public static void shipper() {
        System.out.println("""
                Hello welcome to Northwind Shippers!
                Please enter the following information:
                Name and Phone number...
                """);
        String input = scanner.nextLine().trim();
        String[] input2 = input.split(" ");

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                PreparedStatement statement = connection.prepareStatement(query2)) {

            statement.setString(1, input2[0]);
            statement.setString(2, input2[1]);

            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    System.out.println("ID: " + results.getString("ShipperID"));
                    System.out.println("CompanyName: " + results.getString("CompanyName"));
                    System.out.println("Phone: " + results.getString("Phone"));
                    System.out.println("---------------------------");
                }
            }
            scanner.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateShipper() {
        System.out.println("""
                Welcome,
                Would you like to continue and update your phone number? (yes or no)
                """);
        String choice = scanner.nextLine().trim();

        if (choice.trim().equalsIgnoreCase("Yes")) {
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement statement = connection.prepareStatement(query);
            ) {
                try (ResultSet results = statement.executeQuery()) {
                    while (results.next()) {
                        System.out.println("ID: " + results.getString("ShipperID"));
                        System.out.println("CompanyName: " + results.getString("CompanyName"));
                        System.out.println("Phone: " + results.getString("Phone"));
                        System.out.println("---------------------------");
                    }
                }
                System.out.println("Would you like to change your information? If so, please enter the id and phone number of shipper information.");
                String input = scanner.nextLine();
                String[] choiceSplit = input.split(" ");
                String query = "UPDATE Shippers SET Phone = ? WHERE ShipperID = ? AND Phone = ?";
                System.out.println("Please enter new number");
                String userNewNumber = String.valueOf(scanner.nextLine().trim().equalsIgnoreCase(" "));

                try (
                        PreparedStatement statedQuery = connection.prepareStatement(query)
                ) {
                    statedQuery.setString(1, userNewNumber);
                    statedQuery.setString(2, choiceSplit[0]);
                    statedQuery.setString(3, choiceSplit[1]);
                    int infoUpdated = statedQuery.executeUpdate();

                    if (infoUpdated > 0) {
                        System.out.println("Updated information");
                    } else {
                        System.out.println("Please try again");
                    }
                    try (ResultSet results = statement.executeQuery()) {
                        while (results.next()) {
                            System.out.println("ID: " + results.getString("ShipperID"));
                            System.out.println("CompanyName: " + results.getString("CompanyName"));
                            System.out.println("Phone: " + results.getString("Phone"));
                            System.out.println("---------------------------");
                        }
                    }
                }
                scanner.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Have a nice day!");
            System.exit(0);
        }
    }

    public static void deleteShipper() {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    System.out.println("ID: " + results.getString("ShipperID"));
                    System.out.println("CompanyName: " + results.getString("CompanyName"));
                    System.out.println("Phone: " + results.getString("Phone"));
                    System.out.println("---------------------------");
                }
            }
            System.out.println("Please enter the ID of the shipper you would like to delete.");
            String input = scanner.nextLine().trim();

            try (
                    PreparedStatement delete = connection.prepareStatement("DELETE FROM Shippers WHERE ShipperID = ?")
            ){
                delete.setString(1, input);
                int infoDeleted = delete.executeUpdate();
                if (infoDeleted > 0) {
                    try (ResultSet results = statement.executeQuery()) {
                        while (results.next()) {
                            System.out.println("ID: " + results.getString("ShipperID"));
                            System.out.println("CompanyName: " + results.getString("CompanyName"));
                            System.out.println("Phone: " + results.getString("Phone"));
                            System.out.println("---------------------------");
                        }
                        System.out.println("Here is your updated data");
                        System.out.println("Successful");
                    }
                } else {
                    System.out.println("Please try again");
                }
                scanner.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}