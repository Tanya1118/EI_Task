interface Observer {
    void update(Room room);
}

public class OccupancySensor implements Observer {
    @Override
    public void update(Room room) {
        if (room.isOccupied()) {
            System.out.println("OccupancySensor: Room " + room.getRoomId() + " is occupied. AC and lights turned on.");
        } else {
            System.out.println("OccupancySensor: Room " + room.getRoomId() + " is unoccupied. AC and lights are turned off");
        }
    }
}
