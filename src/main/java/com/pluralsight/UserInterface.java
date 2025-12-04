package com.pluralsight;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class UserInterface {

    // -----------------------------------------------------------

    // Page 7 introducing "dealership : Dealership"

    private Connection connection;
    private VehicleDao vehicleDao;
    private SalesDao salesDao;
    private LeaseDao leaseDao;
    private int command;

    Dealership dealership;
    // -----------------------------------------------------------
    public void userInterface() {
        init();

        System.out.println();
        System.out.println();

        do {
            String display = """
                ====== Dealership Menu ======
                 \
                Choose an option:\s
                 \
                1) Price
                 \
                2) Make/Model
                 \
                3) Year
                 \
                4) Color
                 \
                5) Mileage
                 \
                6) Vehicle Type
                 \
                7) All Vehicles
                 \
                8) Add a Vehicle
                 \
                9) Remove a Vehicle
                 \
                10) Sell a Vehicle
                 \
                11) Lease a Vehicle
                 \
                99) Exit\s
                 \
                ==========================
                """;
            System.out.println(display);


            command = ConsoleHelper.promptForInt("Enter your command".trim());
            System.out.println();

            switch (command) {
                case 1:
                    processGetByPriceRequest();
                    System.out.println("\n======================\n");
                    break;

                case 2:
                    processGetByMakeModelRequest();
                    System.out.println("\n======================\n");
                    break;

                case 3:
                    processGetByYearRequest();
                    System.out.println("\n======================\n");
                    break;

                case 4:
                    processGetByColorRequest();
                    System.out.println("\n======================\n");
                    break;

                case 5:
                    processGetByMileageRequest();
                    System.out.println("\n======================\n");
                    break;

                case 6:
                    processGetByVehicleTypeRequest();
                    System.out.println("\n======================\n");
                    break;

                case 7:
                    processGetAllVehiclesByRequest();
                    System.out.println("\n======================\n");
                    break;

                case 8:
                    processAddVehicleRequest();
                    System.out.println("\n======================\n");
                    break;

                case 9:
                    processRemoveVehicleRequest();
                    System.out.println("\n======================\n");
                    break;

                case 10:
                    processSellAVehicle();
                    System.out.println("\n======================\n");
                    break;

                case 11:
                    processLeaseAVehicle();
                    System.out.println("\n======================\n");
                    break;

                case 99:
                    closeConnection();
                    return;

                default:
                    System.out.println("Invalid input. Please enter a valid option.");
                    System.out.println("======================\n");
            }
        } while (true);
    }

    private void init() {
        try {
            connection = DatabaseManager.getConnection();

            vehicleDao = new VehicleDao(connection);
            salesDao = new SalesDao(connection);
            leaseDao = new LeaseDao(connection);

            System.out.println("Connected to database successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void processGetByPriceRequest() {
        float minPrice = ConsoleHelper.promptForFloat("Please enter the minimum price range you're searching for");
        float maxPrice = ConsoleHelper.promptForFloat("Please enter the maximum price range you're searching for");

        List<Vehicle> vehicles = dealership.getVehicleByPrice(minPrice, maxPrice);
        displayVehicles(vehicles);
    }

    public void processGetByMakeModelRequest() {
        String makeRequest = ConsoleHelper.promptForString("Please enter the make");
        String modelRequest = ConsoleHelper.promptForString("Please enter the model");

        List<Vehicle> vehicles = dealership.getVehicleByMakeModel(makeRequest, modelRequest);
        displayVehicles(vehicles);
    }

    public void processGetByYearRequest() {
        int minimumYear = ConsoleHelper.promptForInt("Please enter the minimum year range you're searching for");
        int maximumYear = ConsoleHelper.promptForInt("Please enter the maximum year range you're searching for");

        List<Vehicle> vehicles = dealership.getVehicleByYear(minimumYear, maximumYear);
        displayVehicles(vehicles);
    }

    public void processGetByColorRequest() {
        String colorRequest = ConsoleHelper.promptForString("Please enter vehicle's color");
        List<Vehicle> vehicles = dealership.getVehicleByColor(colorRequest);
        displayVehicles(vehicles);
    }

    public void processGetByMileageRequest() {
        int minimumMileage = ConsoleHelper.promptForInt("Please enter the minimum mileage range you're searching for");
        int maximumMileage = ConsoleHelper.promptForInt("Please enter the maximum mileage range you're searching for");

        List<Vehicle> vehicles = dealership.getVehicleByMileage(minimumMileage, maximumMileage);
        displayVehicles(vehicles);
    }

    public void processGetByVehicleTypeRequest() {
        String vehicleTypeRequest = ConsoleHelper.promptForString("Please enter the vehicle type");
        List<Vehicle> vehicles = dealership.getVehicleByType(vehicleTypeRequest);
        displayVehicles(vehicles);
    }

    public void processGetAllVehiclesByRequest() {
        List<Vehicle> vehicles = dealership.getInventory();
        displayVehicles(vehicles);
    }

    public void processAddVehicleRequest() {
        // UPDATE SO THAT IF an integer is inputted into a getString, it tells you to put in a STRING.
        String getVin = ConsoleHelper.promptForString("Enter vehicle's VIN");
        int getYear = ConsoleHelper.promptForInt("Enter vehicle's year");
        String getMake = ConsoleHelper.promptForString("Enter vehicle's make");
        String getModel = ConsoleHelper.promptForString("Enter vehicle's model");
        String getType = ConsoleHelper.promptForString("Enter vehicle's type");
        String getColor = ConsoleHelper.promptForString("Enter vehicle's color");
        int getOdo = ConsoleHelper.promptForInt("Enter vehicle's odometer");
        double getPrice = ConsoleHelper.promptForDouble("Enter vehicle's price");

        Vehicle newVehicle = new Vehicle(getVin,getYear,getMake,getModel,getType,getColor,getOdo,getPrice);

        dealership.addVehicle(newVehicle);

        DealershipFileManager fm = new DealershipFileManager();
        fm.saveDealership(dealership);

    }

    public void processRemoveVehicleRequest() {
        // Show all vehicles so user can see VINs
        System.out.println("Current inventory:");
        displayVehicles(dealership.getAllVehicles());

        // Prompt for VIN
        String vinToRemove = ConsoleHelper.promptForString("Enter the VIN of the vehicle to remove");

        // Try to find the vehicle
        Vehicle vehicleToRemove = null;
        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (Objects.equals(vehicle.getVin(), vinToRemove)) {
                vehicleToRemove = vehicle;
                break;
            }
        }

        // Remove if found
        if (vehicleToRemove != null) {
            dealership.removeVehicle(vehicleToRemove);
            System.out.println("Vehicle removed successfully!");

            // Save the updated dealership
            DealershipFileManager fm = new DealershipFileManager();
            fm.saveDealership(dealership);
        } else {
            System.out.println("Vehicle with VIN " + vinToRemove + " not found.");
        }
    }

    private void processLeaseAVehicle() {
        // Show all vehicles so user can see VINs
        System.out.println("Current inventory:");
        displayVehicles(dealership.getAllVehicles());

        // Prompt for VIN
        String vinToSell = ConsoleHelper.promptForString("Enter the VIN of the vehicle to sell");

        // Try to find the vehicle
        Vehicle vehicleToSell = null;
        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (Objects.equals(vehicle.getVin(), vinToSell)) {
                vehicleToSell = vehicle;
                break;
            }
        }

        String contractDate = ConsoleHelper.promptForString("Enter the date");
        String customerName = ConsoleHelper.promptForString("Enter customer's name");
        String customerEmail = ConsoleHelper.promptForString("Enter customer's email");


        LeaseContract leaseContract = new LeaseContract(contractDate, customerName, customerEmail, vehicleToSell);
        ContractDataManager cm = new ContractDataManager();
        cm.saveContract(leaseContract);

        dealership.removeVehicle(vehicleToSell);
        DealershipFileManager file = new DealershipFileManager();
        file.saveDealership(dealership);
    }

    private void processSellAVehicle() {
        // Show all vehicles so user can see VINs
        System.out.println("Current inventory:");
        displayVehicles(dealership.getAllVehicles());

        // Prompt for VIN
        String vinToSell = ConsoleHelper.promptForString("Enter the VIN of the vehicle to sell");

        // Try to find the vehicle
        Vehicle vehicleToSell = null;
        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (Objects.equals(vehicle.getVin(), vinToSell)) {
                vehicleToSell = vehicle;
                break;
            }
        }

        String contractDate = ConsoleHelper.promptForString("Enter the date");
        String customerName = ConsoleHelper.promptForString("Enter customer's name");
        String customerEmail = ConsoleHelper.promptForString("Enter customer's email");
        String financeOption = ConsoleHelper.promptForString("Would you like to finance your vehicle? (Y/N)");

        boolean financeOutput = financeOption.equalsIgnoreCase("Y") ? true : false;

        SalesContract salesContract = new SalesContract(contractDate, customerName, customerEmail, vehicleToSell, financeOutput);
        ContractDataManager cm = new ContractDataManager();
        cm.saveContract(salesContract);

        dealership.removeVehicle(vehicleToSell);

        DealershipFileManager file = new DealershipFileManager();
        file.saveDealership(dealership);

    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }
}