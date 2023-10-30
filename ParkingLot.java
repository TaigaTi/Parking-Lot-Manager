/*
 * COMP2232 Object-Oriented Programming Concepts
 * Semester I 2023-2024
 * Assignment 2 
 * 400013038
 * Defines the structure of a parking lot
 */

public class ParkingLot {
    private int parkingLotID; // Identification number for a parking lot
    private Floor floors[]; // Floors in a parking lot

    ParkingLot(int parkingLotID) {
        // Initialise parking lot properties
        this.parkingLotID = parkingLotID;
        floors = new Floor[20]; 

        for (int i = 0; i < floors.length; i++) {
            floors[i] = new Floor(i+1, 0, 0);
        }
    }

    // Get the parking lot ID
    public int getParkingLotID() {
        return parkingLotID;
    }

    // Get the floors in a parking lot
    public Floor[] getFloors() {
        return floors;
    }

    // Get a specific floor
    public Floor getFloor(int floorNo) {
        return floors[floorNo];
    }
}
