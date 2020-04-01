package eu.dmpr.kn.demo.adapter;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import eu.dmpr.kn.demo.exception.RestTemplateResponseErrorHandler;
import eu.dmpr.kn.demo.utils.Log;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Component
public class RestAdapter implements RestOperation {

    @Autowired
    private RestTemplate restTemplate;

    @Nullable
    public <T> T getCurrencyData(URI url, Class<T> responseType) throws RestClientException {
        Log.info("Sending request: {}", url.toString());
        return restTemplate.getForObject(url, responseType);
    }

}
