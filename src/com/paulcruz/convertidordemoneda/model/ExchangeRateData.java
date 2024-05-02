package com.paulcruz.convertidordemoneda.model;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRateData {
    private String baseCurrency;
    private String date;
    private Map<String, Double> rates;

    public ExchangeRateData() {
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(JsonObject ratesJson) {
        this.rates = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : ratesJson.entrySet()) {
            String currency = entry.getKey();
            double rate = entry.getValue().getAsDouble();
            this.rates.put(currency, rate);
        }
    }

    @Override
    public String toString() {
        return "ExchangeRateData{" +
                "baseCurrency='" + baseCurrency + '\'' +
                ", date='" + date + '\'' +
                ", rates=" + rates +
                '}';
    }
}
