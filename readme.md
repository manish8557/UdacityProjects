# Hotel Reservation System (Java)

## Overview

This project is a **console-based Hotel Reservation System** developed in **Java** using Object-Oriented Programming principles.
The application allows customers to create accounts, search for rooms, make reservations, and view their bookings.
It also provides an **admin interface** to manage rooms, customers, and reservations.

The system follows a **layered architecture**:

* Model Layer
* Service Layer
* API Layer
* UI Layer

---

## Features

### Customer Features

1. **Create an Account**

   * Users can create an account using email, first name, and last name.

2. **Find and Reserve a Room**

   * Search for available rooms using check-in and check-out dates.

3. **View Reservations**

   * Customers can view all reservations associated with their email.

---

### Admin Features

1. **View All Customers**
2. **View All Rooms**
3. **View All Reservations**
4. **Add New Rooms**

---

## Project Structure

```
src
│
├── model
│   ├── Customer.java
│   ├── Room.java
│   ├── FreeRoom.java
│   ├── Reservation.java
│   ├── IRoom.java
│   └── RoomType.java
│
├── service
│   ├── CustomerService.java
│   └── ReservationService.java
│
├── api
│   ├── HotelResource.java
│   └── AdminResource.java
│
├── ui
│   ├── MainMenu.java
│   └── AdminMenu.java
│
└── HotelApplication.java
```

---

## Technologies Used

* Java
* Object-Oriented Programming (OOP)
* Collections Framework
* Date Handling
* Command Line Interface (CLI)

---

## How to Compile and Run

### Compile the Project

Navigate to the **src** folder and run:

```
javac model/*.java service/*.java api/*.java ui/*.java HotelApplication.java
```

### Run the Application

```
java HotelApplication
```

---

## Example Main Menu

```
Main Menu
1. Find and reserve a room
2. See my reservations
3. Create an account
4. Admin
5. Exit
```

---

## Example Admin Menu

```
Admin Menu
1. See all Customers
2. See all Rooms
3. See all Reservations
4. Add a Room
5. Back to Main Menu
```

---

## Learning Outcomes

This project demonstrates:

* Java OOP concepts
* Interface implementation
* Inheritance
* Collections usage
* Layered application architecture
* Basic CLI application design

---

## Author

Manish Kumar
Computer Science Student | Java & Android Developer
