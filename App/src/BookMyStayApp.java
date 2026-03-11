/**
 * UseCase6RoomAllocationService.java
 * UC6: Reservation Confirmation & Room Allocation
 * Version: 6.0
 */

import java.util.*;

/* ---------------- RESERVATION ---------------- */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/* ---------------- INVENTORY ---------------- */

class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 3);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        int count = inventory.get(roomType);
        inventory.put(roomType, count - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/* ---------------- BOOKING QUEUE ---------------- */

class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

/* ---------------- ROOM ALLOCATION SERVICE ---------------- */

class RoomAllocationService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();

    public void processBooking(BookingRequestQueue queue, RoomInventory inventory) {

        while (!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();
            String roomType = r.getRoomType();

            System.out.println("\nProcessing booking for " + r.getGuestName());

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRoomIds.add(roomId);

                roomAllocations
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                inventory.decrementRoom(roomType);

                System.out.println("Reservation Confirmed!");
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);

            } else {
                System.out.println("Sorry! No rooms available for " + roomType);
            }
        }
    }

    private String generateRoomId(String roomType) {
        String id;
        do {
            id = roomType.substring(0,2).toUpperCase() + new Random().nextInt(1000);
        } while (allocatedRoomIds.contains(id));
        return id;
    }
}

/* ---------------- MAIN APPLICATION ---------------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Room Allocation System v6.0");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();

        // Booking requests
        queue.addRequest(new Reservation("Krishna", "Single Room"));
        queue.addRequest(new Reservation("Rahul", "Double Room"));
        queue.addRequest(new Reservation("Ananya", "Suite Room"));
        queue.addRequest(new Reservation("Amit", "Suite Room"));

        RoomAllocationService service = new RoomAllocationService();

        service.processBooking(queue, inventory);

        inventory.displayInventory();
    }
}