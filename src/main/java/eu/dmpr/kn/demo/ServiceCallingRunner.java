package eu.dmpr.kn.demo;

import static eu.dmpr.kn.demo.utils.Log.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import eu.dmpr.kn.demo.exception.NoRatesFoundException;
import eu.dmpr.kn.demo.model.ExchangeRate;
import eu.dmpr.kn.demo.service.DemoExchangeService;
import eu.dmpr.kn.demo.utils.Log;

@Component
@Profile("!test")
public class ServiceCallingRunner implements CommandLineRunner {

    @Autowired
    private DemoExchangeService demoExchangeService;
    @Autowired
    private ConfigurableApplicationContext ctx;

    @Override
    public void run(String... args) throws Exception {
        try {

            String baseCurrency = args[0];
            String targetCurrency = args[1];

            ExchangeRate rate = demoExchangeService.latest(baseCurrency, targetCurrency);
            Log.warn("Exchange rate: {}", rate.toString());

            ExchangeRate lowestRate = demoExchangeService.lowestRateOfCurrentMonth(baseCurrency, targetCurrency);
            Log.warn("Lowest exchange rate for current month: {}", lowestRate.toString());

            ExchangeRate highestRate = demoExchangeService.highestRateOfCurrentMonth(baseCurrency, targetCurrency);
            Log.warn("Highest exchange rate for current month: {}", highestRate.toString());

        } catch (IndexOutOfBoundsException e) {
            error("Check application arguments! Example: USD EUR");
        } catch (NoRatesFoundException e) {
            error(e.getMessage());
        } catch (Exception e) {
            error("Error occured ", e.getMessage());
        } finally {
            ctx.close();
        }
    }
}
