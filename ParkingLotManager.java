
/*
 * COMP2232 Object-Oriented Programming Concepts
 * Semester I 2023-2024
 * Assignment 2 
 * 400013038
 * Manages the two parking lots.
 */
import java.util.ArrayList;

public class ParkingLotManager {
	private ParkingLot parkingLots[]; // Parking lots
	private ArrayList<Customer> customers; // List of customers

	ParkingLotManager() {
		parkingLots = new ParkingLot[2];

		this.parkingLots[0] = new ParkingLot(1);
		this.parkingLots[1] = new ParkingLot(2);
		this.customers = new ArrayList<Customer>();
	}

	// Output the current status of the parking lots
	public void report() {
		for (ParkingLot parkingLot : parkingLots) {
			System.out.println("Parking Lot: " + parkingLot.getParkingLotID());

			// Print the floor numbers and the number of free spaces for each floor
			Floor[] floors = parkingLot.getFloors();
			for (int i = 0; i < parkingLot.getFloors().length; i++) {
				System.out.println("Floor " + floors[i].getFloorNo() + ": " + floors[i].getFreeSpaces() + " Free");
			}

			int individuals = 0; // Counter for the number of individuals

			// Check each customer in the array
			for (Customer customer : customers) {
				// Output spaces rented by each company
				if (customer.getCustomerType() == "company") {
					System.out.println(
							"Company " + customer.getCustomerID() + ": " + customer.getOccupiedSpaces() + "Rented");
				}
				// Count the number of individuals
				else {
					individuals++;
				}
			}
			// Output the number of individuals
			System.out.println("Individual Customers: " + individuals);
			System.out.println(); // Extra line for readability
		}
	}

	// Output a space allocation map for a given parking lot
	public void map(int parkingLotId) {
		Floor[] floors = parkingLots[parkingLotId].getFloors();

		for (int i = 0; i < parkingLots[parkingLotId].getFloors().length; i++) {
			ParkingSpace spaces[] = floors[i].getSpaces();

			// Output floor number
			if (i < 9) {
				System.out.print(floors[i].getFloorNo() + ":  ");
			} else {
				System.out.print(floors[i].getFloorNo() + ": ");
			}

			// Output space allocations
			for (int j = 0; j < spaces.length; j++) {
				System.out.print(spaces[j].getValue() + " | ");
			}
			System.out.println();
		}
	}

	// Allocate a space to an indivudual customer
	public double allocateSpaceIndividual(int customerID, int numberOfDays)
			throws NoFreeSpacesException, CustomerAlreadyRentingException {
		Floor available = null;
		Customer customer = getCustomerById(customerID);

		// Check to see if the customer is already renting a space
		if (customer.getOccupiedSpaces() != 0) {
			throw new CustomerAlreadyRentingException();
		}

		// Find the first available floor in a parking lot
		for (ParkingLot parkingLot : parkingLots) {
			for (Floor floor : parkingLot.getFloors()) {
				if (floor.getFreeSpaces() != 0) {
					available = floor;
					break;
				}

			}
			if (available != null) {
				break;
			}
		}

		// If there are no available spaces, throw the No Free Spaces Exception
		if (available == null) {
			throw new NoFreeSpacesException();
		}

		// Assign the space to the customer
		ParkingSpace space = available.getAvailableSpace();

		customer.addCustomerSpace(space);
		space.setCustomer(customer);
		customer.setNumberOfDays(numberOfDays);

		// Otherwise return the cost
		double cost = available.getCostPerDay() * numberOfDays;

		return cost;
	}

	// Allocate a space on the desired floor to an individual
	public double allocateSpaceIndividualFloor(int customerID, int numberOfDays, int desiredFloorNo)
			throws NoFreeSpacesException, CustomerAlreadyRentingException {
		Floor available = null;
		Customer customer = getCustomerById(customerID);

		// Check to see if the customer is already renting a space
		if (customer.getOccupiedSpaces() != 0) {
			throw new CustomerAlreadyRentingException();
		}

		// For each parking lot, check to see if there are available spaces on the
		// desired floor
		for (ParkingLot parkingLot : parkingLots) {
			if (parkingLot.getFloor(desiredFloorNo) != null) {
				available = parkingLot.getFloor(desiredFloorNo);
				break;
			}
			if (available != null) {
				break;
			}
		}

		// Assign the space to the customer
		ParkingSpace space = available.getAvailableSpace();

		customer.addCustomerSpace(space);
		space.setCustomer(customer);

		// Return the cost of the available spot
		double cost = available.getCostPerDay() * numberOfDays;

		return cost;
	}

