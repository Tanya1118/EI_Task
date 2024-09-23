import java.util.HashMap;
import java.util.Map;

/**
 * OfficeConfiguration class manages the configuration of rooms in the office.
 * It follows the Singleton design pattern to ensure only one instance exists.
 */
public class OfficeConfiguration {
    private static OfficeConfiguration instance; // Single instance of OfficeConfiguration
    private Map<Integer, Room> rooms; // Map to store rooms by their IDs

    private OfficeConfiguration() {
        rooms = new HashMap<>(); // Initialize the map
    }

    /**
     * Get the single instance of OfficeConfiguration.
     * @return Instance of OfficeConfiguration
     */
    public static OfficeConfiguration getInstance() {
        if (instance == null) {
            instance = new OfficeConfiguration();
        }
        return instance;
    }

    /**
     * Add a room to the office configuration.
     * @param roomId Unique identifier for the room
     * @param capacity Maximum capacity of the room
     */
    public void addRoom(int roomId, int capacity) {
        rooms.put(roomId, new Room(roomId, capacity)); // Create and add the room
    }

    /**
     * Get a room by its ID.
     * @param roomId Unique identifier for the room
     * @return Room object if found, otherwise null
     */
    public Room getRoom(int roomId) {
        return rooms.get(roomId); // Retrieve the room from the map
    }
}
