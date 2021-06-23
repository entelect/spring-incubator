package entelect.training.incubator.spring.customer.controller;

import entelect.training.incubator.spring.customer.model.Customer;
import entelect.training.incubator.spring.customer.model.CustomerSearchRequest;
import entelect.training.incubator.spring.customer.model.SearchType;
import entelect.training.incubator.spring.customer.service.CustomersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomersController {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomersController.class);

    private final CustomersService customersService;

    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        LOGGER.info("Processing customer creation request for customer={}", customer);

        final Customer savedCustomer = customersService.createCustomer(customer);

        LOGGER.trace("Customer created");
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getCustomers() {
        LOGGER.info("Fetching all customers");
        List<Customer> customers = customersService.getCustomers();

        if (!customers.isEmpty()) {
            LOGGER.trace("Found customers");
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }

        LOGGER.info("No customers could be found");
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        LOGGER.info("Processing customer search request for customer id={}", id);
        Customer customer = this.customersService.getCustomer(id);

        if (customer != null) {
            LOGGER.trace("Found customer");
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }

        LOGGER.trace("Customer not found");
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "search/{searchType}")
    public ResponseEntity<?> searchCustomers(@PathVariable("searchType") String searchTypeString,
                                             @RequestParam(required = false) String username,
                                             @RequestParam(required = false) String firstName,
                                             @RequestParam(required = false) String lastName,
                                             @RequestParam(required = false) String passportNumber) {

        CustomerSearchRequest searchRequest = new CustomerSearchRequest();
        SearchType searchType = SearchType.fromString(searchTypeString);
        searchRequest.setSearchType(searchType);

        if (username != null) searchRequest.setUsername(username);
        if (firstName != null) searchRequest.setFirstName(firstName);
        if (lastName != null) searchRequest.setLastName(lastName);
        if (passportNumber != null) searchRequest.setPassport(passportNumber);

        LOGGER.info("Processing customer search request type {} for request {}", searchType, searchRequest);
        searchRequest.setSearchType(searchType);
        Customer customer = customersService.searchCustomers(searchRequest);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        }

        LOGGER.trace("Customer not found");
        return ResponseEntity.notFound().build();
    }
}