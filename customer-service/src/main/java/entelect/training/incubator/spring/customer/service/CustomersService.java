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
        return customerOptional.orElse(null);
    }

    public Customer searchCustomers(CustomerSearchRequest searchRequest) {
        Map<SearchType, Supplier<Optional<Customer>>> searchStrategies = new HashMap<>();

        searchStrategies.put(SearchType.NAME_SEARCH, () -> customerRepository.findByFirstNameAndLastName(searchRequest.getFirstName(), searchRequest.getLastName()));
        searchStrategies.put(SearchType.PASSPORT_SEARCH, () -> customerRepository.findByPassportNumber(searchRequest.getPassport()));
        searchStrategies.put(SearchType.USER_SEARCH, () -> customerRepository.findByUsername(searchRequest.getUsername()));

        Optional<Customer> customerOptional = searchStrategies.get(searchRequest.getSearchType()).get();

        return customerOptional.orElse(null);
    }
    
}
