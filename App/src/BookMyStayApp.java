import java.util.*;

// Booking request model
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking Queue (Thread-safe retrieval)
class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
        notify(); // Wake waiting threads
    }

    public synchronized BookingRequest getRequest() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.poll();
    }
}

// Shared Room Inventory (Critical Section)
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Standard", 2);
        rooms.put("Deluxe", 2);
        rooms.put("Suite", 1);
    }

    // 🔴 Critical Section
    public synchronized boolean allocateRoom(String type, String guest) {
        if (rooms.get(type) > 0) {
            int remaining = rooms.get(type) - 1;
            rooms.put(type, remaining);
            System.out.println(guest + " booked " + type +
                    " room. Remaining: " + remaining);
            return true;
        } else {
            System.out.println(guest + " FAILED to book " + type +
                    " (No rooms left)");
            return false;
        }
    }
}

// Worker Thread
class BookingProcessor extends Thread {
    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(String name, BookingQueue queue, RoomInventory inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        try {
            while (true) {
                BookingRequest request = queue.getRequest();
                inventory.allocateRoom(request.roomType, request.guestName);
                Thread.sleep(500); // simulate processing time
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " stopped.");
        }
    }
}

// MAIN PROGRAM
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) throws Exception {

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        // Create worker threads
        BookingProcessor t1 = new BookingProcessor("Processor-1", queue, inventory);
        BookingProcessor t2 = new BookingProcessor("Processor-2", queue, inventory);

        t1.start();
        t2.start();

        // Simulate multiple guests sending requests simultaneously
        queue.addRequest(new BookingRequest("Alice", "Standard"));
        queue.addRequest(new BookingRequest("Bob", "Standard"));
        queue.addRequest(new BookingRequest("Charlie", "Standard"));
        queue.addRequest(new BookingRequest("David", "Suite"));
        queue.addRequest(new BookingRequest("Eva", "Suite"));

        // Let simulation run for few seconds
        Thread.sleep(5000);
        t1.interrupt();
        t2.interrupt();

        System.out.println("Simulation Finished Safely ✅");
    }
}