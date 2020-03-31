package eu.dmpr.kn.demo;

import static eu.dmpr.kn.demo.utils.Log.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import eu.dmpr.kn.demo.exception.BadRequestException;
import eu.dmpr.kn.demo.exception.NoRatesFoundException;
import eu.dmpr.kn.demo.model.ExchangeRate;
import eu.dmpr.kn.demo.service.ExchangeService;
import eu.dmpr.kn.demo.utils.Log;

@Component
public class ServiceCallingRunner implements CommandLineRunner {

    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private ConfigurableApplicationContext ctx;

    @Override
    public void run(String... args) throws Exception {
        try {

            String baseCurrency = args[0];
            String targetCurrency = args[1];


            ExchangeRate rate = exchangeService.latest(baseCurrency, targetCurrency);
            Log.info("Exchange rate: {}", rate.toString());

            ExchangeRate lowestRate = exchangeService.lowestRateOfCurrentMonth(baseCurrency, targetCurrency);
            Log.info("Lowest exchange rate for current month: {}", lowestRate.toString());

            ExchangeRate highestRate = exchangeService.highestRateOfCurrentMonth(baseCurrency, targetCurrency);
            Log.info("Highest exchange rate for current month: {}", highestRate.toString());

        } catch (BadRequestException e) {
            error("Bad Request ");
        } catch (IndexOutOfBoundsException e) {
            error("Check application arguments! Example: USD EUR");
        } catch (NoRatesFoundException e) {
            error(e.getMessage());
        } catch (Exception e) {
            error("Error occured ", e);
        } finally {
            ctx.close();
        }
    }
}
