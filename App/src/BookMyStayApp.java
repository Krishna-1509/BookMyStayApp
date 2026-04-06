import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Maintains room inventory
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Standard", 2);
        rooms.put("Deluxe", 2);
        rooms.put("Suite", 1);
    }

    public boolean isValidRoomType(String type) {
        return rooms.containsKey(type);
    }

    public int getAvailableRooms(String type) {
        return rooms.get(type);
    }

    public void reserveRoom(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " Rooms Available: " + rooms.get(type));
        }
    }
}

// Validation Service
class BookingValidator {

    public static void validate(String roomType, int nights,
                                RoomInventory inventory)
            throws InvalidBookingException {

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected!");
        }

        if (nights <= 0) {
            throw new InvalidBookingException("Number of nights must be greater than 0!");
        }

        if (inventory.getAvailableRooms(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for selected type!");
        }
    }
}

// MAIN PROGRAM
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();

        System.out.println("===== ERROR HANDLING & VALIDATION =====");

        int choice;
        do {
            inventory.displayInventory();

            System.out.println("\n1. Book Room");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                try {
                    System.out.print("Enter Room Type (Standard/Deluxe/Suite): ");
                    String roomType = sc.nextLine();

                    System.out.print("Enter Number of Nights: ");
                    int nights = sc.nextInt();

                    // 🔴 VALIDATION STEP (Fail-Fast)
                    BookingValidator.validate(roomType, nights, inventory);

                    // If validation passes → proceed booking
                    inventory.reserveRoom(roomType);
                    System.out.println("Booking Confirmed Successfully!");

                } catch (InvalidBookingException e) {
                    // Graceful failure
                    System.out.println("Booking Failed: " + e.getMessage());
                }
            }

        } while (choice != 0);

        System.out.println("System remained stable after errors ✅");
    }
}