// Command Interface
interface Command {
    void execute();
}

// Concrete Command: Power On Command
class PowerOnCommand implements Command {
    private Device device;

    public PowerOnCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.powerOn();
    }
}

// Concrete Command: Power Off Command
class PowerOffCommand implements Command {
    private Device device;

    public PowerOffCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.powerOff();
    }
}

// Concrete Command: Volume Up Command
class VolumeUpCommand implements Command {
    private Device device;

    public VolumeUpCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.volumeUp();
    }
}

// Concrete Command: Volume Down Command
class VolumeDownCommand implements Command {
    private Device device;

    public VolumeDownCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.volumeDown();
    }
}

// Concrete Command: Change Channel Command
// Concrete Command: Change Channel Command
class ChangeChannelCommand implements Command {
    private Device device;
    private int channel;

    public ChangeChannelCommand(Device device, int channel) {
        this.device = device;
        this.channel = channel;
    }

    @Override
    public void execute() {
        device.changeChannel(channel);
    }

    // Getter for the device
    public Device getDevice() {
        return this.device;
    }
}


// Receiver Interface (All devices implement this)
interface Device {
    void powerOn();
    void powerOff();
    void volumeUp();
    void volumeDown();
    void changeChannel(int channel);
}

// Receiver: TV
class TV implements Device {
    private int volume = 10;
    private int channel = 1;

    @Override
    public void powerOn() {
        System.out.println("TV is now ON");
    }

    @Override
    public void powerOff() {
        System.out.println("TV is now OFF");
    }

    @Override
    public void volumeUp() {
        volume++;
        System.out.println("TV Volume is now " + volume);
    }

    @Override
    public void volumeDown() {
        volume--;
        System.out.println("TV Volume is now " + volume);
    }

    @Override
    public void changeChannel(int channel) {
        this.channel = channel;
        System.out.println("TV Channel changed to " + channel);
    }
}

// Receiver: Sound System
class SoundSystem implements Device {
    private int volume = 15;

    @Override
    public void powerOn() {
        System.out.println("Sound System is now ON");
    }

    @Override
    public void powerOff() {
        System.out.println("Sound System is now OFF");
    }

    @Override
    public void volumeUp() {
        volume++;
        System.out.println("Sound System Volume is now " + volume);
    }

    @Override
    public void volumeDown() {
        volume--;
        System.out.println("Sound System Volume is now " + volume);
    }

    @Override
    public void changeChannel(int channel) {
        // Not applicable for Sound System
        System.out.println("Sound System does not support changing channels.");
    }
}

// Receiver: DVD Player
class DVDPlayer implements Device {
    @Override
    public void powerOn() {
        System.out.println("DVD Player is now ON");
    }

    @Override
    public void powerOff() {
        System.out.println("DVD Player is now OFF");
    }

    @Override
    public void volumeUp() {
        // Not applicable for DVD Player
        System.out.println("DVD Player does not support volume control.");
    }

    @Override
    public void volumeDown() {
        // Not applicable for DVD Player
        System.out.println("DVD Player does not support volume control.");
    }

    @Override
    public void changeChannel(int channel) {
        // Not applicable for DVD Player
        System.out.println("DVD Player does not support changing channels.");
    }
}

// Invoker: Remote Control
// Invoker: Remote Control
class RemoteControl {
    private Command powerOnCommand;
    private Command powerOffCommand;
    private Command volumeUpCommand;
    private Command volumeDownCommand;
    private Command changeChannelCommand;

    public void setPowerOnCommand(Command powerOnCommand) {
        this.powerOnCommand = powerOnCommand;
    }

    public void setPowerOffCommand(Command powerOffCommand) {
        this.powerOffCommand = powerOffCommand;
    }

    public void setVolumeUpCommand(Command volumeUpCommand) {
        this.volumeUpCommand = volumeUpCommand;
    }

    public void setVolumeDownCommand(Command volumeDownCommand) {
        this.volumeDownCommand = volumeDownCommand;
    }

    public void setChangeChannelCommand(Command changeChannelCommand) {
        this.changeChannelCommand = changeChannelCommand;
    }

    public void pressPowerOn() {
        powerOnCommand.execute();
    }

    public void pressPowerOff() {
        powerOffCommand.execute();
    }

    public void pressVolumeUp() {
        volumeUpCommand.execute();
    }

    public void pressVolumeDown() {
        volumeDownCommand.execute();
    }

    public void pressChangeChannel(int channel) {
        // Use the getter to access the device from the current change channel command
        changeChannelCommand = new ChangeChannelCommand(((ChangeChannelCommand) changeChannelCommand).getDevice(), channel);
        changeChannelCommand.execute();
    }
}


// Main class to demonstrate the Command Pattern
public class CommandPatternExample {
    public static void main(String[] args) {
        // Create devices
        Device tv = new TV();
        Device soundSystem = new SoundSystem();
        Device dvdPlayer = new DVDPlayer();

        // Create remote control
        RemoteControl remoteControl = new RemoteControl();

        // Set TV Commands
        remoteControl.setPowerOnCommand(new PowerOnCommand(tv));
        remoteControl.setPowerOffCommand(new PowerOffCommand(tv));
        remoteControl.setVolumeUpCommand(new VolumeUpCommand(tv));
        remoteControl.setVolumeDownCommand(new VolumeDownCommand(tv));
        remoteControl.setChangeChannelCommand(new ChangeChannelCommand(tv, 1));

        // Interact with the TV
        remoteControl.pressPowerOn();  // TV is now ON
        remoteControl.pressVolumeUp(); // TV Volume is now 11
        remoteControl.pressChangeChannel(5); // TV Channel changed to 5
        remoteControl.pressPowerOff(); // TV is now OFF

        System.out.println("--- Switching to Sound System ---");

        // Set Sound System Commands
        remoteControl.setPowerOnCommand(new PowerOnCommand(soundSystem));
        remoteControl.setPowerOffCommand(new PowerOffCommand(soundSystem));
        remoteControl.setVolumeUpCommand(new VolumeUpCommand(soundSystem));
        remoteControl.setVolumeDownCommand(new VolumeDownCommand(soundSystem));

        // Interact with the Sound System
        remoteControl.pressPowerOn();  // Sound System is now ON
        remoteControl.pressVolumeUp(); // Sound System Volume is now 16
        remoteControl.pressPowerOff(); // Sound System is now OFF

        System.out.println("--- Switching to DVD Player ---");

        // Set DVD Player Commands
        remoteControl.setPowerOnCommand(new PowerOnCommand(dvdPlayer));
        remoteControl.setPowerOffCommand(new PowerOffCommand(dvdPlayer));

        // Interact with the DVD Player
        remoteControl.pressPowerOn();  // DVD Player is now ON
        remoteControl.pressPowerOff(); // DVD Player is now OFF
    }
}
