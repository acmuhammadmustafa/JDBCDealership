package com.pluralsight;

public class SalesContract extends Contract {

    private double salesTax;
    private double recordingFee;
    private double processFee;
    private boolean finance;

    public SalesContract(String date, String customerName, String email, Vehicle vehicleSold, boolean finance) {
        super(date, customerName, email, vehicleSold);
        this.salesTax = 0.05;
        this.recordingFee = 100;
        this.processFee = compute_processing_fee(vehicleSold.getPrice());
        this.finance = finance;
    }

    public double getSalesTax() {

        return salesTax;
    }

    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public double getProcessFee() {
        return processFee;
    }

    public void setProcessFee(double processFee) {
        this.processFee = processFee;
    }

    public boolean isFinance() {
        return finance;
    }

    public void setFinance(boolean finance) {
        this.finance = finance;
    }

    @Override
    public double getMonthlyPay() {
        // If not financed, return 0
        if (!finance) {
            return 0;
        }

        // Get total price when finance is true.
        double totalPrice = getTotalPrice();

        if (totalPrice >= 10000) {
            // $10,000 or more = 4.25% for 48 months
            double interestRate = 0.0425;
            int months = 48;

            return Calculations.getLoanPayment(totalPrice, months, interestRate);

        } else {
            // Under $10,000 = 5.25% for 24 months
            double interestRate = 0.0525;
            int months = 24;

            return Calculations.getLoanPayment(totalPrice,months,interestRate);
        }
    }

    public double compute_processing_fee(double price){
        if(price<10000){
            return 295;
        } else{
            return 495;
        }
    }

    @Override
    public double getTotalPrice() {
        double vehiclePrice = getVehicleSold().getPrice(); // Grabs the vehicle price.
        //salesTax = vehiclePrice * .05; // Converting it into decimal.

        //recordingFee = 100;
        if (vehiclePrice < 10000) {
            processFee = 295;
        } else {
            processFee = 495;
        }

        double totalPrice = salesTax * recordingFee + processFee + vehiclePrice;

        return  totalPrice;
    }
}