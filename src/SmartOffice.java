import java.util.Scanner;

/**
 * SmartOffice class provides a command-line interface to manage office room bookings.
 * Users can log in, configure room counts, set capacities, and manage bookings.
 */
public class SmartOffice {
    public static void main(String[] args) {
        // Initialize the office configuration, occupancy sensor, and authentication service
        OfficeConfiguration officeConfig = OfficeConfiguration.getInstance();
        OccupancySensor sensor = new OccupancySensor();
        AuthenticationService authService = new AuthenticationService();
        Scanner scanner = new Scanner(System.in);
        int numberOfRooms = 0;

        // Authentication process for the user
        System.out.println("Please log in to access booking features.");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (!authService.authenticate(username, password)) {
            System.out.println("Invalid username or password. Access denied.");
            return;
        }

        System.out.println("Login successful. Welcome, " + username + "!");
        System.out.println("You now have access to booking and configuration features.");

        // Configure the number of rooms
        System.out.println("Config room count: ");
        try {
            numberOfRooms = Integer.parseInt(scanner.nextLine());
            StringBuilder roomList = new StringBuilder("Office configured with " + numberOfRooms + " meeting rooms: ");
            for (int i = 1; i <= numberOfRooms; i++) {
                officeConfig.addRoom(i, 0); // Initialize rooms with no capacity
                roomList.append("Room ").append(i);
                if (i < numberOfRooms) {
                    roomList.append(", ");
                }
            }
            System.out.println(roomList.toString() + ".");
        } catch (NumberFormatException e) {
            System.out.println("Invalid room count. Please enter a valid number.");
            return;
        }

        // Set maximum capacity for a specific room
        System.out.println("Config room max capacity [roomId] [capacity]: ");
        try {
            String[] inputs = scanner.nextLine().split(" ");
            int roomId = Integer.parseInt(inputs[0]);
            int capacity = Integer.parseInt(inputs[1]);

            if (capacity <= 0) {
                System.out.println("Invalid capacity. Please enter a valid positive number.");
                return;
            }

            Room room = officeConfig.getRoom(roomId);
            if (room == null) {
                System.out.println("Invalid room ID. Room does not exist.");
                return;
            }

            room.setCapacity(capacity); // Set the room capacity
            System.out.println("Room " + roomId + " maximum capacity set to " + capacity);
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter valid roomId and capacity.");
        }

        // Main command loop for room management
        while (true) {
            System.out.println("Enter command (Add occupant / Block room / Cancel room / Room statistics): ");
            String command = scanner.nextLine().trim();

            // Add Occupant Command
            if (command.equalsIgnoreCase("Add occupant")) {
                System.out.println("Add occupant [roomId] [occupants]: ");
                try {
                    String[] inputs = scanner.nextLine().split(" ");
                    int roomId = Integer.parseInt(inputs[0]);
                    int occupants = Integer.parseInt(inputs[1]);

                    Room room = officeConfig.getRoom(roomId);
                    if (room == null) {
                        System.out.println("Room " + roomId + " does not exist.");
                        continue;
                    }

                    // Check if capacity is set for the room
                    if (room.getCapacity() <= 0) {
                        System.out.println("Room " + roomId + " has not been configured with a capacity. Please set the capacity first.");
                        System.out.print("Enter maximum capacity for Room " + roomId + ": ");
                        try {
                            int capacity = Integer.parseInt(scanner.nextLine());
                            if (capacity <= 0) {
                                System.out.println("Invalid capacity. Please enter a valid positive number.");
                                continue;
                            }
                            room.setCapacity(capacity);
                            System.out.println("Room " + roomId + " maximum capacity set to " + capacity + ".");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid capacity.");
                            continue;
                        }
                    }

                    // Validate and set occupants
                    if (occupants < 0) {
                        System.out.println("Invalid input. Occupants cannot be negative.");
                    } else if (occupants > room.getCapacity()) {
                        System.out.println("Room " + roomId + " occupancy exceeds maximum capacity.");
                    } else {
                        room.addObserver(sensor);
                        room.setOccupancy(occupants);
                        if (occupants == 0) {
                            System.out.println("Room " + roomId + " is now unoccupied. AC and lights turned off.");
                        }
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid input. Please provide valid roomId and number of occupants.");
                }
            }

            // Block Room Command
            else if (command.equalsIgnoreCase("Block room")) {
                System.out.println("Block room [roomId] [startTime] [duration]: ");
                try {
                    String[] inputs = scanner.nextLine().split(" ");
                    int roomId = Integer.parseInt(inputs[0]);
                    String startTime = inputs[1];
                    int duration = Integer.parseInt(inputs[2]);

                    Room room = officeConfig.getRoom(roomId);
                    if (room == null) {
                        System.out.println("Room " + roomId + " does not exist.");
                        continue;
                    }

                    if (room.isBooked()) {
                        System.out.println("Room " + roomId + " is already booked during this time. Cannot book.");
                    } else {
                        BookingCommand bookingCommand = new BookingCommand(room, startTime, duration);
                        bookingCommand.execute();
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid input. Please provide valid roomId, start time, and duration.");
                }
            }

            // Cancel Room Command
            else if (command.equalsIgnoreCase("Cancel room")) {
                System.out.println("Cancel room [roomId]:");
                try {
                    int roomId = Integer.parseInt(scanner.nextLine());
                    Room room = officeConfig.getRoom(roomId);
                    if (room == null) {
                        System.out.println("Room " + roomId + " does not exist.");
                        continue;
                    }

                    if (room.isBooked()) {
                        room.cancelBooking();
                    } else {
                        System.out.println("Room " + roomId + " is not booked. Cannot cancel booking.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please provide a valid roomId.");
                }
            }

            // Room Usage Statistics Command
            else if (command.equalsIgnoreCase("Room statistics")) {
                System.out.println("Room Usage Statistics:");
                for (int i = 1; i <= numberOfRooms; i++) {
                    Room room = officeConfig.getRoom(i);
                    if (room != null) {
                        System.out.println("Room " + room.getRoomId() + ":");
                        System.out.println("  Total bookings: " + room.getTotalBookings());
                        System.out.println("  Total occupied time: " + room.getTotalOccupiedTime() + " ms");
                    }
                }
            }

            // Exit or Continue
            while (true) {
                System.out.print("Do you want to continue? (yes/no): ");
                String response = scanner.nextLine().trim().toLowerCase();

                if (response.equals("yes")) {
                    break; // Continue the main loop
                } else if (response.equals("no")) {
                    System.out.println("Exiting the program.");
                    scanner.close();
                    return; // Exit the program
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            }
        }
    }
}
