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
        String query = "select * from vehicles where price between ? and ? and sold = false";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> processGetByMakeModelRequest(String make, String model) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "select * from vehicles where make = ? and model = ? and sold = false";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, make);
            stmt.setString(2, model);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return vehicles;
    }

    public List<Vehicle> processGetByColorRequest(String color) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "select * from vehicles where color = ? and sold = false";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, color);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return vehicles;
    }

    public List<Vehicle> processGetByMileageRequest(int minMileage, int maxMileage) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "select * from vehicles where mileage between ? and ? and sold = false";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, minMileage);
            stmt.setInt(2, maxMileage);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return vehicles;
    }

    public List<Vehicle> processGetByVehicleTypeRequest(String type) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "select * from vehicles where `Vehicle Type` = ? and sold = false";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, type);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return vehicles;
    }

    public List<Vehicle> getAllVehicle() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "select * from vehicles where sold = false";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return vehicles;
    }

    // -----------------------------------------------
    public void addVehicles(Vehicle vehicle) {

        String query = "insert into vehicles (VIN, Year, Make, Model, `Vehicle Type`, Color, Odometer, price, sold from vehicles)"
                + "values (?, ?, ?, ?, ?, ?, ?, ?, false)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicle.getVin());
            stmt.setInt(2, vehicle.getYear());
            stmt.setString(3, vehicle.getMake());
            stmt.setString(4, vehicle.getModel());
            stmt.setString(5, vehicle.getVehicleType());
            stmt.setString(6, vehicle.getColor());
            stmt.setInt(7, vehicle.getOdometer());
            stmt.setDouble(8, vehicle.getPrice());
            stmt.executeUpdate();
            System.out.println("Vehicle added successfully!");
        } catch (SQLException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public void removeVehicles(int vin) throws SQLException{
        String query = "delete from vehicles where vin = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1,vin);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Vehicle removed!");
            } else {
                System.out.println("Vehicle VIN: " + vin + "not found");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private Vehicle mapResultSetToVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getString("VIN"),
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