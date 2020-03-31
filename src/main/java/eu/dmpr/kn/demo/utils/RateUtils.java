package eu.dmpr.kn.demo.utils;

import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BinaryOperator;

import eu.dmpr.kn.demo.exception.NoRatesFoundException;
import eu.dmpr.kn.demo.model.ExchangeRate;
import eu.dmpr.kn.demo.model.HistoricalExchangeRates;

public class RateUtils {

    public static Map.Entry<String, BigDecimal> min(Map.Entry<String, BigDecimal> rate1, Map.Entry<String, BigDecimal> rate2) {
        BigDecimal value1 = rate1.getValue();
        BigDecimal value2 = rate2.getValue();

        return value1.compareTo(value2) < 0 ? rate1: rate2;
    }

    public static Map.Entry<String, BigDecimal> max(Map.Entry<String, BigDecimal> rate1, Map.Entry<String, BigDecimal> rate2) {
        BigDecimal value1 = rate1.getValue();
        BigDecimal value2 = rate2.getValue();

        return value1.compareTo(value2) > 0 ? rate1: rate2;
    }

    public static Map<String, BigDecimal> transformToDateRatePairs(HistoricalExchangeRates historicalExchangeRates) {

        Map<String, BigDecimal> historicalRates = historicalExchangeRates.getRates().entrySet().stream()
                .collect(toMap( entry -> entry.getKey(),
                                entry -> entry.getValue().values().stream().findFirst().get()));

        return historicalRates;
    }

    public static ExchangeRate reduceRates(String base, String target, HistoricalExchangeRates historicalExchangeRates,
            BinaryOperator<Map.Entry<String, BigDecimal>> reduceFn) {

        Map.Entry<String, BigDecimal> minRateEntry = transformToDateRatePairs(historicalExchangeRates).entrySet().stream()
                .reduce(reduceFn)
                .orElseThrow(() -> new NoRatesFoundException());

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
