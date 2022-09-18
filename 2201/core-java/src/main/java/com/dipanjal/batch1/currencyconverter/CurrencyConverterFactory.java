package com.dipanjal.batch1.currencyconverter;

import java.util.concurrent.ConcurrentHashMap;

public class CurrencyConverterFactory {

    private ConcurrentHashMap<String, CurrencyConverter> ioc = new ConcurrentHashMap<>();

    private CurrencyConverterFactory() {
        //bean registration
        ioc.put("USD", new USDCurrencyConverter());
        ioc.put("INR", new INRCurrencyConverter());
        ioc.put("EUR", new EURCurrencyConverter());
    }

    public CurrencyConverter getConverter(String type){
//        if(type.equals("USD")) {
//            return new USDCurrencyConverter();
//        } else if (type.equals("INR")) {
//            return new INRCurrencyConverter();
//        } else if (type.equals("EUR")) {
//            return new EURCurrencyConverter();
//        }
//        return null;

        return ioc.getOrDefault(type, null);
    }
}
