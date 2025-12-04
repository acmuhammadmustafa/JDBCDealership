package com.pluralsight;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class VehicleDao {
    private final Connection connection;
    Dealership dealership;
    public VehicleDao(Connection connection) {
        this.connection = connection;
    }

    private void init() {
        DealershipFileManager dealershipFileManager = new DealershipFileManager();
        this.dealership = dealershipFileManager.getDealership();
    }

    public void processGetByPriceRequest() {
        float minPrice = ConsoleHelper.promptForFloat("Please enter the minimum price range you're searching for");
        float maxPrice = ConsoleHelper.promptForFloat("Please enter the maximum price range you're searching for");

        List<Vehicle> vehicles = dealership.getVehicleByPrice(minPrice, maxPrice);
        displayVehicles(vehicles);
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }
    public void showVehicles() {

        String username = "root";
        String password = "yearup";
        String database = "SQLDealership";
        String databaseurl = "jdbc:mysql://localhost:3306/" + database;

        try (Connection connection = DriverManager.getConnection(databaseurl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT VIN, Year, Make, Model, `Vehicle Type`, Color, Odometer, price, Sold FROM vehicles");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                System.out.printf(
                        "Vehicle == " +
                                "VIN: %d" +
                                " | Year: %d" +
                                " | Make: %s" +
                                " | Model: %s" +
                                " | Vehicle Type: %s" +
                                " | Color: %s" +
                                " | Odometer/Mileage: %d" +
                                " | Price: %.2f" +
                                " ==" + '\n',
                        resultSet.getInt("VIN"),
                        resultSet.getInt("Year"),
                        resultSet.getString("Make"),
                        resultSet.getString("Model"),
                        resultSet.getString("Vehicle Type"),
                        resultSet.getString("Color"),
                        resultSet.getInt("Odometer"),
                        resultSet.getFloat("price"));
            }

        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
}

