package ui;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu() {

        while (true) {

            System.out.println("\nMain Menu");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:
                    findAndReserveRoom();
                    break;

                case 2:
                    seeMyReservations();
                    break;

                case 3:
                    createAccount();
                    break;

                case 4:
                    AdminMenu.adminMenu();
                    break;

                case 5:
                    System.out.println("Thank you for using the hotel reservation system.");
                    System.exit(0);

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void findAndReserveRoom() {

        System.out.println("Enter check-in date (yyyy-mm-dd): ");
        String checkIn = scanner.nextLine();

        System.out.println("Enter check-out date (yyyy-mm-dd): ");
        String checkOut = scanner.nextLine();

        Date checkInDate = java.sql.Date.valueOf(checkIn);
        Date checkOutDate = java.sql.Date.valueOf(checkOut);

        Collection<IRoom> rooms = hotelResource.findARoom(checkInDate, checkOutDate);

        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        for (IRoom room : rooms) {
            System.out.println(room);
        }

        System.out.println("Enter room number to reserve:");
        String roomNumber = scanner.nextLine();

        System.out.println("Enter your email:");
        String email = scanner.nextLine();

        IRoom room = hotelResource.getRoom(roomNumber);

        Reservation reservation =
                hotelResource.bookARoom(email, room, checkInDate, checkOutDate);

        System.out.println("Reservation successful:");
        System.out.println(reservation);
    }

    private static void seeMyReservations() {

        System.out.println("Enter your email:");
        String email = scanner.nextLine();

        Collection<Reservation> reservations =
                hotelResource.getCustomersReservations(email);

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    private static void createAccount() {

        System.out.println("Enter email:");
        String email = scanner.nextLine();

        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        hotelResource.createACustomer(email, firstName, lastName);

        System.out.println("Account created successfully.");
    }
}