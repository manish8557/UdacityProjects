package ui;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.util.Calendar;
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

            int option;

            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                continue; // restart loop
            }

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

        Date checkInDate;
        Date checkOutDate;

        try {
            checkInDate = java.sql.Date.valueOf(checkIn);
            checkOutDate = java.sql.Date.valueOf(checkOut);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
            return;
        }

        Date today = new Date();

        if (checkInDate.before(today)) {
            System.out.println("Check-in date cannot be in the past.");
            return;
        }

        if (!checkOutDate.after(checkInDate)) {
            System.out.println("Check-out date must be after check-in date.");
            return;
        }

        Collection<IRoom> rooms = hotelResource.findARoom(checkInDate, checkOutDate);

        Date bookingCheckIn = checkInDate;
        Date bookingCheckOut = checkOutDate;

        // 🔁 Handle recommended rooms
        if (rooms.isEmpty()) {

            System.out.println("No rooms available for selected dates.");

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(checkInDate);
            calendar.add(Calendar.DATE, 7);
            bookingCheckIn = calendar.getTime();

            calendar.setTime(checkOutDate);
            calendar.add(Calendar.DATE, 7);
            bookingCheckOut = calendar.getTime();

            System.out.println("Searching recommended rooms for:");
            System.out.println(bookingCheckIn + " to " + bookingCheckOut);

            rooms = hotelResource.findARoom(bookingCheckIn, bookingCheckOut);

            if (rooms.isEmpty()) {
                System.out.println("No recommended rooms found.");
                return;
            }

            System.out.println("Recommended Rooms:");
        } else {
            System.out.println("Available Rooms:");
        }

        for (IRoom room : rooms) {
            System.out.println(room);
        }

        // 🔐 ROOM VALIDATION
        System.out.println("Enter room number to reserve:");
        String roomNumber = scanner.nextLine();

        IRoom room = hotelResource.getRoom(roomNumber);

        if (room == null) {
            System.out.println("Invalid room number.");
            return;
        }

        // 🔐 EMAIL VALIDATION
        System.out.println("Enter your email:");
        String email = scanner.nextLine();

        if (hotelResource.getCustomer(email) == null) {
            System.out.println("Customer not found. Please create an account first.");
            return;
        }

        // 🔒 FINAL BOOKING
        Reservation reservation =
                hotelResource.bookARoom(email, room, bookingCheckIn, bookingCheckOut);

        if (reservation == null) {
            System.out.println("Reservation failed.");
            return;
        }

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

        try {
            // 🔥 validate email first
            new model.Customer("test", "test", email);

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid email format. Please try again.");
            return;
        }

        // ✅ Only runs if email is valid
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        hotelResource.createACustomer(email, firstName, lastName);

        System.out.println("Account created successfully.");
    }
}