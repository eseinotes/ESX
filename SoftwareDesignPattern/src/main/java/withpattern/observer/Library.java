package withpattern.observer;

import java.util.ArrayList;

public class Library {

    private ArrayList<Observer> observers;

    public Library() {
        observers = new ArrayList<>();
    }

    // Registra un nuevo observador.
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    // Elimina un observador de la lista.
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Notifica a todos los observadores registrados.
    private void notifyObservers(String message) {

        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    // Cuando se devuelve un libro, se genera una notificación.
    public void returnBook(String bookTitle) {

        System.out.println("Libro devuelto: " + bookTitle);

        notifyObservers(
                "El libro " + bookTitle + " está disponible."
        );
    }
}