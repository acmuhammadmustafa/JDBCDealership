package com.pluralsight;

import java.io.*;
import java.rmi.dgc.Lease;

public class ContractDataManager {
    public void saveContract(Contract contract) {
        try {
            FileWriter fw = new FileWriter("contracts.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
        if (contract instanceof SalesContract sale) {

                //Create a FileWriter and BufferedWriter:

//            if (sale.isFinance() == true){
//                sale.isFinance() = "Yes";
//            }

            String saleData = "Sale" + "|" +
                        sale.getDate() + "|" +
                        sale.getCustomerName() + "|" +
                        sale.getEmail() + "|" +
                        sale.getVehicleSold().getVin() + "|" +
                        sale.getVehicleSold().getYear() + "|" +
                        sale.getVehicleSold().getMake() + "|" +
                        sale.getVehicleSold().getModel() + "|" +
                        sale.getVehicleSold().getVehicleType() + "|" +
                        sale.getVehicleSold().getColor() + "|" +
                        sale.getVehicleSold().getOdometer() + "|" +
                        sale.getVehicleSold().getPrice() + "|" +
                        sale.getSalesTax() + "|" +
                        sale.getRecordingFee() + "|" +
                        sale.getProcessFee() + "|" +
                        sale.getTotalPrice() + "|" +
                        sale.isFinance() + "|" +
                        sale.getMonthlyPay();

                bw.write(saleData);
                bw.newLine();
                System.out.println("Sale Contract saved successfully!");

            }


        if (contract instanceof LeaseContract lease) {

            String leaseData = "Lease" + "|" +
                        lease.getDate() + "|" +
                        lease.getCustomerName() + "|" +
                        lease.getEmail() + "|" +
                        lease.getVehicleSold().getVin() + "|" +
                        lease.getVehicleSold().getYear() + "|" +
                        lease.getVehicleSold().getMake() + "|" +
                        lease.getVehicleSold().getModel() + "|" +
                        lease.getVehicleSold().getVehicleType() + "|" +
                        lease.getVehicleSold().getColor() + "|" +
                        lease.getVehicleSold().getOdometer() + "|" +
                        lease.getVehicleSold().getPrice() + "|" +
                        lease.getEndValue() + "|" +
                        lease.getLeaseFee() + "|" +
                        lease.getTotalPrice() + "|" +
                        lease.getMonthlyPay();

                bw.write(leaseData);
                bw.newLine();

                System.out.println("Lease Contract saved successfully!");

            }
        bw.close();
            } catch (IOException e) {
            System.out.println("Something went wrong..");
        } 
    }
}