// Notification Interface
interface Notification {
    void notifyUser(String message);
}

// Concrete Class for Email Notification
class EmailNotification implements Notification {
    @Override
    public void notifyUser(String message) {
        System.out.println("Email Notification: " + message);
    }
}

// Concrete Class for SMS Notification
class SMSNotification implements Notification {
    @Override
    public void notifyUser(String message) {
        System.out.println("SMS Notification: " + message);
    }
}

// Concrete Class for Push Notification
class PushNotification implements Notification {
    @Override
    public void notifyUser(String message) {
        System.out.println("Push Notification: " + message);
    }
}

// Concrete Class for Instagram Notification
class InstagramNotification implements Notification {
    @Override
    public void notifyUser(String message) {
        System.out.println("Instagram Notification: " + message);
    }
}

// Concrete Class for WhatsApp Notification
class WhatsAppNotification implements Notification {
    @Override
    public void notifyUser(String message) {
        System.out.println("WhatsApp Notification: " + message);
    }
}

// Notification Factory
class NotificationFactory {
    public static Notification createNotification(String type) {
        if (type == null) {
            return null;
        }
        switch (type.toUpperCase()) {
            case "EMAIL":
                return new EmailNotification();
            case "SMS":
                return new SMSNotification();
            case "PUSH":
                return new PushNotification();
            case "INSTAGRAM":
                return new InstagramNotification();
            case "WHATSAPP":
                return new WhatsAppNotification();
            default:
                return null;
        }
    }
}

// Client code
public class NotificationSystemExample {
    public static void main(String[] args) {
        Notification email = NotificationFactory.createNotification("EMAIL");
        email.notifyUser("You have a new email!");

        Notification sms = NotificationFactory.createNotification("SMS");
        sms.notifyUser("You have a new SMS!");

        Notification push = NotificationFactory.createNotification("PUSH");
        push.notifyUser("You have a new push notification!");

        Notification instagram = NotificationFactory.createNotification("INSTAGRAM");
        instagram.notifyUser("You have a new Instagram message!");

        Notification whatsapp = NotificationFactory.createNotification("WHATSAPP");
        whatsapp.notifyUser("You have a new WhatsApp message!");
    }
}
