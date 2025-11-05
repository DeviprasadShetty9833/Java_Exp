📘 File 1: transport/Transport.java
package transport;

// Abstract base class representing all transport modes
public abstract class Transport {
    // Abstract methods (must be overridden by child classes)
    public abstract double calculateFare(int distance);
    public abstract int getETA(int distance);
    public abstract void printReceipt(String source, String destination, int distance);
}

📘 File 2: transport/Bus.java
package transport;

public class Bus extends Transport {
    @Override
    public double calculateFare(int distance) {
        return distance * 5.0; // ₹5 per km
    }

    @Override
    public int getETA(int distance) {
        return distance / 2; // Bus speed ≈ 30 km/h
    }

    @Override
    public void printReceipt(String source, String destination, int distance) {
        double fare = calculateFare(distance);
        int eta = getETA(distance);
        System.out.println("----- BUS RECEIPT -----");
        System.out.println("From: " + source);
        System.out.println("To: " + destination);
        System.out.println("Distance: " + distance + " km");
        System.out.println("Estimated Time: " + eta + " mins");
        System.out.println("Fare: ₹" + fare);
        System.out.println("------------------------\n");
    }
}

📘 File 3: transport/Metro.java
package transport;

public class Metro extends Transport {
    @Override
    public double calculateFare(int distance) {
        return 20 + (distance * 3.5); // ₹20 base + ₹3.5 per km
    }

    @Override
    public int getETA(int distance) {
        return distance / 3; // Faster than bus
    }

    @Override
    public void printReceipt(String source, String destination, int distance) {
        double fare = calculateFare(distance);
        int eta = getETA(distance);
        System.out.println("----- METRO RECEIPT -----");
        System.out.println("From: " + source);
        System.out.println("To: " + destination);
        System.out.println("Distance: " + distance + " km");
        System.out.println("Estimated Time: " + eta + " mins");
        System.out.println("Fare: ₹" + fare);
        System.out.println("--------------------------\n");
    }
}

📘 File 4: transport/Auto.java
package transport;

public class Auto extends Transport {
    @Override
    public double calculateFare(int distance) {
        return 30 + (distance * 10); // ₹30 base + ₹10 per km
    }

    @Override
    public int getETA(int distance) {
        return distance * 3; // Slower due to traffic
    }

    @Override
    public void printReceipt(String source, String destination, int distance) {
        double fare = calculateFare(distance);
        int eta = getETA(distance);
        System.out.println("----- AUTO RECEIPT -----");
        System.out.println("From: " + source);
        System.out.println("To: " + destination);
        System.out.println("Distance: " + distance + " km");
        System.out.println("Estimated Time: " + eta + " mins");
        System.out.println("Fare: ₹" + fare);
        System.out.println("-------------------------\n");
    }
}

📘 File 5: transport/EBike.java
package transport;

public class EBike extends Transport {
    @Override
    public double calculateFare(int distance) {
        return distance * 2.5; // ₹2.5 per km
    }

    @Override
    public int getETA(int distance) {
        return distance * 2; // Moderate speed
    }

    @Override
    public void printReceipt(String source, String destination, int distance) {
        double fare = calculateFare(distance);
        int eta = getETA(distance);
        System.out.println("----- E-BIKE RECEIPT -----");
        System.out.println("From: " + source);
        System.out.println("To: " + destination);
        System.out.println("Distance: " + distance + " km");
        System.out.println("Estimated Time: " + eta + " mins");
        System.out.println("Fare: ₹" + fare);
        System.out.println("---------------------------\n");
    }
}

📘 File 6: app/MainApp.java
package app;

import transport.*;

public class MainApp {
    public static void main(String[] args) {
        Transport t; // Base class reference (polymorphic)

        // Example 1: Bus booking
        t = new Bus();
        t.printReceipt("Andheri", "Dadar", 10);

        // Example 2: Metro booking
        t = new Metro();
        t.printReceipt("Borivali", "Churchgate", 25);

        // Example 3: Auto booking
        t = new Auto();
        t.printReceipt("Powai", "Ghatkopar", 8);

        // Example 4: E-Bike booking
        t = new EBike();
        t.printReceipt("Thane", "Mulund", 6);
    }
}
