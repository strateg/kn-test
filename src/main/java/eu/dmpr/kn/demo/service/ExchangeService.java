package eu.dmpr.kn.demo.service;

import static eu.dmpr.kn.demo.model.ExchangeRate.from;
import static eu.dmpr.kn.demo.utils.RateUtils.reduceRates;
import static java.util.stream.Collectors.toMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.dmpr.kn.demo.model.ExchangeRate;
import eu.dmpr.kn.demo.model.ExchangeRates;
import eu.dmpr.kn.demo.model.HistoricalExchangeRates;
import eu.dmpr.kn.demo.utils.RateUtils;
import eu.dmpr.kn.demo.utils.Log;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class ExchangeService {

    @Autowired
    ExchangeServiceCallinglService clientCallService;

    public ExchangeRate latest(String base, String target) {
        ExchangeRates fetchedRates = clientCallService.getLatestExchangeRate(base, target);

        return from(fetchedRates, target);
    }

    public ExchangeRate lowestRateOfCurrentMonth(String base, String target) {
        HistoricalExchangeRates historicalExchangeRates = clientCallService.getHistoricalExchangeRatesForCurrentMonth(base, target);
        Log.info("Historical rates: {}", historicalExchangeRates );

        return reduceRates(base, target, historicalExchangeRates, RateUtils::min);
    }

    public ExchangeRate highestRateOfCurrentMonth(String base, String target) {
        HistoricalExchangeRates historicalExchangeRates = clientCallService.getHistoricalExchangeRatesForCurrentMonth(base, target);

        return reduceRates(base, target, historicalExchangeRates, RateUtils::max);
    }
}
