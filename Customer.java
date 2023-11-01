/*
 * COMP2232 Object-Oriented Programming Concepts
 * Semester I 2023-2024
 * Assignment 2 
 * 400013038
 * Describes the customer
 */

public class Customer {
    private int customerID; // Identification number for a customer
    private String customerType; // Type of customer (company or individual)
    private int numberOfCars; // Number of cars for a customer
    private int numberOfDays; // Number of days renting
    private ParkingSpace[] customerParkingSpaces; // Parking spaces held by a customer

    Customer(int customerID, String customerType, int numberOfCars, int numberOfDays) {
        // Initialise customer fields
        this.customerID = customerID;
        this.customerType = customerType;
        this.numberOfCars = numberOfCars;
        this.numberOfDays = numberOfDays;

        // Check customer type to assign number of parking spaces
        if (customerType == "individual") {
            customerParkingSpaces = new ParkingSpace[1];
        } else  {
            customerParkingSpaces = new ParkingSpace[10];
        }
    }

    // Return the customer ID
    int getCustomerID() {
        return customerID;
    }

    // Set the customer ID
    void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    // Return the customer type
    String getCustomerType() {
        return customerType;
    }

    // Set the customer type
    void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    // Return the parking spaces assigned to a customer
    ParkingSpace[] getCustomerParkingSpaces() {
        return customerParkingSpaces;
    }

    // Return the occupied parking spaces assigned to a customer
    ParkingSpace[] getOccupiedParkingSpaces() {
        ParkingSpace[] occupied = new ParkingSpace[getOccupiedSpaces()];
        int index = 0;

        for (ParkingSpace space : customerParkingSpaces){
            if (space != null) {
                occupied[index++] = space;
            }
        }
        return occupied;
    }

    // Set the customer parking spaces
    void setCustomerParkingSpaces(ParkingSpace[] customerParkingSpaces) {
        this.customerParkingSpaces = customerParkingSpaces;
    }

    // Assign a space to a customer
    void addCustomerSpace(ParkingSpace space) {
        for (int i = 0; i < this.getCustomerParkingSpaces().length; i++) {
            if (customerParkingSpaces[i] == null) {
                customerParkingSpaces[i] = space;
                break;
            }
        }
    }

    // Return the number of days a customer is renting
    int getNumberOfDays() {
        return numberOfDays;
    }

    void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    // Return the number of cars
     int getNumberOfCars() {
        return numberOfCars;
    }

    void setNumberOfCars(int numberOfCars) {
        this.numberOfCars = numberOfCars;
    }

    // Return the number of occupied spaces
    int getOccupiedSpaces() {
        int count = 0;

        for (ParkingSpace space: customerParkingSpaces) {
            if (space != null) {
                count++;
            }
        }
        return count;
    }
}