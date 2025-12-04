package com.pluralsight;

import java.sql.*;
import java.util.*;

public class VehicleDao {
    private final Connection connection;

    public VehicleDao(Connection connection) {
        this.connection = connection;
    }


    public List<Vehicle> processGetByPriceRequest(double minPrice, double maxPrice) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "select * from vehicles where price between ? and ? sold = false";
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setDouble(1,minPrice);
            stmt.setDouble(2,maxPrice);

            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return vehicles;
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
    private Vehicle mapResultSetToVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getInt("VIN"),
                rs.getInt("Year"),
                rs.getString("Make"),
                rs.getString("Model"),
                rs.getString("Vehicle Type"),
                rs.getString("Color"),
                rs.getInt("Odometer"),
                rs.getDouble("price")
        );
    }
}

