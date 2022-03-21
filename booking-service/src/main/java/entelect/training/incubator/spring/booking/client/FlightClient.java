package entelect.training.incubator.spring.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import entelect.training.incubator.spring.booking.model.Flight;

@FeignClient(value = "flight-client", url = "localhost:8202")
public interface FlightClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/flights/{flightId}", produces = "application/json")
    public Flight getFlightById(@PathVariable("flightId") Integer flightId);

}
