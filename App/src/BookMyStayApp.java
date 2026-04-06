import java.util.*;

// Reservation model
class Reservation {
    String reservationId;
    String roomType;
    String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

// Room inventory
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String type) {
        return inventory.get(type) > 0;
    }

    public void bookRoom(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void releaseRoom(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }

    public void showInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }
    }
}

// Booking Manager
class BookingManager {
    Map<String, Reservation> bookings = new HashMap<>();
    int roomCounter = 101;

    public Reservation confirmBooking(String reservationId, String roomType, RoomInventory inv) {
        if (!inv.isAvailable(roomType)) {
            System.out.println("No rooms available!");
            return null;
        }

        String roomId = roomType.substring(0, 1) + roomCounter++;
        inv.bookRoom(roomType);

        Reservation r = new Reservation(reservationId, roomType, roomId);
        bookings.put(reservationId, r);

        System.out.println("Booking Confirmed! Room ID: " + roomId);
        return r;
    }
}

// Cancellation Service with rollback stack
class CancellationService {
    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              Map<String, Reservation> bookings,
                              RoomInventory inv) {

        if (!bookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found!");
            return;
        }

        Reservation r = bookings.get(reservationId);

        // Step 1: push room ID to rollback stack
        rollbackStack.push(r.roomId);

        // Step 2: restore inventory
        inv.releaseRoom(r.roomType);

        // Step 3: remove booking
        bookings.remove(reservationId);

        System.out.println("Booking Cancelled Successfully!");
        System.out.println("Room " + rollbackStack.peek() + " returned to inventory.");
    }
}

// MAIN PROGRAM
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();
        BookingManager manager = new BookingManager();
        CancellationService cancelService = new CancellationService();

        int choice;
        do {
            inventory.showInventory();

            System.out.println("\n1. Book Room");
            System.out.println("2. Cancel Booking");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Room Type (Standard/Deluxe/Suite): ");
                    String type = sc.nextLine();

                    manager.confirmBooking(id, type, inventory);
                    break;

                case 2:
                    System.out.print("Enter Reservation ID to cancel: ");
                    String cancelId = sc.nextLine();

                    cancelService.cancelBooking(cancelId, manager.bookings, inventory);
                    break;
            }

        } while (choice != 0);

        System.out.println("System closed safely.");
    }
}