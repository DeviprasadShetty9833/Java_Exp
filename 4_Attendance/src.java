
import java.io.*;
import java.util.*;

class AttendanceLogger {
    private static final String FILE_NAME = "attendance_log.txt";

    // Add attendance entry
    public static void addEntry(String date, String course, int present, int absent, String notes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String line = date + "," + course + "," + present + "," + absent + "," + notes;
            bw.write(line);
            bw.newLine();
            System.out.println("✅ Attendance entry added successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error writing to file: " + e.getMessage());
        }
    }

    // Read all entries
    public static void readEntries() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("📘 Attendance Log:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }

    // Generate summary per course
    public static void generateSummary() {
        Map<String, int[]> summary = new HashMap<>(); // course -> [present, absent, days]
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String course = parts[1];
                    int present = Integer.parseInt(parts[2]);
                    int absent = Integer.parseInt(parts[3]);

                    summary.putIfAbsent(course, new int[3]);
                    int[] stats = summary.get(course);
                    stats[0] += present;
                    stats[1] += absent;
                    stats[2]++;
                }
            }

            for (String course : summary.keySet()) {
                int[] stats = summary.get(course);
                double avg = (stats[0] * 100.0) / (stats[0] + stats[1]);

                try (BufferedWriter bw = new BufferedWriter(new FileWriter("course_summary_" + course + ".txt"))) {
                    bw.write("Course: " + course);
                    bw.newLine();
                    bw.write("Total Days: " + stats[2]);
                    bw.newLine();
                    bw.write("Total Present: " + stats[0]);
                    bw.newLine();
                    bw.write("Total Absent: " + stats[1]);
                    bw.newLine();
                    bw.write("Average Attendance %: " + avg);
                    bw.newLine();
                }
                System.out.println("📊 Summary generated for course: " + course);
            }
        } catch (IOException e) {
            System.out.println("❌ Error generating summary: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n===== Attendance Logger Menu =====");
            System.out.println("1. Add Entry");
            System.out.println("2. Read Entries");
            System.out.println("3. Generate Summary");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = sc.nextLine();
                    System.out.print("Enter course code: ");
                    String course = sc.nextLine();
                    System.out.print("Enter present count: ");
                    int present = sc.nextInt();
                    System.out.print("Enter absent count: ");
                    int absent = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter notes: ");
                    String notes = sc.nextLine();
                    addEntry(date, course, present, absent, notes);
                    break;
                case 2:
                    readEntries();
                    break;
                case 3:
                    generateSummary();
                    break;
                case 4:
                    System.out.println(" Exiting...");
                    break;
                default:
                    System.out.println(" Invalid choice.");
            }
        } while (choice != 4);
        sc.close();
    }
}



