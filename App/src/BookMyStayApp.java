import java.util.*;

// Represents a confirmed reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int nights;
    private double totalCost;

    public Reservation(String reservationId, String guestName,
                       String roomType, int nights, double totalCost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
        this.totalCost = totalCost;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public int getNights() { return nights; }
    public double getTotalCost() { return totalCost; }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " +
                roomType + " | Nights: " + nights +
                " | ₹" + totalCost;
    }
}

// Stores confirmed bookings in order
class BookingHistory {
    private List<Reservation> reservations = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation stored in booking history.");
    }

    // Retrieve all reservations
    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(reservations);
    }
}

// Generates reports from history
class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> reservations) {
        System.out.println("\n===== BOOKING HISTORY =====");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {
        int totalBookings = reservations.size();
        int totalNights = 0;
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalNights += r.getNights();
            totalRevenue += r.getTotalCost();
        }

        System.out.println("\n===== BOOKING SUMMARY REPORT =====");
        System.out.println("Total Bookings : " + totalBookings);
        System.out.println("Total Nights   : " + totalNights);
        System.out.println("Total Revenue  : ₹" + totalRevenue);
    }
}

// MAIN PROGRAM
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        System.out.println("===== BOOKING HISTORY & REPORTING =====");

        // Simulating confirmed bookings
        int choice;
        do {
            System.out.println("\n1. Add Confirmed Booking");
            System.out.println("2. View Booking History");
            System.out.println("3. Generate Summary Report");
            System.out.println("0. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Guest Name: ");
                    String name = sc.nextLine();

                    System.out.print("Room Type: ");
                    String room = sc.nextLine();

                    System.out.print("Number of Nights: ");
                    int nights = sc.nextInt();

                    System.out.print("Total Cost: ");
                    double cost = sc.nextDouble();

                    Reservation reservation =
                            new Reservation(id, name, room, nights, cost);

                    history.addReservation(reservation);
                    break;

                case 2:
                    reportService.showAllBookings(
                            history.getAllReservations());
                    break;

                case 3:
                    reportService.generateSummary(
                            history.getAllReservations());
                    break;
            }

        } while (choice != 0);

        System.out.println("Program Ended.");
    }
}