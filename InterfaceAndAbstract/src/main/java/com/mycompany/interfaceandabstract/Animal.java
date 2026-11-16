package com.mycompany.interfaceandabstract;

public abstract class Animal {

    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    // Método normal: todos los animales comen.
    public void eat() {
        System.out.println(name + " está comiendo.");
    }

    // Método abstracto: cada animal emite un sonido diferente.
    public abstract void makeSound();

}