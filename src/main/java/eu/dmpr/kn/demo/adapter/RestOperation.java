package eu.dmpr.kn.demo.adapter;

import java.net.URI;

import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;

public interface RestOperation {

    @Nullable
    <T> T getCurrencyData(URI url, Class<T> responseType) throws RestClientException;
    
}
