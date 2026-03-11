/**
 * UseCase5BookingRequestQueue.java
 * UC5: Booking Request (First-Come-First-Served)
 * Version: 5.0
 */

import java.util.Queue;
import java.util.LinkedList;

/* ---------------- RESERVATION CLASS ---------------- */

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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

/* ---------------- BOOKING REQUEST QUEUE ---------------- */

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request to queue
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display all requests in queue
    public void displayRequests() {

        System.out.println("\n--- Booking Requests in Queue ---");

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }
}

/* ---------------- MAIN APPLICATION ---------------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Booking Request Queue v5.0");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        bookingQueue.addRequest(new Reservation("Krishna", "Single Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Double Room"));
        bookingQueue.addRequest(new Reservation("Ananya", "Suite Room"));

        // Display queue (FIFO order)
        bookingQueue.displayRequests();

        System.out.println("\nAll booking requests stored in FIFO order.");
    }
}