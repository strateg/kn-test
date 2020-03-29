package eu.dmpr.kn.demo.model;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRate {

    private String baseCurrency;
    private String targetCurrency;
    private BigDecimal rate;
    private String date;

    public static ExchangeRate from(ExchangeRates rates, String targetCurrency) {
        Map.Entry<String, BigDecimal> rate = rates.getRates().entrySet().stream()
                .filter(entry -> targetCurrency.compareTo(entry.getKey()) == 0)
                .findFirst()
                .get();

        return ExchangeRate.builder()
                .baseCurrency(rates.getBase())
                .targetCurrency(rate.getKey())
                .rate(rate.getValue())
                .date(rates.getDate())
                .build();
    }
}
