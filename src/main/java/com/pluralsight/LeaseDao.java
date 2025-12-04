package com.pluralsight;

import java.sql.*;

public class LeaseDao {
    private final Connection connection;

    public LeaseDao(Connection connection) {
        this.connection = connection;
    }

    public void saveLeaseContract(LeaseContract contract) {
        String query = "insert into leasescontracts (contract_date, customer_name, customer_email, " +
                "vin, expected_ending_value, lease_fee, total_price, monthly_payment) " +
                "values (?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, contract.getDate());
            stmt.setString(2, contract.getCustomerName());
            stmt.setString(3, contract.getEmail());
            stmt.setString(4, contract.getVehicleSold().getVin());
            stmt.setDouble(5, contract.getEndValue());
            stmt.setDouble(6, contract.getLeaseFee());
            stmt.setDouble(7, contract.getTotalPrice());
            stmt.setDouble(8, contract.getMonthlyPay());

            stmt.executeUpdate();

            markVehicleAsSold(contract.getVehicleSold().getVin());

            System.out.println("Lease contract saved!");
        } catch (SQLException e) {
            System.out.println("Error saving lease contract");
            e.printStackTrace();
        }
    }

    private void markVehicleAsSold(String vin) {
        String query = "update vehicles set Sold = true where vin = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(vin));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}