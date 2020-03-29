package eu.dmpr.kn.demo.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeRate {

    private String baseCurrency;
    private String targetCurrency;
    private BigDecimal rate;
    private String date;
}
