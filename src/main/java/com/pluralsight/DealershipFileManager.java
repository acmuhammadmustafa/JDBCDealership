package com.pluralsight;

import java.io.*;
public class DealershipFileManager {

    // File Reader

    public Dealership getDealership() {
        Dealership dealership = null;  // Recognizes dealership as null.

        try {
            // Step 1: Opens the fileReader and bufferedReader.
            FileReader fileReader = new FileReader("inventory.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String lineFromString;

            // Step 2: Reads the first line.
            if ((lineFromString = bufferedReader.readLine()) != null) {
                String[] parts = lineFromString.split("\\|");
                String name = parts[0];
                String address = parts[1];
                String phone = parts[2];

            // Step 3: Uses the details above to create a Dealership object.
                dealership = new Dealership(name, address, phone);
            }

// ----------------------------------------------------------------------------------------------------
            // Step 4: Read the remaining lines for vehicles
            while ((lineFromString = bufferedReader.readLine()) != null) {

                String[] part = lineFromString.split("\\|");

                int vin = Integer.parseInt(part[0]);
                int year = Integer.parseInt(part[1]);
                String make = part[2];
                String model = part[3];
                String vehicleType = part[4];
                String color = part[5];
                int odometer = Integer.parseInt(part[6]);
                double price = Double.parseDouble(part[7]);

            // Step 5: Create a Vehicle object using the details from the current line
                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

            // Step 6: Add the Vehicle to the Dealership's inventory
                assert dealership != null;
                dealership.getInventory().add(vehicle);
//                System.out.println(vehicle);
            }

            // Step 7: Close the readers
            fileReader.close();
            bufferedReader.close();

        } catch (IOException e) {
            System.out.println("Something went wrong..");
        }

        return dealership;
    }

// ----------------------------------------------------------------------------------------------------

    // File Writer:
    public void saveDealership(Dealership dealership){

        try  {
            //Create a FileWriter and BufferedWriter:
            FileWriter fw = new FileWriter("inventory.csv");
            BufferedWriter bw = new BufferedWriter(fw);

            // Write dealership info
            bw.write(dealership.getName() + "|" +
                    dealership.getAddress() + "|" +
                    dealership.getPhone());
            bw.newLine();

            // Write all vehicles
            for (Vehicle vehicle : dealership.getInventory()){
                bw.write(vehicle.getVin() + "|" +
                        vehicle.getYear() + "|" +
                        vehicle.getMake() + "|" +
                        vehicle.getModel() + "|" +
                        vehicle.getVehicleType() + "|" +
                        vehicle.getColor() + "|" +
                        vehicle.getOdometer() + "|" +
                        vehicle.getPrice());
                bw.newLine();
            }

            //Close the BufferedWriter
            bw.close();
            System.out.println("Dealership saved successfully!");

        } catch (IOException e) {
            System.out.println("Something went wrong..");
        }
    }

    // File Writer:
//    private static void addVehicleToDealership(Vehicle inventory){

//        // ALL OF THIS WILL NEED UPDATING:
//
//
//        try  {
//            //Create a FileWriter and BufferedWriter:
//            FileWriter fw = new FileWriter("inventory.csv",true);
//            BufferedWriter bw = new BufferedWriter(fw);
//            //Create a new line and formatting the way the information will be written into transaction.csv.
//            bw.newLine();
//            bw.write(inventory.getVin() + "|" + inventory.getYear() + "|" + inventory.getMake() + "|" + inventory.getModel() + "|" + inventory.getVehicleType() + "|" + inventory.getColor()  + "|" + inventory.getOdometer()  + "|" + inventory.getPrice());
//            //Close the BufferedWriter. (FileWriter being closed is not necessary as BufferedWriter wraps around it.)
//            bw.close();
//        } catch (IOException e) {
//            System.out.println("Something is wrong...");
//        }
//    }



}