	// Allocate spaces on the desired floor to a company
	public double allocateSpaceCompany(int numberOfCars, int companyNo) throws NoFreeSpacesException {
		int assigned = 0; // Number of cars assigned for the company
		int numberOfFloors = (numberOfCars / 2) + (numberOfCars % 2); // Number of floors needed
		Customer company = getCompanyById(companyNo); // Company to rent spaces to
		double cost = 0.0; // Cost for rent per month

		// Check if the company has reached their car limit
		if (company.getOccupiedSpaces() + numberOfCars > 10) {
			throw new NoFreeSpacesException();
		}

		int index = 0;
		int occupied = 0; // Number of spaces occupied by the company on a floor

		for (ParkingLot parkingLot : parkingLots) {
			for (Floor floor : parkingLot.getFloors()) {
				occupied = 0;

				// Count the number of spaces occupied by the company on the floor
				for (ParkingSpace space : company.getOccupiedParkingSpaces()) {
					if (space.getFloor() == floor) {
						occupied++;
					}
				}

				// Check if the floor has spaces available
				if (floor.getFreeSpaces() >= 2 && occupied < 2) {
					// Get a list of available spaces on the available floor
					ParkingSpace[] freeSpaces = floor.getAvailableSpaces();

					// Allocate the space
					for (int i = 0; i < (2 - occupied); i++) {
						if (assigned < numberOfCars) {
							company.addCustomerSpace(freeSpaces[i]); // Assign the spaces to the company
							freeSpaces[i].setCustomer(company); // Assign the customer to the space
							cost += floor.getCostPerMonth(); // Get the cost for the space

							assigned++;
						}
					}

					index++;

					if (index >= numberOfFloors - 1) {
						break;
					}
				}
			}
		}

		// If not enough floors have available spaces, throw the No Free Spaces Exception
		if (index < numberOfFloors - 1) {
			throw new NoFreeSpacesException();
		}
		return cost;
	}

	// Remove an individual from a space
	public boolean removeIndividual(int customerID) {
		Customer customer = getCustomerById(customerID);

		// Check if the customer exists and is renting a space
		if (customer != null && customer.getOccupiedSpaces() != 0) {
			for (ParkingSpace space : customer.getCustomerParkingSpaces()) {
				space.removeCustomer(); // Remove the customer from the space
				customer.setCustomerParkingSpaces(null); // Remove the space from the customer
			}
			return true;
		} else {
			return false;
		}
	}

	// Remove a company from the spaces occupied
	public boolean removeCompany(int companyNo) {
		Customer company = getCompanyById(companyNo);

		// Check if the customer exists and is renting a space
		if (company != null && company.getOccupiedSpaces() != 0) {
			for (ParkingSpace space : company.getOccupiedParkingSpaces()) {
				space.removeCustomer(); // Remove the customer from the space
				company.setCustomerParkingSpaces(null); // Remove the space from the company
			}
			return true;
		} else {
			return false;
		}
	}

	// Check how many spaces are allocated to a company
	public int totalCompanySpacesAllocated(int companyNo) {
		Customer company = getCompanyById(companyNo);

		return company.getOccupiedSpaces();
	}

	// Get the monthly rent owed by a company
	public double monthlyRent(int companyNo) {
		Customer company = getCompanyById(companyNo);
		double rent = 0.0;
		if (company.getOccupiedSpaces() != 0) {
			for (ParkingSpace space : company.getOccupiedParkingSpaces()) {
				rent += space.getFloor().getCostPerMonth();
			}
		}
		return rent;
	}

	// Find the total amount owed by an individual
	public double totalOwedIndividual(int customerID) {
		Customer customer = getCustomerById(customerID);
		double owed = 0.0;

		if (customer.getOccupiedSpaces() != 0) {
			for (ParkingSpace space : customer.getOccupiedParkingSpaces()) {
				owed = space.getFloor().getCostPerDay() * customer.getNumberOfDays();
				System.out.println(customer.getNumberOfDays());
				System.out.println(space.getFloor().getCostPerDay());
			}
		} else {
			owed = 0.0;
		}
		return owed;
	}

