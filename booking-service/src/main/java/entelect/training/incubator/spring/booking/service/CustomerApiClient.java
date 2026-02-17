package entelect.training.incubator.spring.booking.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class CustomerApiClient {

    private final RestClient customerRestClient;

    public CustomerApiClient(@Qualifier("customerRestClient") RestClient customerRestClient) {
        this.customerRestClient = customerRestClient;
    }

    public boolean customerExists(Integer customerId) {
        if (customerId == null) {
            return false;
        }

        try {
            customerRestClient.get()
                    .uri("/customers/{id}", customerId)
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
