package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    private static final Map<String, IRoom> rooms = new HashMap<>();
    private static final Collection<Reservation> reservations = new ArrayList<>();

    // static reference
    private static ReservationService instance = new ReservationService();

    public static ReservationService getInstance() {
        return instance;
    }

    // Add room
    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    // Get a room
    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    // Reserve a room
    public Reservation reserveARoom(Customer customer, IRoom room,
                                    Date checkInDate, Date checkOutDate) {

        Reservation reservation =
                new Reservation(customer, room, checkInDate, checkOutDate);

        reservations.add(reservation);

        return reservation;
    }

    // Find available rooms
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {

        Collection<IRoom> availableRooms = new ArrayList<>(rooms.values());

        for (Reservation reservation : reservations) {

            if (!(checkOutDate.before(reservation.getCheckInDate()) ||
                    checkInDate.after(reservation.getCheckOutDate()))) {

                availableRooms.remove(reservation.getRoom());
            }
        }

        return availableRooms;
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    // Get customer reservations
    public Collection<Reservation> getCustomersReservation(Customer customer) {

        Collection<Reservation> customerReservations = new ArrayList<>();

        for (Reservation reservation : reservations) {

            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }

        return customerReservations;
    }

    // Print all reservations
    public void printAllReservation() {

        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}