/*
 * COMP2232 Object-Oriented Programming Concepts
 * Semester I 2023-2024
 * Assignment 2 
 * 400013038
 * Manages the menu.
 */

import java.util.Scanner;

public class Menu implements Constants {
	private ParkingLotManager lots;
	private Scanner scan;

	public Menu() {
		lots = new ParkingLotManager();
		scan = new Scanner(System.in);
	}

	public void runMenu() {
		String selection;

		// User enters the selection and presses Enter
		displayMenu();
		outputMessage("Enter your selection: ");
		selection = scan.nextLine();
		int value = Integer.parseInt(selection);
		while (value != MENU_ITEM_ID) {
			switch (value) {
				case 1: {
					int id = getInputAsInteger("Enter customer ID: ");
					int total = getInputAsInteger("Enter the number of days: ");
					if ((id > 0) && (total >= 0)) {
						try {
							double cost = lots.allocateSpaceIndividual(id, total);
							outputMessage("Total cost is " + cost + "\n");
						} catch (NoFreeSpacesException e) {
							outputMessage("There are no spaces available.\n");
						} catch (CustomerAlreadyRentingException e) {
							outputMessage("The customer is alreading renting a space.\n");
						}
					}
					break;
				}

				case 2: {
					int id = getInputAsInteger("Enter customer ID: ");
					int total = getInputAsInteger("Enter the number of days: ");
					int floor = getInputAsInteger("Enter the floor: ") - 1;
					if ((id > 0) && (total > 0) && (floor >= 0)) {
						try {
							double cost = lots.allocateSpaceIndividualFloor(id, total, floor);
							outputMessage("Total cost is " + cost + "\n");
						} catch (NoFreeSpacesException e) {
							outputMessage("There are no spaces available.\n");
						} catch (CustomerAlreadyRentingException e) {
							outputMessage("The customer is alreading renting a space.\n");
						}
					}
					break;
				}

				case 3: {
					int id = getInputAsInteger("Enter company ID: ");
					int numberOfCars = getInputAsInteger("Enter number of cars: ");
					;

					if ((numberOfCars > 0) && (id > 0)) {
						try {
							System.out.println(lots.allocateSpaceCompany(numberOfCars, id));
						} catch (NoFreeSpacesException e) {
							System.out.println("There are no free spaces.");
						}
					}

					break;
				}

				case 4: {
					int id = getInputAsInteger("Enter customer ID: ");

					if (id > 0) {
						if (lots.removeIndividual(id) == true) {
							System.out.println("Customer successfully removed.");
						} else {
							System.out.println("Customer was not removed.");
						}
					}
					break;
				}

				case 5: {
					int id = getInputAsInteger("Enter company ID: ");

					if (id > 0) {
						if (lots.removeCompany(id) == true) {
							System.out.println("Company successfully removed.");
						} else {
							System.out.println("Company was not removed.");
						}
					}
					break;
				}

				case 6: {
					int id = getInputAsInteger("Enter company ID: ");

					if (id > 0) {
						System.out.println(lots.totalCompanySpacesAllocated(id));
					}
					break;
				}

				case 7: {
					int id = getInputAsInteger("Enter company ID: ");

					if (id > 0) {
						System.out.println(lots.monthlyRent(id));
					}
					break;
				}

				case 8: {
					int id = getInputAsInteger("Enter customer ID: ");

					if (id > 0) {
						System.out.println(lots.totalOwedIndividual(id));
					}
					break;
				}

				case 9: {
					System.out.println(lots.totalFreeSpaces());
					break;
				}

				case 10: {
					int id = getInputAsInteger("Enter building ID: ");

					if (id > 0) {
						System.out.println(lots.totalFreeSpacesBuilding(id));
					}
					break;
				}

				case 11: {
					int parkingLotID = getInputAsInteger("Enter building ID: ");

					if (parkingLotID > 0) {
						if (lots.areSpacesFree(parkingLotID)) {
							System.out.println("Spaces are free.");
						} else {
							System.out.println("Spaces are not free.");
						}
					}
					break;
				}

				case 12: {
					int parkingLotID = getInputAsInteger("Enter building ID: ");
					int floorNo = getInputAsInteger("Enter floor number: ");
					int spaceID = getInputAsInteger("Enter space ID: ");

					if (spaceID > 0 && floorNo > 0 && parkingLotID > 0) {
						lots.freeSpace(spaceID, floorNo, parkingLotID - 1);
					}
					break;
				}
				
				case 13: {
					int parkingLotID = getInputAsInteger("Enter building ID: ");
					int floorNo = getInputAsInteger("Enter floor number: ");

					if (floorNo > 0 && parkingLotID > 0) {
						 int[] spaces = lots.getListOfFreeSpaces(floorNo, parkingLotID);
						 for (int i = 0; i < spaces.length; i++)
						 {
							System.out.print(spaces[i] + ", ");
						 }
						 System.out.println();
					}
					break;
				}

				case 14: {
					lots.compactSpaces();
					lots.map(0);
					System.out.println();
					lots.map(1);
					break;
				}

				case 15: {
					lots.report();
					break;
				}

				case 16: {
					int id = getInputAsInteger("Enter Parking Lot ID: ") - 1;
					lots.map(id);
					break;
				}

				default: {
					outputMessage("Invalid menu selection.\n");
					break;
				}
			}
			displayMenu();
			outputMessage("Enter your selection: ");
			selection = scan.nextLine();
			value = Integer.parseInt(selection);
		}
	}

	public void closeMenu() {
		scan.close();
	}

	private void displayMenu() {
		System.out.println("\nMenu");
		System.out.println("----\n");
		System.out.println("1. Individual Cost");
		System.out.println("2. Individual Cost (For Specific Floor)");
		System.out.println("3. Company Cost");
		System.out.println("4. Remove Individual");
		System.out.println("5. Remove Company");
		System.out.println("6. Total Company Spaces");
		System.out.println("7. Monthly Company Rent");
		System.out.println("8. Total Owed Individual");
		System.out.println("9. Total Free Spaces");
		System.out.println("10. Total Free Spaces (For Specific Building)");
		System.out.println("11. Check For Free Spaces");
		System.out.println("12. Free Space");
		System.out.println("13. Get List of Free Spaces");
		System.out.println("14. Compact Spaces");
		System.out.println("15. Generate Report");
		System.out.println("16. Generate Space Allocation Map");
		System.out.println("17. Exit\n");
	}

	private void outputMessage(String message) {
		System.out.print(message);
	}

	private int getInputAsInteger(String message) {
		int result = -1;

		outputMessage(message);
		String selection = scan.nextLine();
		result = Integer.parseInt(selection);

		return result;
	}
}