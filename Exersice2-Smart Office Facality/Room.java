import java.util.ArrayList;
import java.util.List;

/**
 * Room class represents a meeting room in the office.
 * It tracks occupancy, capacity, booking statistics, and observers.
 */
public class Room {
    private int roomId; // Unique identifier for the room
    private int capacity; // Maximum capacity of the room
    private int occupants; // Current number of occupants in the room
    private List<Observer> observers; // List of observers to notify on changes
    private String startTime; // Start time of the current booking
    private int duration; // Duration of the current booking
    private long lastUnoccupiedTime; // Timestamp when the room was last unoccupied
    private int totalBookings; // To track total bookings (including occupants)
    private long totalOccupiedTime; // To track total occupied time

    /**
     * Constructor to initialize the room with an ID and capacity.
     * @param roomId Unique identifier for the room
     * @param capacity Maximum capacity of the room
     */
    public Room(int roomId, int capacity) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.occupants = 0; 
        this.observers = new ArrayList<>();
        this.startTime = null; 
        this.duration = 0; 
        this.lastUnoccupiedTime = System.currentTimeMillis(); 
        this.totalBookings = 0; // Initialize total bookings
        this.totalOccupiedTime = 0; // Initialize total occupied time
    }

    // Getter for room ID
    public int getRoomId() {
        return roomId;
    }

    // Setter for room capacity
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // Getter for room capacity
    public int getCapacity() {
        return capacity;
    }

    /**
     * Set the occupancy of the room and notify observers.
     * @param occupants New number of occupants in the room
     */
    public void setOccupancy(int occupants) {
        if (this.occupants != occupants) {
            if (occupants > 0) {
                // Room is being occupied, count this as a booking
                totalOccupiedTime += System.currentTimeMillis() - lastUnoccupiedTime; // Update occupied time
                totalBookings++; // Increment total bookings when adding occupants
            } else {
                // Room is being unoccupied
                lastUnoccupiedTime = System.currentTimeMillis(); // Update unoccupied time
            }
            this.occupants = occupants; // Update the number of occupants
            notifyObservers(); // Notify observers of the change
        }
    }

    // Check if the room is currently occupied
    public boolean isOccupied() {
        return occupants > 0;
    }

    // Get the time the room has been unoccupied
    public long getUnoccupiedTime() {
        return System.currentTimeMillis() - lastUnoccupiedTime; 
    }

    /**
     * Add an observer to be notified of changes in the room.
     * @param observer Observer to be added
     */
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    // Notify all observers of changes
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    /**
     * Book the room for a specified duration.
     * @param startTime Start time of the booking
     * @param duration Duration of the booking in minutes
     */
    public void book(String startTime, int duration) {
        this.startTime = startTime;
        this.duration = duration;
        totalBookings++; // Increment total bookings when room is booked
        System.out.println("Room " + roomId + " booked from " + startTime + " for " + duration + " minutes.");
    }

    // Cancel the current booking and reset occupancy
    public void cancelBooking() {
        this.startTime = null;
        this.duration = 0;
        if (occupants > 0) {
            setOccupancy(0); // Reset occupancy to 0 when booking is canceled
        }
        System.out.println("Booking for Room " + roomId + " cancelled successfully.");
    }

    // Check if the room is currently booked
    public boolean isBooked() {
        return this.startTime != null || occupants > 0; // Either booked or occupied by people
    }

    // Get total number of bookings for the room
    public int getTotalBookings() {
        return totalBookings;
    }

    // Get total occupied time for the room
    public long getTotalOccupiedTime() {
        return totalOccupiedTime;
    }
}
