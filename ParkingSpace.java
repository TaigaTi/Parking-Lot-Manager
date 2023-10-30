/*
 * COMP2232 Object-Oriented Programming Concepts
 * Semester I 2023-2024
 * Assignment 2 
 * 400013038
 * Defines the structure of a parking space
 */

public class ParkingSpace {
    private int spaceNo; // Identification number for a parking space
    private char value; // Indicates the availability of a space
    private Customer customer; // The customer currently occupying the space
    private Floor floor;

    ParkingSpace(int spaceNo, char value, Customer customer, Floor floor) {
        // Initialise parking space properties
        this.spaceNo = spaceNo;
        this.value = value;
        this.customer = customer;
        this.floor = floor;
    }

    // Return the occupancy of the parking space
    char getValue() {
        return value;
    }

    // Set the occupancy of the parking space
    void setValue(char value) {
        this.value = value;
    }

    // Return the space number
    public int getSpaceNo() {
        return spaceNo;
    }

    // Set the space number
    public void setSpaceNo(int spaceNo) {
        this.spaceNo = spaceNo;
    }

    // Set the customer to a space
    public void setCustomer(Customer customer) {
        this.customer = customer;

        if (customer.getCustomerType() == "individual")
        {
            setValue('X');
        } else
        {
            setValue('C');
        }
    }

    // Set the floor that the space is on
    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    // Return the floor that the space is on
    public Floor getFloor() {
        return this.floor;
    }

    // Remove the customer from the space
    public void removeCustomer() {
        this.customer = null;
        setValue('O');
    }
}