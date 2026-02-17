package entelect.training.incubator.spring.booking.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class FlightApiClient {
    private final RestClient flightRestClient;

    public FlightApiClient(@Qualifier("flightRestClient") RestClient flightRestClient) {
        this.flightRestClient = flightRestClient;
    }

    public boolean flightExists(Integer flightId) {
        if (flightId == null) {
            return false;
        }

        try {
            flightRestClient.get()
                    .uri("/flights/{id}", flightId)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false; // 404
            }
            throw ex; // other errors to bubble up eg service is down, 500 etc
        }
    }
}
