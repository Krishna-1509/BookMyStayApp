/**
 * UseCase2RoomInitialization.java
 * UC2: Basic Room Types & Static Availability
 *
 * @version 2.0
 */

/* Abstract Room Class */
abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: $" + price + " per night");
    }
}

/* Single Room */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 80);
    }
}

/* Double Room */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 150);
    }
}

/* Suite Room */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 600, 300);
    }
}

/* Main Application */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App         ");
        System.out.println("   Hotel Booking System v2.0     ");
        System.out.println("=================================");

        /* Room Objects */
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        /* Static Availability */
        int singleAvailable = 10;
        int doubleAvailable = 5;
        int suiteAvailable = 2;

        System.out.println("\n--- Room Details ---\n");

        single.displayDetails();
        System.out.println("Available Rooms: " + singleAvailable);
        System.out.println();

        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + doubleAvailable);
        System.out.println();

        suite.displayDetails();
        System.out.println("Available Rooms: " + suiteAvailable);
        System.out.println();

        System.out.println("Program executed successfully.");
    }
}