import java.util.Scanner;

public class CommunityAccessSystem {

    Scanner scanner = new Scanner(System.in);

    // Can store up to 100 residents
    Resident[] residents = new Resident[100];

    // Counts the number of residents
    int residentCount = 0;

    // Used to create resident ID
    int nextResidentNumber = 1;


    // Starts the system
    public void run() {

        int choice;

        // Load sample data
        loadSampleData();

        do {

            System.out.println();
            System.out.println("===============================================");
            System.out.println("   COMMUNITY ACCESS CONTROL & DUES COLLECTION");
            System.out.println("       Residential Management System");
            System.out.println("===============================================");
            System.out.println("1. Register New Resident");
            System.out.println("2. View All Residents");
            System.out.println("3. Record Dues Payment");
            System.out.println("4. Check Access Status");
            System.out.println("5. View Outstanding Dues");
            System.out.println("6. Financial Summary");
            System.out.println("7. Exit");
            System.out.println("===============================================");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    registerResident();
                    break;

                case 2:
                    viewResidents();
                    break;

                case 3:
                    recordPayment();
                    break;

                case 4:
                    checkAccessStatus();
                    break;

                case 5:
                    viewOutstandingDues();
                    break;

                case 6:
                    financialSummary();
                    break;

                case 7:
                    System.out.println();
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println();
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 7);

        scanner.close();
    }


    // REGISTER NEW RESIDENT
    public void registerResident() {

        System.out.println();
        System.out.println("===== REGISTER NEW RESIDENT =====");

        if (residentCount >= residents.length) {
            System.out.println("The system is full. Cannot add more residents.");
            return;
        }

        System.out.print("Resident Name: ");
        String name = scanner.nextLine();

        System.out.print("Unit Number: ");
        String unitNumber = scanner.nextLine();

        System.out.print("Monthly Dues Amount (Rs.): ");
        double monthlyDues = scanner.nextDouble();
        scanner.nextLine();

        // Create resident ID
        String residentID = "RES-" +
                String.format("%03d", nextResidentNumber);

        // Create a new Resident object
        residents[residentCount] = new Resident(
                residentID,
                name,
                unitNumber,
                monthlyDues,
                0 // Initial payment is 0
        );

        residentCount++;
        nextResidentNumber++;

        System.out.println();
        System.out.println("Resident registered successfully!");
        System.out.println("Resident ID: " + residentID);
        System.out.println("Name: " + name);
        System.out.println("Unit: " + unitNumber);
        System.out.println("Monthly Dues: Rs. " + monthlyDues);
    }


    // VIEW ALL RESIDENTS
    public void viewResidents() {

        System.out.println();
        System.out.println("===== ALL RESIDENTS =====");

        if (residentCount == 0) {
            System.out.println("No residents found.");
            return;
        }

        for (int i = 0; i < residentCount; i++) {

            System.out.println();
            System.out.println("Resident " + (i + 1));

            residents[i].displayResident();
        }

        System.out.println();
        System.out.println("Total Residents: " + residentCount);
    }


    // RECORD DUES PAYMENT
    public void recordPayment() {

        System.out.println();
        System.out.println("===== RECORD DUES PAYMENT =====");

        if (residentCount == 0) {
            System.out.println("No residents found.");
            return;
        }

        System.out.print("Enter Resident ID: ");
        String residentID = scanner.nextLine();

        for (int i = 0; i < residentCount; i++) {

            if (residents[i].getResidentID().equals(residentID)) {

                System.out.println();
                System.out.println("Resident: " + residents[i].getName());
                System.out.println("Current Total Paid: Rs. " + 
                        residents[i].getTotalPaid());

                System.out.print("Payment Amount (Rs.): ");
                double paymentAmount = scanner.nextDouble();
                scanner.nextLine();

                if (paymentAmount <= 0) {
                    System.out.println("Invalid payment amount.");
                    return;
                }

                // Add payment to total
                double newTotalPaid = residents[i].getTotalPaid() + 
                        paymentAmount;
                residents[i].setTotalPaid(newTotalPaid);

                System.out.println();
                System.out.println("Payment recorded successfully!");
                System.out.println("Payment Amount: Rs. " + paymentAmount);
                System.out.println("New Total Paid: Rs. " + newTotalPaid);

                // Check if outstanding dues reduced to allow access
                double outstanding = residents[i].getOutstandingDues();

                if (outstanding < residents[i].getMonthlyDues()) {
                    residents[i].setAccessStatus("Allowed");
                    System.out.println("Access Status: ALLOWED");
                }

                return;
            }
        }

        System.out.println("Resident not found.");
    }


    // CHECK ACCESS STATUS
    public void checkAccessStatus() {

        System.out.println();
        System.out.println("===== CHECK ACCESS STATUS =====");

        if (residentCount == 0) {
            System.out.println("No residents found.");
            return;
        }

        System.out.print("Enter Resident ID: ");
        String residentID = scanner.nextLine();

        for (int i = 0; i < residentCount; i++) {

            if (residents[i].getResidentID().equals(residentID)) {

                System.out.println();
                System.out.println("===== ACCESS VERIFICATION =====");

                String name = residents[i].getName();
                String unit = residents[i].getUnitNumber();
                double outstanding = residents[i].getOutstandingDues();
                double monthlyDues = residents[i].getMonthlyDues();

                System.out.println("Resident: " + name);
                System.out.println("Unit: " + unit);
                System.out.println("Outstanding Dues: Rs. " + 
                        String.format("%.2f", outstanding));

                // Decision logic for access
                if (outstanding > monthlyDues * 2) {

                    residents[i].setAccessStatus("Denied");
                    System.out.println();
                    System.out.println("❌ ACCESS DENIED");
                    System.out.println("Reason: Outstanding dues exceed 2 months");
                    System.out.println("Outstanding: Rs. " + 
                            String.format("%.2f", outstanding));

                } else if (outstanding > monthlyDues) {

                    System.out.println();
                    System.out.println("⚠ ACCESS WARNING");
                    System.out.println("You are " + 
                            String.format("%.2f", outstanding - monthlyDues) + 
                            " Rs. overdue");
                    System.out.println("Status: Access Allowed (Payment Due Soon)");

                } else {

                    residents[i].setAccessStatus("Allowed");
                    System.out.println();
                    System.out.println("✅ ACCESS ALLOWED");
                    System.out.println("Status: Current on payments");
                }

                return;
            }
        }

        System.out.println("Resident not found.");
    }


