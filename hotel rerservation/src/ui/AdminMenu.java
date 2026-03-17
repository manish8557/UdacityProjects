package ui;

import api.AdminResource;
import model.*;

import java.util.*;

public class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void adminMenu() {

        while (true) {

            System.out.println("\nAdmin Menu");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");

            int option;

            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                continue;
            }

            switch (option) {

                case 1:
                    Collection<Customer> customers = adminResource.getAllCustomers();

                    if (customers.isEmpty()) {
                        System.out.println("No customers found.");
                    } else {
                        customers.forEach(System.out::println);
                    }
                    break;

                case 2:
                    Collection<IRoom> rooms = adminResource.getAllRooms();

                    if (rooms.isEmpty()) {
                        System.out.println("No rooms found.");
                    } else {
                        rooms.forEach(System.out::println);
                    }
                    break;

                case 3:
                    adminResource.displayAllReservations();
                    break;

                case 4:
                    addRoom();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void addRoom() {

        List<IRoom> rooms = new ArrayList<>();

        try {

            System.out.println("Enter room number:");
            String roomNumber = scanner.nextLine();

            if (roomNumber == null || roomNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("Room number cannot be empty.");
            }

            // ✅ Prevent duplicate room numbers
            if (adminResource.getAllRooms().stream()
                    .anyMatch(r -> r.getRoomNumber().equals(roomNumber))) {

                System.out.println("Room already exists.");
                return;
            }

            System.out.println("Enter room price:");
            double price;

            try {
                price = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a number.");
                return;
            }

            if (price < 0) {
                throw new IllegalArgumentException("Room price cannot be negative.");
            }

            System.out.println("Enter room type (1 = SINGLE, 2 = DOUBLE):");
            int type;

            try {
                type = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid room type. Please enter 1 or 2.");
                return;
            }

            RoomType roomType;

            if (type == 1) {
                roomType = RoomType.SINGLE;
            } else if (type == 2) {
                roomType = RoomType.DOUBLE;
            } else {
                throw new IllegalArgumentException("Invalid room type. Choose 1 or 2.");
            }

            IRoom room;

            // ✅ Polymorphism (FreeRoom usage)
            if (price == 0) {
                room = new FreeRoom(roomNumber, roomType);
            } else {
                room = new Room(roomNumber, price, roomType);
            }

            rooms.add(room);

            adminResource.addRoom(rooms);

            System.out.println("Room added successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


}
