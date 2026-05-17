package Applications;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class RoomManager {
    private List<Room> rooms;

    public RoomManager() {
        rooms = new ArrayList<>();
        loadFromFile();

        if (rooms.isEmpty()) {
            rooms.add(new Room(101, "Single", 1200));
            rooms.add(new Room(102, "Double", 1800));
            rooms.add(new Room(103, "Deluxe", 2500));
        }
    }

    public boolean addRoom(int roomNumber, String roomType, double price) {
        if (findRoom(roomNumber) != null) {
            return false;
        }
        rooms.add(new Room(roomNumber, roomType, price));
        saveToFile();
        return true;
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public Room findRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public boolean bookRoom(int roomNumber, String customerName) {
        Room room = findRoom(roomNumber);
        if (room != null && !room.isBooked()) {
            room.bookRoom(customerName);
            saveToFile();
            return true;
        }
        return false;
    }

    public boolean checkoutRoom(int roomNumber) {
        Room room = findRoom(roomNumber);
        if (room != null && room.isBooked()) {
            room.checkoutRoom();
            saveToFile();
            return true;
        }
        return false;
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("rooms.txt"))) {
            for (Room room : rooms) {
                writer.write(
                    room.getRoomNumber() + "," +
                    room.getRoomType() + "," +
                    room.getPrice() + "," +
                    room.isBooked() + "," +
                    room.getCustomerName()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        File file = new File("rooms.txt");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                int roomNo = Integer.parseInt(data[0]);
                String type = data[1];
                double price = Double.parseDouble(data[2]);
                boolean booked = Boolean.parseBoolean(data[3]);
                String customer = data.length > 4 ? data[4] : "";

                Room room = new Room(roomNo, type, price);

                if (booked) {
                    room.bookRoom(customer);
                }

                rooms.add(room);
            }

        } catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }
}
