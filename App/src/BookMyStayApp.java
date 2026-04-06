import java.io.*;
import java.util.*;

// Snapshot of system state
class SystemState implements Serializable {
    Map<String, Integer> inventory;
    Map<String, String> bookings;

    public SystemState(Map<String,Integer> inventory,
                       Map<String,String> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {
    private static final String FILE_NAME = "hotel_state.ser";

    // Save state to file
    public static void save(SystemState state) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (Exception e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state from file
    public static SystemState load() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state restored from file.");
            return (SystemState) in.readObject();

        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return null;
        }
    }
}

// MAIN PROGRAM
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Default state
        Map<String,Integer> inventory = new HashMap<>();
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);

        Map<String,String> bookings = new HashMap<>();

        // 🔄 LOAD PREVIOUS STATE (Recovery)
        SystemState loaded = PersistenceService.load();
        if (loaded != null) {
            inventory = loaded.inventory;
            bookings = loaded.bookings;
        }

        int choice;
        do {
            System.out.println("\nInventory: " + inventory);
            System.out.println("Bookings: " + bookings);

            System.out.println("\n1. Book Room");
            System.out.println("2. Save & Exit");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Reservation ID: ");
                String id = sc.nextLine();

                System.out.print("Room Type (Standard/Deluxe/Suite): ");
                String type = sc.nextLine();

                if (inventory.get(type) > 0) {
                    inventory.put(type, inventory.get(type) - 1);
                    bookings.put(id, type);
                    System.out.println("Booking confirmed!");
                } else {
                    System.out.println("No rooms available!");
                }
            }

        } while (choice != 2);

        // 💾 SAVE STATE BEFORE EXIT
        SystemState state = new SystemState(inventory, bookings);
        PersistenceService.save(state);

        System.out.println("Application closed safely.");
    }
}