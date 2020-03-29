package eu.dmpr.kn.demo.adapter;

import java.net.URI;

import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class RestAdapter implements RestOperation {

    private RestTemplate restTemplate;

    @Nullable
    public <T> T getCurrencyData(URI url, Class<T> responseType) throws RestClientException {

        return restTemplate.getForObject(url, responseType);
    }

}
