package com.paulcruz.convertidordemoneda.main;

import com.google.gson.JsonObject;
import com.paulcruz.convertidordemoneda.api.ExchangeRateService;
import com.paulcruz.convertidordemoneda.model.ExchangeRateData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Obtener tasas de cambio de la API
        ExchangeRateService exchangeRateService = new ExchangeRateService();
        try {
            JsonObject exchangeRatesJson = exchangeRateService.getExchangeRates();
            ExchangeRateData exchangeRateData = new ExchangeRateData();
            exchangeRateData.setBaseCurrency(exchangeRatesJson.get("base_code").getAsString());
            exchangeRateData.setDate(exchangeRatesJson.get("time_last_update_utc").getAsString());
            exchangeRateData.setRates(exchangeRatesJson.getAsJsonObject("conversion_rates"));

            System.out.println("Tasas de cambio obtenidas correctamente.");
            System.out.println("Moneda base: " + exchangeRateData.getBaseCurrency());
            System.out.println("Fecha de actualización: " + exchangeRateData.getDate());

            // Obtener la lista de monedas disponibles
            List<String> currencies = new ArrayList<>(exchangeRateData.getRates().keySet());

            // Ordenar la lista de monedas por la primera letra en orden alfabético
            currencies.sort(Comparator.comparing(s -> s.substring(0, 1)));

            // Mostrar lista de monedas disponibles
            System.out.println("\nMonedas disponibles:");
            int columnCounter = 0;
            for (String currency : currencies) {
                System.out.printf("%-7s", currency);
                columnCounter++;
                if (columnCounter == 6) {
                    System.out.println();
                    columnCounter = 0;
                }
            }
            if (columnCounter != 0) {
                System.out.println();
            }

            String userInput;
            do {
                // El usuario elige la moneda de origen y el monto
                System.out.println("\nIngrese la moneda de origen (ej. USD, EUR, GBP):");
                String fromCurrency = scanner.nextLine().toUpperCase();
                System.out.println("Ingrese el monto a convertir:");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consumir el salto de línea

                // El usuario elige la moneda de destino y recibe el resultado de la conversión
                System.out.println("Ingrese la moneda de destino (ej. USD, EUR, GBP):");
                String toCurrency = scanner.nextLine().toUpperCase();

                double convertedAmount;
                if (exchangeRateData.getRates().containsKey(fromCurrency) && exchangeRateData.getRates().containsKey(toCurrency)) {
                    double fromRate = exchangeRateData.getRates().get(fromCurrency);
                    double toRate = exchangeRateData.getRates().get(toCurrency);
                    convertedAmount = amount * (toRate / fromRate);

                    System.out.println("\n" + amount + " " + fromCurrency + " equivale a " + convertedAmount + " " + toCurrency);
                } else {
                    System.out.println("Las monedas ingresadas no son válidas.");
                }

                // Preguntar al usuario si quiere realizar otra conversión
                System.out.println("\n¿Desea realizar otra conversión? (Ingrese 'EXIT' para salir, de lo contrario presione Enter)");
                userInput = scanner.nextLine().toUpperCase();
            } while (!userInput.equals("EXIT"));

            System.out.println("¡Hasta luego!");

        } catch (IOException e) {
            System.err.println("Error al obtener las tasas de cambio de la API: " + e.getMessage());
        }
    }
}
