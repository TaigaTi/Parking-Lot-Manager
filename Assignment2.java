/*
 * COMP2232 Object-Oriented Programming Concepts
 * Semester I 2023-2024
 * Assignment 2 
 */

import java.util.Arrays;

public class Assignment2 {
	public static void main(String args[]) {
		Menu menu = new Menu();
		menu.runMenu();
		menu.closeMenu();
/* 
		// Test Stuff
		ParkingLotManager p1 = new ParkingLotManager();

		// Give a full report for the parking lot manager
		p1.report();

		// Print a map of the free spaces
		p1.map(1);

		// Allocate space to an individual
		try {
			System.out.println(p1.allocateSpaceIndividual(8, 5));
		} catch (NoFreeSpacesException e) {
			System.out.println("There are no free spaces.");
		} catch (CustomerAlreadyRentingException e) {
			System.out.println("Customer already renting.");
		}

		// Allocate space to an individual on a given floor
		try {
			System.out.println(p1.allocateSpaceIndividualFloor(9, 5, 2));
		} catch (NoFreeSpacesException e) {
			System.out.println("There are no free spaces.");
		} catch (CustomerAlreadyRentingException e) {
			System.out.println("Customer already renting.");
		}

		// Allocate space to a company
		try {
			System.out.println(p1.allocateSpaceCompany(2, 3));
		} catch (NoFreeSpacesException e) {
			System.out.println("There are no free spaces.");
		}

		System.out.println(p1.totalFreeSpaces());
		System.out.println(p1.totalFreeSpacesBuilding(2));
		System.out.println(p1.areSpacesFree(2));		
		System.out.println(Arrays.toString(p1.getListOfFreeSpaces(1, 2)));	*/
	} 
}