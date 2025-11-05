// File: BookingSystem.java

// 1. Abstract Base Class
abstract class Transport {
    abstract double calculateFare(int distance);
    abstract int getETA(int distance);
    abstract void printReceipt(String source, String destination, int distance);

    protected void printHeader(String transportType) {
        System.out.println("=========================================");
        System.out.println("  Smart Transport Receipt (" + transportType + ")");
        System.out.println("=========================================");
    }

    protected void printFooter() {
        System.out.println("Thank you for using Smart Transport!");
        System.out.println("=========================================\n");
    }
}

// 2. Derived Classes

class Bus extends Transport {
    private static final double BASE_FARE = 10.0;
    private static final double PER_KM_CHARGE = 2.0;
    private static final int AVG_SPEED_KMH = 30;

    double calculateFare(int distance) {
        return BASE_FARE + (distance * PER_KM_CHARGE);
    }

    int getETA(int distance) {
        return (int) ((double) distance / AVG_SPEED_KMH * 60);
    }

    void printReceipt(String source, String destination, int distance) {
        double fare = calculateFare(distance);
        int eta = getETA(distance);
        printHeader("Bus");
        System.out.printf("From: %s\nTo: %s\n", source, destination);
        System.out.printf("Distance: %d km\nEstimated Time: %d minutes\n", distance, eta);
        System.out.printf("Total Fare: $%.2f\n", fare);
        printFooter();
    }
}

class Metro extends Transport {
    private static final int AVG_SPEED_KMH = 50;
    private static final int WAIT_TIME = 5;

    double calculateFare(int distance) {
        if (distance <= 5) return 20.0;
        else if (distance <= 15) return 30.0;
        else return 40.0;
    }

    int getETA(int distance) {
        int travelTime = (int) ((double) distance / AVG_SPEED_KMH * 60);
        return travelTime + WAIT_TIME;
    }

    void printReceipt(String source, String destination, int distance) {
        double fare = calculateFare(distance);
        int eta = getETA(distance);
        printHeader("Metro");
        System.out.printf("From Station: %s\nTo Station: %s\n", source, destination);
        System.out.printf("Distance: %d km\nEstimated Time: %d minutes\n", distance, eta);
        System.out.printf("Total Fare: $%.2f\n", fare);
        printFooter();
    }
}

class Auto extends Transport {
    private static final double BASE_FARE = 25.0;
    private static final double PER_KM_CHARGE = 8.0;
    private static final int AVG_SPEED_KMH = 35;

    double calculateFare(int distance) {
        return BASE_FARE + (distance * PER_KM_CHARGE);
    }

    int getETA(int distance) {
        return (int) ((double) distance / AVG_SPEED_KMH * 60);
    }

    void printReceipt(String source, String destination, int distance) {
        double fare = calculateFare(distance);
        int eta = getETA(distance);
        printHeader("Auto-rickshaw");
        System.out.printf("Pickup: %s\nDrop: %s\n", source, destination);
        System.out.printf("Distance: %d km\nEstimated Time: %d minutes\n", distance, eta);
        System.out.printf("Total Fare: $%.2f\n", fare);
        printFooter();
    }
}

class EBike extends Transport {
    private static final double PER_KM_CHARGE = 3.0;
    private static final int AVG_SPEED_KMH = 25;

    double calculateFare(int distance) {
        return distance * PER_KM_CHARGE;
    }

    int getETA(int distance) {
        return (int) ((double) distance / AVG_SPEED_KMH * 60);
    }

    void printReceipt(String source, String destination, int distance) {
        double fare = calculateFare(distance);
        int eta = getETA(distance);
        printHeader("E-Bike");
        System.out.printf("Start: %s\nEnd: %s\n", source, destination);
        System.out.printf("Distance: %d km\nEstimated Time: %d minutes\n", distance, eta);
        System.out.printf("Total Fare: $%.2f\n", fare);
        printFooter();
    }
}

// 3. Main Class
public class BookingSystem {
    public static void main(String[] args) {
        System.out.println("--- Smart Transport Booking System ---");
        System.out.println("Demonstrating Runtime Polymorphism\n");

        Transport transport;

        transport = new Bus();
        transport.printReceipt("City Center", "University", 12);

        transport = new Metro();
        transport.printReceipt("Main Station", "North Plaza", 7);

        transport = new Auto();
        transport.printReceipt("Hospital", "Railway Station", 5);

        transport = new EBike();
        transport.printReceipt("Park", "Library", 3);

        System.out.println("--- Demonstration Complete ---");
    }
}
