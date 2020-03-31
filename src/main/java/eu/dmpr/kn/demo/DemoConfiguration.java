package eu.dmpr.kn.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import eu.dmpr.kn.demo.adapter.RestAdapter;
import eu.dmpr.kn.demo.service.ExchangeServiceCallinglService;

@ComponentScan
@Configuration
public class DemoConfiguration {

    @Value("${exchange_service_url}")
    private String BASE_URL;

    @Bean
    public ExchangeServiceCallinglService exchangeRestCalls() {
        return new ExchangeServiceCallinglService(new RestAdapter(), BASE_URL);
    }
}
