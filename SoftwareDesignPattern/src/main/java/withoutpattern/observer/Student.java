package withoutpattern.observer;

public class Student {

    private String name;

    public Student(String name) {
        this.name = name;
    }

    public void receiveNotification(String message) {
        System.out.println(name + " recibe: " + message);
    }
}
