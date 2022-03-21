package entelect.training.incubator.spring.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import entelect.training.incubator.spring.booking.model.Customer;

@FeignClient(value = "customer-client", url = "https://localhost:8201/")
public interface CustomerClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/customers/{customerId}", produces = "application/json")
    Customer getCustomerById(@PathVariable("customerId") Long customerId);

}
