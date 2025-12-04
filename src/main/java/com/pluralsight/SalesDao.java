package com.pluralsight;

import java.sql.*;

public class SalesDao {
    private final Connection connection;

    public SalesDao(Connection connection){
        this.connection = connection;
    }

    public void saveSalesContract(SalesContract contract){
        String query = "insert into salescontracts (contract_date, customer_name, customer_email, "
                + "vin, sales_tax, recording_fee, processing_fee, total_price, financed, monthly_payment)"
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, contract.getDate());
            stmt.setString(2, contract.getCustomerName());
            stmt.setString(3, contract.getEmail());
            stmt.setString(4, String.valueOf(contract.getVehicleSold().getVin()));
            stmt.setDouble(5, contract.getSalesTax());
            stmt.setDouble(6, contract.getRecordingFee());
            stmt.setDouble(7, contract.getProcessFee());
            stmt.setDouble(8, contract.getTotalPrice());
            stmt.setBoolean(9, contract.isFinance());
            stmt.setDouble(10, contract.getMonthlyPay());

            stmt.executeUpdate();

            markVehicleAsSold(contract.getVehicleSold().getVin());

            System.out.println("Sales contract saved!");
        } catch (SQLException e) {
            System.out.println("Error saving sales contract");
            e.printStackTrace();
        }
    }

    private void markVehicleAsSold(int vin) {
        String query = "update vehicles set Sold = true where vin = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(vin));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
