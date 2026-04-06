import java.util.*;

// Represents an optional add-on service
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Manages mapping between Reservation ID and Add-On Services
class AddOnServiceManager {

    // reservationID → list of services
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    // Attach service to reservation
    public void addServiceToReservation(String reservationId, AddOnService service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Service '" + service.getServiceName() +
                "' added to Reservation " + reservationId);
    }

    // View services of a reservation
    public void viewServices(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected for Reservation " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");
        for (AddOnService s : services) {
            System.out.println("- " + s);
        }
    }

    // Calculate total add-on cost
    public double calculateServiceCost(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);
        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// MAIN PROGRAM
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        // Predefined services (easy to extend later)
        List<AddOnService> availableServices = Arrays.asList(
                new AddOnService("Breakfast", 500),
                new AddOnService("Airport Pickup", 1200),
                new AddOnService("Spa Access", 2000),
                new AddOnService("Extra Bed", 800)
        );

        System.out.println("===== ADD-ON SERVICE SELECTION =====");
        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        int choice;
        do {
            System.out.println("\nAvailable Services:");
            for (int i = 0; i < availableServices.size(); i++) {
                System.out.println((i + 1) + ". " + availableServices.get(i));
            }
            System.out.println("0. Finish Selection");

            System.out.print("Choose service: ");
            choice = sc.nextInt();

            if (choice > 0 && choice <= availableServices.size()) {
                manager.addServiceToReservation(
                        reservationId,
                        availableServices.get(choice - 1)
                );
            }

        } while (choice != 0);

        // Show selected services
        manager.viewServices(reservationId);

        // Show total cost
        double totalCost = manager.calculateServiceCost(reservationId);
        System.out.println("\nTotal Add-On Cost = ₹" + totalCost);

        System.out.println("\nBooking & Room Allocation remain unchanged ✅");
    }
}