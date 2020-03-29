package eu.dmpr.kn.demo.service;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.web.util.UriComponentsBuilder;

import eu.dmpr.kn.demo.adapter.RestOperation;
import eu.dmpr.kn.demo.model.ExchangeRates;
import eu.dmpr.kn.demo.model.HistoricalExchangeRates;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class ClientCallService {

    private RestOperation restOperation;
    private String BASE_URL;

    ExchangeRates getLatestExchangeRate(String base, String target) {

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                                            .path("/latest")
                                            .queryParam("base", base)
                                            .queryParam("symbols", target)
                                            .build()
                                            .encode()
                                            .toUri();

        return restOperation.getCurrencyData(targetUrl, ExchangeRates.class);
    }


    HistoricalExchangeRates getHistoricalExchangeRates(String base, String target) {
        LocalDate todaydate = LocalDate.now();
        LocalDate startAtDate = todaydate.withDayOfMonth(1);
        LocalDate endsAtDate = todaydate.plusMonths(1).minusDays(1);
        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                                            .path("/history")
                                            .queryParam("base", base)
                                            .queryParam("symbols", target)
                                            .queryParam("start_at", startAtDate.toString())
                                            .queryParam("end_at", endsAtDate.toString())
                                            .build()
                                            .encode()
                                            .toUri();

        return restOperation.getCurrencyData(targetUrl, HistoricalExchangeRates.class);
    }
}
