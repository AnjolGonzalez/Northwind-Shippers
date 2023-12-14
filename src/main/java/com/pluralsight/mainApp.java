package com.pluralsight;
import static com.pluralsight.DataManager.*;

import java.util.Scanner;

public class mainApp {
    public static void main(String[] args) {
        DataManager.setConnection();
        System.out.println("""
                Welcome,
                What would you like to do?
                1) Add a new shipper
                2) Update shipper information
                3) Delete shipper information
                0 Exit
                """);
        String choice = scanner.nextLine();
        switch (choice) {

            case "1":
                DataManager.shipper();
                break;
            case "2":
                DataManager.updateShipper();
                break;
            case "3":
                DataManager.deleteShipper();
                break;
            case "0":
                System.exit(0);
        }
    }
}
