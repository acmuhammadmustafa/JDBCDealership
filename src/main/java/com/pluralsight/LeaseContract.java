package com.pluralsight;

public class LeaseContract extends Contract{

   private double endValue;
   private double leaseFee;

    public LeaseContract(String date, String customerName, String email, Vehicle vehicleSold) {
        super(date, customerName, email, vehicleSold);
        this.endValue = endValue;
        this.leaseFee = leaseFee;
    }

    public double getEndValue() {
        return endValue;
    }

    public void setEndValue(double endValue) {
        this.endValue = endValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    public void setLeaseFee(double leaseFee) {
        this.leaseFee = leaseFee;
    }

    @Override
    public double getMonthlyPay() {
        double vehiclePrice = getVehicleSold().getPrice();

        double interestRate = 0.04;
        int months = 36;


        return Calculations.getLoanPayment(vehiclePrice,months,interestRate);
    }

    @Override
    public double getTotalPrice() {
        double vehiclePrice = getVehicleSold().getPrice();
        endValue = vehiclePrice/2;
        leaseFee = vehiclePrice * .07;


        return vehiclePrice * endValue * leaseFee;
    }
}
