interface Command {
    void execute();
}

public class BookingCommand implements Command {
    private Room room;
    private String startTime;
    private int duration;

    // Modify constructor to include startTime and duration
    public BookingCommand(Room room, String startTime, int duration) {
        this.room = room;
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public void execute() {
        if (!room.isOccupied()) {
            room.book(startTime, duration); // Pass startTime and duration to the book method
        } else {
            System.out.println("Room " + room.getRoomId() + " is already booked.");
        }
    }
}
