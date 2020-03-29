package eu.dmpr.kn.demo.utils;

import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.util.Map;

import eu.dmpr.kn.demo.model.HistoricalExchangeRates;

public class AppUtils {

    public static Map.Entry<String, BigDecimal> min(Map.Entry<String, BigDecimal> stringBigDecimalEntry, Map.Entry<String, BigDecimal> stringBigDecimalEntry1) {
        BigDecimal value1 = stringBigDecimalEntry.getValue();
        BigDecimal value2 = stringBigDecimalEntry1.getValue();

        return value1.compareTo(value2) > 0 ? stringBigDecimalEntry: stringBigDecimalEntry1;
    }

    public static Map.Entry<String, BigDecimal> max(Map.Entry<String, BigDecimal> stringBigDecimalEntry, Map.Entry<String, BigDecimal> stringBigDecimalEntry1) {
        BigDecimal value1 = stringBigDecimalEntry.getValue();
        BigDecimal value2 = stringBigDecimalEntry1.getValue();

        return value1.compareTo(value2) < 0 ? stringBigDecimalEntry: stringBigDecimalEntry1;
    }

    public static Map<String, BigDecimal> transformToDateRatePairs(HistoricalExchangeRates historicalExchangeRates) {

        Map<String, BigDecimal> historicalRates = historicalExchangeRates.getRates().entrySet().stream()
                .collect(
                        toMap(
                                entry -> entry.getKey(),
                                entry -> entry.getValue().values().stream().findFirst().get()));

        return historicalRates;
    }
}
