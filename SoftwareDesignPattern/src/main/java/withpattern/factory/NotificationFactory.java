package withpattern.factory;


public class NotificationFactory {

    public static Notification createNotification(String type) {

        if (type.equals("EMAIL")) {
            return new EmailNotification();
        }

        if (type.equals("SMS")) {
            return new SMSNotification();
        }

        throw new IllegalArgumentException("Tipo no válido");
    }
}