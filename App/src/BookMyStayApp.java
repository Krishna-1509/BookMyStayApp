/**
 * UseCase4RoomSearch.java
 * UC4: Room Search & Availability Check
 * Version: 4.0
 */

import java.util.HashMap;
import java.util.Map;

/* ---------------- ROOM DOMAIN MODEL ---------------- */

abstract class Room {

    protected String roomType;
    protected double price;

    public Room(String roomType, double price) {
        this.roomType = roomType;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public abstract void displayDetails();
}

/* Concrete Room Types */

class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 2000);
    }

    public void displayDetails() {
        System.out.println("Single Room | Price: ₹" + price + " | 1 Bed");
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 3500);
    }

    public void displayDetails() {
        System.out.println("Double Room | Price: ₹" + price + " | 2 Beds");
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 6000);
    }

    public void displayDetails() {
        System.out.println("Suite Room | Price: ₹" + price + " | Luxury Suite");
    }
}

/* ---------------- INVENTORY ---------------- */

class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

/* ---------------- SEARCH SERVICE ---------------- */

class RoomSearchService {

    public void searchRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("\nAvailable Rooms:\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Only show rooms with availability > 0
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("------------------------");
            }
        }
    }
}

/* ---------------- MAIN CLASS ---------------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Room Search System v4.0");

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Room objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Search Service
        RoomSearchService searchService = new RoomSearchService();

        // Perform Search
        searchService.searchRooms(inventory, rooms);

        System.out.println("\nSearch completed successfully.");
    }
}