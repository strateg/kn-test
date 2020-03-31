package eu.dmpr.kn.demo.service;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.web.util.UriComponentsBuilder;

import eu.dmpr.kn.demo.adapter.RestOperation;
import eu.dmpr.kn.demo.model.ExchangeRates;
import eu.dmpr.kn.demo.model.HistoricalExchangeRates;
import eu.dmpr.kn.demo.utils.Log;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class ExchangeServiceCallinglService {

    private RestOperation restOperation;
    private String BASE_URL;

    ExchangeRates getLatestExchangeRate(String base, String target) {
        URI targetUrl = UriComponentsBuilder
                .fromUriString(BASE_URL)
                .path("/latest")
                .queryParam("base", base)
                .queryParam("symbols", target)
                .build()
                .encode()
                .toUri();

        Log.info("Requesting latest exchange rate for pair ({}/{})", base, target);
        return restOperation.getCurrencyData(targetUrl, ExchangeRates.class);
    }


    HistoricalExchangeRates getHistoricalExchangeRatesForCurrentMonth(String base, String target) {
        LocalDate today = LocalDate.now();
        LocalDate startAtDate = today.withDayOfMonth(1);
        LocalDate endsAtDate = today.plusMonths(1).minusDays(1);
        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/history")
                .queryParam("base", base)
                .queryParam("symbols", target)
                .queryParam("start_at", startAtDate.toString())
                .queryParam("end_at", endsAtDate.toString())
                .build()
                .encode()
                .toUri();

        Log.info("Requesting historical exchange rates data for pair ({}/{}) and for period from {} to {}", base, target, startAtDate, endsAtDate);
        return restOperation.getCurrencyData(targetUrl, HistoricalExchangeRates.class);
    }
}
