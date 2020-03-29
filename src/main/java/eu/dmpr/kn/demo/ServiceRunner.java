package eu.dmpr.kn.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import eu.dmpr.kn.demo.model.ExchangeRate;
import eu.dmpr.kn.demo.service.ExchangeService;
import eu.dmpr.kn.demo.utils.Log;

@Component
public class ServiceRunner implements CommandLineRunner {

    @Autowired
    private ExchangeService exchangeService;

    @Override
    public void run(String... args) throws Exception {
        String baseCurrency = args[0];
        String targetCurrency = args[1];
        ExchangeRate rate = exchangeService.latest(baseCurrency, targetCurrency);
        Log.info("Exchange rate: {}", rate.toString());

        ExchangeRate rates = exchangeService.lowestRateOfCurrentMonth(baseCurrency, targetCurrency);
        Log.info("Exchange rates for current month: {}", rates.toString());

        ExchangeRate highestRate = exchangeService.highestRateOfCurrentMonth(baseCurrency, targetCurrency);
        Log.info("Exchange rates for current month: {}", highestRate.toString());


    }
}