    // VIEW OUTSTANDING DUES
    public void viewOutstandingDues() {

        System.out.println();
        System.out.println("===== OUTSTANDING DUES REPORT =====");

        if (residentCount == 0) {
            System.out.println("No residents found.");
            return;
        }

        System.out.println();
        System.out.println("Resident ID | Name | Outstanding Dues (Rs.)");
        System.out.println("---------------------------------------");

        double totalOutstanding = 0;

        for (int i = 0; i < residentCount; i++) {

            String resID = residents[i].getResidentID();
            String name = residents[i].getName();
            double outstanding = residents[i].getOutstandingDues();

            System.out.println(resID + " | " + name + " | " + 
                    String.format("%.2f", outstanding));

            totalOutstanding += outstanding;
        }

        System.out.println("---------------------------------------");
        System.out.println("Total Outstanding: Rs. " + 
                String.format("%.2f", totalOutstanding));

        // Identify overdue residents
        System.out.println();
        System.out.println("--- OVERDUE RESIDENTS ---");

        int overdueCount = 0;

        for (int i = 0; i < residentCount; i++) {

            double outstanding = residents[i].getOutstandingDues();
            double monthlyDues = residents[i].getMonthlyDues();

            if (outstanding > monthlyDues) {

                System.out.println(residents[i].getResidentID() + " - " + 
                        residents[i].getName() + 
                        ": Rs. " + 
                        String.format("%.2f", outstanding));

                overdueCount++;
            }
        }

        if (overdueCount == 0) {
            System.out.println("No overdue residents.");
        }
    }


    // FINANCIAL SUMMARY
    public void financialSummary() {

        System.out.println();
        System.out.println("===== FINANCIAL SUMMARY =====");

        if (residentCount == 0) {
            System.out.println("No residents found.");
            return;
        }

        double totalExpectedDues = 0;
        double totalPaid = 0;
        double totalOutstanding = 0;

        int allowedCount = 0;
        int deniedCount = 0;
        int warningCount = 0;

        for (int i = 0; i < residentCount; i++) {

            double expectedDues = residents[i].getMonthlyDues() * 3;
            double paid = residents[i].getTotalPaid();
            double outstanding = residents[i].getOutstandingDues();

            totalExpectedDues += expectedDues;
            totalPaid += paid;
            totalOutstanding += outstanding;

            // Categorize residents
            if (outstanding > residents[i].getMonthlyDues() * 2) {

                deniedCount++;

            } else if (outstanding > residents[i].getMonthlyDues()) {

                warningCount++;

            } else {

                allowedCount++;
            }
        }

        System.out.println();
        System.out.println("--- FINANCIAL STATUS ---");
        System.out.println("Total Expected Dues: Rs. " + 
                String.format("%.2f", totalExpectedDues));
        System.out.println("Total Amount Collected: Rs. " + 
                String.format("%.2f", totalPaid));
        System.out.println("Total Outstanding: Rs. " + 
                String.format("%.2f", totalOutstanding));

        System.out.println();
        System.out.println("--- COLLECTION RATE ---");

        double collectionRate = 0;

        if (totalExpectedDues > 0) {
            collectionRate = (totalPaid / totalExpectedDues) * 100;
        }

        System.out.println("Collection Rate: " + 
                String.format("%.2f", collectionRate) + "%");

        // Determine status based on collection rate
        String status;

        if (collectionRate >= 80) {

            status = "Excellent";

        } else if (collectionRate >= 60) {

            status = "Good";

        } else {

            status = "Poor";
        }

        System.out.println("Status: " + status);

        System.out.println();
        System.out.println("--- RESIDENT STATUS ---");
        System.out.println("Current (Dues Paid): " + allowedCount);
        System.out.println("Warning (1+ Month Overdue): " + warningCount);
        System.out.println("Blocked (2+ Month Overdue): " + deniedCount);

        System.out.println();
        System.out.println("Total Residents: " + residentCount);
    }


    // LOAD SAMPLE DATA
    private void loadSampleData() {

        // Sample Resident 1
        residents[0] = new Resident(
                "RES-001",
                "Raj Kumar",
                "Apt 101",
                5000,
                5000
        );
        residents[0].setAccessStatus("Allowed");

        // Sample Resident 2
        residents[1] = new Resident(
                "RES-002",
                "Priya Singh",
                "Apt 102",
                5000,
                10000
        );
        residents[1].setAccessStatus("Allowed");

        // Sample Resident 3
        residents[2] = new Resident(
                "RES-003",
                "Amit Patel",
                "Apt 103",
                5500,
                5500
        );
        residents[2].setAccessStatus("Allowed");

        // Sample Resident 4
        residents[3] = new Resident(
                "RES-004",
                "Neha Verma",
                "Apt 201",
                5000,
                0
        );
        residents[3].setAccessStatus("Denied");

        residentCount = 4;
        nextResidentNumber = 5;
    }
}
