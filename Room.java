package Applications;

public class Room {
    private int roomNumber;
    private String roomType;
    private double price;
    private boolean booked;
    private String customerName;

    public Room(int roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.booked = false;
        this.customerName = "";
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBooked() {
        return booked;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void bookRoom(String customerName) {
        this.booked = true;
        this.customerName = customerName;
    }

    public void checkoutRoom() {
        this.booked = false;
        this.customerName = "";
    }

    @Override
    public String toString() {
        String status = booked ? "Booked by " + customerName : "Available";
        return "Room No: " + roomNumber +
               " | Type: " + roomType +
               " | Price: " + price +
               " | Status: " + status;
    }
}