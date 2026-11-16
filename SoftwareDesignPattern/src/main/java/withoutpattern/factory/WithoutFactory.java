package withoutpattern.factory;

public class WithoutFactory {

    public static void run() {

        String type = "EMAIL";

        Notification notification;

        if (type.equals("EMAIL")) {
            notification = new EmailNotification();
        } else {
            notification = new SMSNotification();
        }

        notification.send("Devuelve el libro.");
    }
}