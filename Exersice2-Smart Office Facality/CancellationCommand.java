public class CancellationCommand implements Command {
    private Room room;

    public CancellationCommand(Room room) {
        this.room = room;
    }

    @Override
    public void execute() {
        if (room.isOccupied()) {
            room.cancelBooking();
        } else {
            System.out.println("Room " + room.getRoomId() + " is not booked.");
        }
    }
}
