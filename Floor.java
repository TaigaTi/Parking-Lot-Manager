/*
 * COMP2232 Object-Oriented Programming Concepts
 * Semester I 2023-2024
 * Assignment 2 
 * 400013038
 * Defines the structure of a parking lot floor
 */

public class Floor {
    private int floorNo; // identification number for a floor
    private ParkingSpace[] spaces; // holds the spaces of a floor
    private double costPerDay; // Cost to rent a space on a floor per day
    private double costPerMonth; // Cost to rent a space on a floor per month

    Floor(int floorNo, double costPerDay, double costPerMonth) {
        // Ground floor
        if (floorNo == 1) {
            this.floorNo = floorNo;
            this.costPerDay = 20;
            this.costPerMonth = 500;
            this.spaces = new ParkingSpace[15];
        }
        // Top floor
        else if (floorNo == 20) {
            this.floorNo = floorNo;
            this.costPerDay = 5;
            this.costPerMonth = 100;
            this.spaces = new ParkingSpace[12];
        }
        // All other floors
        else {
            this.floorNo = floorNo;
            this.costPerDay = 10;
            this.costPerMonth = 200;
            this.spaces = new ParkingSpace[20];
        }

        // Initialise the spaces on the floor
        for (int i = 0; i < spaces.length; i++) {
            this.spaces[i] = new ParkingSpace(i+1, 'O', null, this);
        }
    }

    // Get the floor number
    int getFloorNo() {
        return floorNo;
    }

    // Get the spaces on a floor
    ParkingSpace[] getSpaces() {
        return spaces;
    }

    // Get a specific space
    ParkingSpace getSpace(int spaceID) {
        return spaces[spaceID];
    }

    // Get the number of free spaces
    int getFreeSpaces() {
        int freeSpaces = 0; 

        for (int i = 0; i < spaces.length; i++) {
            if (spaces[i].getValue() == 'O') {
                freeSpaces++;
            }
        }
        return freeSpaces;
    }

    // Return the cost per day
    public double getCostPerDay() {
        return costPerDay;
    }

    // Set the cost per day
    public void setCostPerDay(double costPerDay) {
        this.costPerDay = costPerDay;
    }

    // Return the cost per month
    public double getCostPerMonth() {
        return costPerMonth;
    }

    // Set the cost per month
    public void setCostPerMonth(double costPerMonth) {
        this.costPerMonth = costPerMonth;
    }

    // Set the spaces
    public void setSpaces(ParkingSpace[] spaces) {
        this.spaces = spaces;
    }

    // Return an available space on a floor
    public ParkingSpace getAvailableSpace(){
        ParkingSpace available = null;

        for (int i = 0; i < spaces.length; i++) {
            if (spaces[i].getValue() == 'O') {
                available = spaces[i];
                break;
            }
        }
        return available;
    }

    // Get a list of the available spaces on a floor
    public ParkingSpace[] getAvailableSpaces(){
        ParkingSpace[] available = new ParkingSpace[getSpaces().length];
        int index = 0;

        for (int i = 0; i < spaces.length; i++) {
                if (spaces[i].getValue() == 'O') {
                        available[index++] = spaces[i];
            }
        }
        return available;
    }
}