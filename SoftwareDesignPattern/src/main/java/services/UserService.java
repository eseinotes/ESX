package services;

/*
 * Servicio encargado de comprobar si un estudiante
 * está autorizado para solicitar préstamos.
 */
public class UserService {

    public boolean validateUser(int studentId) {

        System.out.println("1. Comprobando estudiante...");

        if (studentId <= 0) {
            System.out.println("Estudiante no autorizado.");
            return false;
        }

        System.out.println("Estudiante autorizado.");
        return true;
    }
}