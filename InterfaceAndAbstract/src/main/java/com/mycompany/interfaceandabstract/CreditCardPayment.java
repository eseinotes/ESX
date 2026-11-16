package com.mycompany.interfaceandabstract;


import com.mycompany.interfaceandabstract.Payment;


public class CreditCardPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println("Pagando " + amount + " euros con tarjeta.");
    }
}