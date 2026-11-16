package com.mycompany.interfaceandabstract;


public class InterfaceAndAbstract {

    public static void main(String[] args) {

        Payment payment1 = new CreditCardPayment();
        Payment payment2 = new PayPalPayment();

        payment1.pay(100);
        payment2.pay(50);
        
        
        Dog dog = new Dog("Toby");
        Cat cat = new Cat("Michi");

        dog.eat();
        dog.makeSound();

        cat.eat();
        cat.makeSound();
        
        
        // con polimorfismo
        Animal animal1 = new Dog("Toby");
        Animal animal2 = new Cat("Michi");

        animal1.makeSound();
        animal2.makeSound();
        

    }
}