	// Find the total number of free spaces
	public int totalFreeSpaces() {
		int spaces = 0;

		for (ParkingLot parkingLot : parkingLots) {
			for (Floor floor : parkingLot.getFloors()) {
				spaces += floor.getFreeSpaces();
			}
		}

		return spaces;
	}

	// Find the total number of free spaces in each building
	public int totalFreeSpacesBuilding(int parkingLotID) {
		int spaces = 0;

		for (Floor floor : parkingLots[parkingLotID - 1].getFloors()) {
			spaces += floor.getFreeSpaces();
		}

		return spaces;
	}

	// Check to see if there are any available spaces in a given parking lot
	public boolean areSpacesFree(int parkingLotID) {
		for (Floor floor : parkingLots[parkingLotID - 1].getFloors()) {
			if (floor.getFreeSpaces() > 0) {
				return true;
			}
		}
		return false;
	}

	// Check if a given space on a specified floor, in a specified building is free
	public void freeSpace(int spaceNo, int floorNo, int parkingLotID) {
		Floor floor[] = parkingLots[parkingLotID].getFloors();

		if (floor[floorNo].getSpace(spaceNo).getValue() != 'O' && floor[floorNo].getSpace(spaceNo).getValue() != 'D') {
			floor[floorNo].getSpace(spaceNo).setValue('O');
		}
	}

	// Return a list of the available spaces on a specified floor in a given parking
	// lot
	public int[] getListOfFreeSpaces(int floorNo, int parkingLotID) {
		int result[] = { 0 };
		ArrayList<Integer> resultList = new ArrayList<>();

		Floor floor[] = parkingLots[parkingLotID - 1].getFloors();

		// Store the space number of the available spaces in a list
		for (ParkingSpace space : floor[floorNo].getSpaces()) {
			if (space.getCustomer() == null) {
				resultList.add(space.getSpaceNo());
			}
		}

		result = new int[resultList.size()]; // Create an integer array of the same size
		for (int i = 0; i < resultList.size(); i++) {
			result[i] = resultList.get(i); // Copy elements from the ArrayList to the array
		}

		return result;
	}

	// Optimize the spaces on each floor
	public void compactSpaces() {
		for (ParkingLot parkingLot : parkingLots) {
			for (Floor floor : parkingLot.getFloors()) {
				int occupiedIndex = 0;
				int emptyIndex = 0;

				// Store the empty and occupied spaces in seperate arrays
				ParkingSpace[] occupied = new ParkingSpace[floor.getSpaces().length - floor.getFreeSpaces()];
				ParkingSpace[] empty = new ParkingSpace[floor.getFreeSpaces()];

				for (ParkingSpace parkingSpace : floor.getSpaces()) {
					if (parkingSpace.getCustomer() == null && parkingSpace.getValue() != 'D') {
						empty[emptyIndex++] = parkingSpace;
					} else {
						occupied[occupiedIndex++] = parkingSpace;
					}
				}

				// Create an array to store the final result
				ParkingSpace[] arranged = new ParkingSpace[emptyIndex + occupiedIndex];

				int index = 0;

				// Store occupied spaces in the final array
				for (ParkingSpace occ : occupied) {
					arranged[index++] = occ;
				}

				// Store empty spaces in the final array
				for (ParkingSpace emp : empty) {
					arranged[index++] = emp;
				}

				// Set the spaces on the floor to the final array
				floor.setSpaces(arranged);

			}
		}
	}

	// Helper function to return a customer with the specified ID
	public Customer getCustomerById(int customerID) {
		Customer customer = null;

		// Check each customer and return one with the matchcing ID
		for (Customer c : customers) {
			if (c.getCustomerType() == "individual" && c.getCustomerID() == customerID) {
				customer = c;
			}
		}

		// If the customer does not exist, create a new customer with the given ID
		if (customer == null) {
			customer = new Customer(customerID, "individual", 0, 0);
			customers.add(customer);
		}

		return customer;
	}

	// Helper function to return a company with the specified ID
	public Customer getCompanyById(int customerID) {
		Customer customer = null;

		// Check each customer and return one with the matchcing ID
		for (Customer c : customers) {
			if (c.getCustomerType() == "company" && c.getCustomerID() == customerID) {
				customer = c;
			}
		}

		// If the customer does not exist, create a new customer with the given ID
		if (customer == null) {
			customer = new Customer(customerID, "company", 0, 0);
		}

		customers.add(customer);
		return customer;
	}
}