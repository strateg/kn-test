package eu.dmpr.kn.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import eu.dmpr.kn.demo.adapter.RestAdapter;
import eu.dmpr.kn.demo.service.ClientCallService;

@ComponentScan
@Configuration
public class DemoConfiguration {

    @Value("${exchange_service_url}")
    private String BASE_URL;

    @Bean
    public ClientCallService exchangeRestCalls() {
        return new ClientCallService(new RestAdapter(new RestTemplate()), BASE_URL);
    }
}
