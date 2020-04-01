package eu.dmpr.kn.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import eu.dmpr.kn.demo.adapter.RestAdapter;
import eu.dmpr.kn.demo.exception.RestTemplateResponseErrorHandler;
import eu.dmpr.kn.demo.service.ExchangeServiceCallinglService;

@ComponentScan
@Configuration
public class DemoConfiguration {

    @Value("${exchange_service_url}")
    private String BASE_URL;

    @Bean
    public ExchangeServiceCallinglService exchangeRestCalls(RestAdapter restAdapter) {
        return new ExchangeServiceCallinglService(restAdapter, BASE_URL);
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder,
            RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return builder
                .errorHandler(restTemplateResponseErrorHandler)
                .build();
    }
}
