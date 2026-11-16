package withpattern.factory;

public class WithFactory {

    public static void run() {

        Notification notification =
                NotificationFactory.createNotification("EMAIL");

        notification.send("Devuelve el libro.");
    }
}