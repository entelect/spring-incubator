package entelect.training.incubator.spring.customer.service;

import entelect.training.incubator.spring.customer.model.Customer;
import entelect.training.incubator.spring.customer.model.CustomerSearchRequest;
import entelect.training.incubator.spring.customer.model.SearchType;
import entelect.training.incubator.spring.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;

@Service
public class CustomersService {

    private final CustomerRepository customerRepository;

    public CustomersService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getCustomers() {
        Iterable<Customer> customerIterable = customerRepository.findAll();

        List<Customer> result = new ArrayList<>();
        customerIterable.forEach(result::add);

        return result;
    }

    public Customer getCustomer(Integer id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        // TODO: throw new IllegalArgumentException("Invalid search request"); in stead of returning null
        return customerOptional.orElse(null);
    }

    public Customer searchCustomers(CustomerSearchRequest searchRequest) {
        // TODO: maybe use a switch statement to make it more elegant?
        // TODO: make it enterprise level where the enum owns the strategy and validation (no map wiring thus less repitition and bugs, to add a new search type you just add a new enum with its strategy
        // TODO: Do not return null, throw a domain exception like "CustomerNotFoundException"
        Map<SearchType, Supplier<Optional<Customer>>> searchStrategies = new HashMap<>();

        searchStrategies.put(SearchType.NAME_SEARCH,
                () -> customerRepository.findByFirstNameAndLastName(searchRequest.getFirstName(), searchRequest.getLastName()));

        searchStrategies.put(SearchType.PASSPORT_SEARCH,
                () -> customerRepository.findByPassportNumber(searchRequest.getPassport()));

        searchStrategies.put(SearchType.USER_SEARCH,
                () -> customerRepository.findByUsername(searchRequest.getUsername()));

        Optional<Customer> customerOptional = searchStrategies.get(searchRequest.getSearchType()).get();

        return customerOptional.orElse(null);
    }
}
