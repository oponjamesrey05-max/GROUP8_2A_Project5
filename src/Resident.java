public class Resident {

    private String residentID;
    private String name;
    private String unitNumber;
    private double monthlyDues;
    private double totalPaid;
    private String accessStatus;

    // Constructor
    public Resident(String residentID, String name, String unitNumber,
                    double monthlyDues, double totalPaid) {

        this.residentID = residentID;
        this.name = name;
        this.unitNumber = unitNumber;
        this.monthlyDues = monthlyDues;
        this.totalPaid = totalPaid;

        // Every new resident starts with access allowed
        this.accessStatus = "Allowed";
    }

    // Get resident ID
    public String getResidentID() {
        return residentID;
    }

    // Get resident name
    public String getName() {
        return name;
    }

    // Get unit number
    public String getUnitNumber() {
        return unitNumber;
    }

    // Get monthly dues
    public double getMonthlyDues() {
        return monthlyDues;
    }

    // Get total paid
    public double getTotalPaid() {
        return totalPaid;
    }

    // Get access status
    public String getAccessStatus() {
        return accessStatus;
    }

    // Set access status
    public void setAccessStatus(String accessStatus) {
        this.accessStatus = accessStatus;
    }

    // Set total paid (add payment)
    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    // Calculate outstanding dues
    public double getOutstandingDues() {

        double expectedDues = monthlyDues * 3; // 3 months expected
        double outstanding = expectedDues - totalPaid;

        if (outstanding < 0) {
            outstanding = 0; // No negative dues
        }

        return outstanding;
    }

    // Display the resident information
    public void displayResident() {

        System.out.println("-----------------------------");
        System.out.println("Resident ID: " + residentID);
        System.out.println("Name: " + name);
        System.out.println("Unit: " + unitNumber);
        System.out.println("Monthly Dues: Rs. " + monthlyDues);
        System.out.println("Total Paid: Rs. " + totalPaid);
        System.out.println("Outstanding: Rs. " + 
                String.format("%.2f", getOutstandingDues()));
        System.out.println("Access Status: " + accessStatus);
        System.out.println("-----------------------------");
    }
}
