package com.mycompany.interfaceandabstract;

public class Dog extends Animal {

    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " dice: Guau!");
    }

}
