package eu.dmpr.kn.demo.adapter;

import java.net.URI;

import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import eu.dmpr.kn.demo.exception.RestTemplateResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class RestAdapter implements RestOperation {

    private RestTemplate restTemplate;

    public RestAdapter() {
        restTemplate  = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
    }

    @Nullable
    public <T> T getCurrencyData(URI url, Class<T> responseType) throws RestClientException {
        return restTemplate.getForObject(url, responseType);
    }

}
