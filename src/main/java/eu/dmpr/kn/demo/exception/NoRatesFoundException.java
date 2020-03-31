package eu.dmpr.kn.demo.exception;

public class NoRatesFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Exchange service returned ZERO elements";
    }
}
