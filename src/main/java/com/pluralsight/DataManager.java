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
    public static String password = "Campeon2!";
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
        String input = String.valueOf(scanner.nextLine().trim().equalsIgnoreCase(""));
        String[] input2 = input.split(" ");

        try (
                Connection connection = dataSource.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(query);

                PreparedStatement statement = connection.prepareStatement(query2)) {

                    statement.setString(1, input2[0]);
                    statement.setString(2, input2[0]);
                    int values = statement.executeUpdate();

                    try (ResultSet results = preparedStatement.executeQuery()) {
                        while (results.next()) {
                            System.out.println("ID: " + results.getString("ShipperID"));
                            System.out.println("CompanyName: " + results.getString("CompanyName"));
                            System.out.println("Phone: " + results.getString("Phone"));
                            System.out.println("---------------------------");
                        }
                    }

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateShipper() {
        System.out.println("""
                Welcome,
                Would you like to continue and update your phone number? (yes or no)
                """);
        String choice = String.valueOf(scanner.nextLine().trim().equalsIgnoreCase(" "));

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

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            System.out.println("Have a nice day!");
            System.exit(0);
        }
    }


}
