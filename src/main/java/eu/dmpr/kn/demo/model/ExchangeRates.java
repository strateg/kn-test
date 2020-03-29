package eu.dmpr.kn.demo.model;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

@Data
public class ExchangeRates {

    private String base;
    private String date;
    private Map<String, BigDecimal> rates;

}
