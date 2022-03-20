package entelect.training.incubator.spring.customer.repository;

import entelect.training.incubator.spring.customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void whenFindByUsername_thenReturnCustomer() {
        Customer customer = createTestCustomer("john", null, null, null);
        entityManager.persistAndFlush(customer);

        Optional<List<Customer>> found = customerRepository.findByUsername(customer.getUsername());
        assertThat(found).isPresent();
        assertThat(found.get().get(0).getUsername()).isEqualTo(customer.getUsername());
    }

    @Test
    public void whenFindByPassportNumber_thenReturnCustomer() {
        Customer customer = createTestCustomer("john", null, null, "123456789");
        entityManager.persistAndFlush(customer);

        Optional<List<Customer>> found = customerRepository.findByPassportNumber(customer.getPassportNumber());
        assertThat(found).isPresent();
        assertThat(found.get().get(0).getPassportNumber()).isEqualTo(customer.getPassportNumber());
    }

    @Test
    public void whenFindByFirstNameAndLastName_thenReturnCustomer() {
        Customer customer = createTestCustomer("john", "John", "Doe", "123456789");
        entityManager.persistAndFlush(customer);

        Optional<List<Customer>> found = customerRepository.findByFirstNameAndLastName(customer.getFirstName(), customer.getLastName());
        assertThat(found).isPresent();
        assertThat(found.get().get(0).getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(found.get().get(0).getLastName()).isEqualTo(customer.getLastName());
    }

    private Customer createTestCustomer(String username, String firstName, String lastName, String passport) {
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPassportNumber(passport);
        return customer;
    }

}