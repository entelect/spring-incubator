package entelect.training.incubator.booking.logic;

import entelect.training.incubator.booking.model.Customer;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@NoArgsConstructor
@Service
public class CustomerServiceCaller {
    private RestTemplate restTemplate;

    @Autowired
    public CustomerServiceCaller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean callCustomerSearchById(Integer customerId){
        String url = "http://localhost:8201/customers/" + customerId;
        System.out.println(url);
        //Basic YWRtaW46aXNfYV9saWU=

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic YWRtaW46aXNfYV9saWU=");
        HttpEntity<Customer> entity = new HttpEntity<>(headers);

        ResponseEntity<Customer> response = restTemplate.exchange(url, HttpMethod.GET,entity,Customer.class);

        if (!response.hasBody()){
            System.out.println("Could not find customer with id " + customerId);
            return false;
        }
        System.out.println("Found customer with id " + customerId);
        return true;
    }
}
