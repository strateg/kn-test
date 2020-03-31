package eu.dmpr.kn.demo.exception;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (
                response.getStatusCode().series() == CLIENT_ERROR
                        || response.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();

        if (statusCode.series() == HttpStatus.Series.SERVER_ERROR) {
            // handle SERVER_ERROR
        } else if (statusCode.series() == HttpStatus.Series.CLIENT_ERROR) {
            // handle CLIENT_ERROR
            switch (statusCode) {
                case NOT_FOUND:     throw new NotFoundException();
                case BAD_REQUEST:   throw new BadRequestException();
                default: throw new RuntimeException(statusCode.toString());
            }
        }
    }
}
