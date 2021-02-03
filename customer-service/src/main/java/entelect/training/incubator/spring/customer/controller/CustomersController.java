package entelect.training.incubator.spring.customer.controller;

import entelect.training.incubator.spring.customer.model.Customer;
import entelect.training.incubator.spring.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("customers")
public class CustomersController {
    
    private final Logger logger = LoggerFactory.getLogger(CustomersController.class);
    
    private final CustomerRepository customerRepository;
    
    public CustomersController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        logger.info("Processing customer creation request for customer={}", customer);
        
        final Customer savedCustomer =  this.customerRepository.save(customer);
        
        logger.trace("Customer created");
        return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        logger.info("Processing customer search request for customer id={}", id);
        Optional<Customer> customerOptional =  this.customerRepository.findById(id);
        
        if (customerOptional.isPresent()) {
            logger.trace("Found customer: {}", customerOptional.get());
            return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
        }
    
        logger.trace("Customer not found");
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/search/findByFirstNameAndLastName")
    public ResponseEntity<?> getCustomerByFirstNameAndLastName(
        @RequestParam(value = "firstname") String firstName,
        @RequestParam(value = "lastname") String lastName) {
        logger.info("Processing customer search request for firstname={}, lastname={}", firstName, lastName);
    
        Optional<Customer> customerOptional =  this.customerRepository.findByFirstNameAndLastName(firstName, lastName);
        
        if (customerOptional.isPresent()) {
            logger.trace("Found customer: {}", customerOptional.get());
            return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
        }
    
        logger.trace("Customer not found");
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/search/findByPassportNumber")
    public ResponseEntity<?> getCustomerByPassportNumber(
        @RequestParam(value = "passport") String passportNumber) {
        logger.info("Processing customer search request for passport={}", passportNumber);
    
        Optional<Customer> customerOptional =  this.customerRepository.findByPassportNumber(passportNumber);
        
        if (customerOptional.isPresent()) {
            logger.trace("Found customer: {}", customerOptional.get());
            return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
        }
    
        logger.trace("Customer not found");
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/search/findByUsername")
    public ResponseEntity<?> getCustomerByUsername(
        @RequestParam(value = "username") String username) {
        logger.info("Processing customer search request for username={}", username);
    
        Optional<Customer> customerOptional =  this.customerRepository.findByUsername(username);
        
        if (customerOptional.isPresent()) {
            logger.trace("Found customer: {}", customerOptional.get());
            return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
        }
    
        logger.trace("Customer not found");
        return ResponseEntity.notFound().build();
    }
    
}