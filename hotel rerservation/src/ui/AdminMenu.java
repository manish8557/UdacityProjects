package ui;

import api.AdminResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

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

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:
                    Collection customers = adminResource.getAllCustomers();
                    customers.forEach(System.out::println);
                    break;

                case 2:
                    Collection<IRoom> rooms = adminResource.getAllRooms();
                    rooms.forEach(System.out::println);
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

        System.out.println("Enter room number:");
        String roomNumber = scanner.nextLine();

        System.out.println("Enter room price:");
        double price = scanner.nextDouble();

        System.out.println("Enter room type (1 = SINGLE, 2 = DOUBLE):");
        int type = scanner.nextInt();
        scanner.nextLine();

        RoomType roomType = (type == 1) ? RoomType.SINGLE : RoomType.DOUBLE;

        IRoom room = new Room(roomNumber, price, roomType);
        rooms.add(room);

        adminResource.addRoom(rooms);

        System.out.println("Room added successfully.");
    }
}