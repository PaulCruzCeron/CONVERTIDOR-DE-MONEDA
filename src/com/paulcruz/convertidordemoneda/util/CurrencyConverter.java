package com.paulcruz.convertidordemoneda.util;

public class CurrencyConverter {

    public static double convertCurrency(double amount, double exchangeRate) {
        return amount * exchangeRate;
    }
}
