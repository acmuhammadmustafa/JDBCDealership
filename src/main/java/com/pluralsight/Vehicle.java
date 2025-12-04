package com.pluralsight;

public class Vehicle {

    private int vin;
    private int year;
    private String make;
    private String model;
    private String vehicleType;
    private String color;
    private int odometer;
    private double price;

    public Vehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer, double price){
        this.price = price;
        this.odometer = odometer;
        this.color = color;
        this.vehicleType = vehicleType;
        this.model = model;
        this.make = make;
        this.year = year;
        this.vin = vin;
    }

    public int getVin() {
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



// -----------------------------------------------------------------------



    @Override
    public String toString() {
        return "Vehicle == " +
                "VIN: " + vin +
                " | Year: " + year +
                " | Make: " + make +
                " | Model: " + model +
                " | Vehicle Type: " + vehicleType  +
                " | Color: " + color  +
                " | Odometer/Mileage: " + odometer +
                " | Price: " + String.format("$%.2f",price) +
                " ==" + '\n';
    }
}
