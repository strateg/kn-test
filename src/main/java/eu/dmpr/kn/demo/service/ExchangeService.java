package eu.dmpr.kn.demo.service;

import static eu.dmpr.kn.demo.model.ExchangeRate.from;
import static eu.dmpr.kn.demo.utils.AppUtils.transformToDateRatePairs;
import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BinaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.dmpr.kn.demo.model.ExchangeRate;
import eu.dmpr.kn.demo.model.ExchangeRates;
import eu.dmpr.kn.demo.model.HistoricalExchangeRates;
import eu.dmpr.kn.demo.utils.AppUtils;
import eu.dmpr.kn.demo.utils.Log;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class ExchangeService {

    @Autowired
    ClientCallService clientCallService;

    public ExchangeRate latest(String base, String target) {
        ExchangeRates fetchedRates = clientCallService.getLatestExchangeRate(base, target);

        return from(fetchedRates, target);
    }

    public ExchangeRate lowestRateOfCurrentMonth(String base, String target) {
        HistoricalExchangeRates historicalExchangeRates = clientCallService.getHistoricalExchangeRatesForCurrentMonth(base, target);
        Log.info("Historical rates: {}", historicalExchangeRates );

        return reduceRates(base, target, historicalExchangeRates, AppUtils::min);
    }

    public ExchangeRate highestRateOfCurrentMonth(String base, String target) {
        HistoricalExchangeRates historicalExchangeRates = clientCallService.getHistoricalExchangeRatesForCurrentMonth(base, target);

        return reduceRates(base, target, historicalExchangeRates, AppUtils::max);
    }

    private ExchangeRate reduceRates(String base, String target, HistoricalExchangeRates historicalExchangeRates,
            BinaryOperator<Map.Entry<String, BigDecimal>> reduceFn) {

        Map.Entry<String, BigDecimal> minRateEntry = transformToDateRatePairs(historicalExchangeRates).entrySet().stream()
                .reduce(reduceFn).get();

        String date = minRateEntry.getKey();
        BigDecimal targetRate = minRateEntry.getValue();

        ExchangeRate lowestExchangeRate = ExchangeRate.builder()
                .baseCurrency(base)
                .targetCurrency(target)
                .rate(targetRate)
                .date(date)
                .build();

        return lowestExchangeRate;
    }
}
