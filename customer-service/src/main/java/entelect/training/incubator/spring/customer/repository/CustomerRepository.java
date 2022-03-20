package entelect.training.incubator.spring.customer.repository;

import entelect.training.incubator.spring.customer.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    
    Optional<List<Customer>> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<List<Customer>> findByPassportNumber(String passportNumber);
    
    Optional<List<Customer>> findByUsername(String username);
    
}
