package com.mycompany.softwaredesignpattern;

import java.util.Scanner;
import withoutpattern.adapter.WithoutAdapter;
import withoutpattern.decorator.WithoutDecorator;
import withoutpattern.facade.WithoutFacade;
import withoutpattern.factory.WithoutFactory;
import withoutpattern.observer.WithoutObserver;
import withoutpattern.repository.WithoutRepository;
import withoutpattern.singleton.WithoutSingleton;
import withoutpattern.strategy.WithoutStrategy;
import withpattern.adapter.WithAdapter;
import withpattern.decorator.WithDecorator;
import withpattern.facade.WithFacade;
import withpattern.factory.WithFactory;
import withpattern.observer.WithObserver;
import withpattern.repository.WithRepository;
import withpattern.singleton.WithSingleton;
import withpattern.strategy.WithStrategy;

public class SoftwareDesignPattern {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int option = -1;

        while (option != 0) {

            showMenu();

            System.out.print("Selecciona una opción: ");

            // Leemos una línea para evitar errores al introducir texto.
            String input = scanner.nextLine();

            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Debes introducir un número.");
                continue;
            }

            switch (option) {

                // PATRÓN SINGLETON
                case 1:
                    WithoutSingleton.run();
                    break;

                case 2:
                    WithSingleton.run();
                    break;

                // PATRÓN FACTORY METHOD
                case 3:
                    WithoutFactory.run();
                    break;

                case 4:
                    WithFactory.run();
                    break;

                // PATRÓN ADAPTER
                case 5:
                    WithoutAdapter.run();
                    break;

                case 6:
                    WithAdapter.run();
                    break;

                // PATRÓN FACADE
                case 7:
                    WithoutFacade.run();
                    break;

                case 8:
                    WithFacade.run();
                    break;

                // PATRÓN STRATEGY
                case 9:
                    WithoutStrategy.run();
                    break;

                case 10:
                    WithStrategy.run();
                    break;

                // PATRÓN OBSERVER
                case 11:
                    WithoutObserver.run();
                    break;

                case 12:
                    WithObserver.run();
                    break;

                // PATRÓN REPOSITORY
                case 13:
                    WithoutRepository.run();
                    break;

                case 14:
                    WithRepository.run();
                    break;

                // PATRÓN DECORATOR
                case 15:
                    WithoutDecorator.run();
                    break;

                case 16:
                    WithDecorator.run();
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }

        scanner.close();
    }

    // Muestra las opciones disponibles.
    private static void showMenu() {

        System.out.println("\n========================================");
        System.out.println("         SOFTWARE DESIGN PATTERNS");
        System.out.println("========================================");

        System.out.println("\nSINGLETON");
        System.out.println("1. Sin Singleton");
        System.out.println("2. Con Singleton");

        System.out.println("\nFACTORY METHOD");
        System.out.println("3. Sin Factory Method");
        System.out.println("4. Con Factory Method");

        System.out.println("\nADAPTER");
        System.out.println("5. Sin Adapter");
        System.out.println("6. Con Adapter");

        System.out.println("\nFACADE");
        System.out.println("7. Sin Facade");
        System.out.println("8. Con Facade");

        System.out.println("\nSTRATEGY");
        System.out.println("9. Sin Strategy");
        System.out.println("10. Con Strategy");

        System.out.println("\nOBSERVER");
        System.out.println("11. Sin Observer");
        System.out.println("12. Con Observer");

        System.out.println("\nREPOSITORY");
        System.out.println("13. Sin Repository");
        System.out.println("14. Con Repository");

        System.out.println("\nDECORATOR");
        System.out.println("15. Sin Decorator");
        System.out.println("16. Con Decorator");

        System.out.println("\n0. Salir");
        System.out.println("========================================");
    }
}
