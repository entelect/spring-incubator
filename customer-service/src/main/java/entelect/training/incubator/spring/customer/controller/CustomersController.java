package entelect.training.incubator.spring.customer.controller;

import entelect.training.incubator.spring.customer.model.Customer;
import entelect.training.incubator.spring.customer.model.CustomerSearchRequest;
import entelect.training.incubator.spring.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomersController {

    private final Logger logger = LoggerFactory.getLogger(CustomersController.class);

    private final CustomerService customerService;

    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        logger.info("Processing customer creation request for customer={}", customer);

        final Customer savedCustomer = customerService.createCustomer(customer);

        logger.trace("Customer created");
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getCustomers() {
        logger.info("Fetching all customers");
        List<Customer> customers = customerService.getCustomers();

        if (!customers.isEmpty()) {
            logger.trace("Found customers");
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }

        logger.info("No customers could be found");
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        logger.info("Processing customer search request for customer id={}", id);
        Customer customer = this.customerService.getCustomer(id);

        if (customer != null) {
            logger.trace("Found customer");
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }

        logger.trace("Customer not found");
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchCustomers(@RequestBody CustomerSearchRequest searchRequest) {
        logger.info("Processing customer search request for request {}", searchRequest);

        Customer customer = customerService.searchCustomers(searchRequest);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        }

        logger.trace("Customer not found");
        return ResponseEntity.notFound().build();
    }
}