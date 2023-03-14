package entelect.training.incubator.booking.logic;

import entelect.training.incubator.booking.model.Customer;
import entelect.training.incubator.booking.model.Flight;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@NoArgsConstructor
@Service
public class FlightServiceCaller {
    private RestTemplate restTemplate;

    @Autowired
    public FlightServiceCaller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean callFlightSearchById(Integer flightId){
        String url = "http://localhost:8202/flights/" + flightId;
        System.out.println(url);
        //Basic YWRtaW46aXNfYV9saWU=

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic YWRtaW46aXNfYV9saWU=");
        HttpEntity<Flight> entity = new HttpEntity<>(headers);

        ResponseEntity<Flight> response = restTemplate.exchange(url, HttpMethod.GET,entity,Flight.class);

        if (!response.hasBody()){
            System.out.println("Could not find flight with id " + flightId);
            return false;
        }
        System.out.println("Found flight with id " + flightId);
        return true;
    }
}
