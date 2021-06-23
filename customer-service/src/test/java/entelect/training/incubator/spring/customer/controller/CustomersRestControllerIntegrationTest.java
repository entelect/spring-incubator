package entelect.training.incubator.spring.customer.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import entelect.training.incubator.spring.customer.CustomerServiceApplication;
import entelect.training.incubator.spring.customer.model.Customer;
import entelect.training.incubator.spring.customer.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CustomerServiceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CustomersRestControllerIntegrationTest {

    private static final String TEST_CUSTOMER_USERNAME = "john";
    private static final String TEST_CUSTOMER_FIRST_NAME = "John";
    private static final String TEST_CUSTOMER_LAST_NAME = "Doe";
    private static final String TEST_CUSTOMER_PASSPORT_NUMBER = "123456789";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName(TEST_CUSTOMER_FIRST_NAME);

        mvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON).content(toJson(customer)));

        List<Customer> found = (List<Customer>) repository.findAll();
        assertThat(found).extracting(Customer::getFirstName).containsOnly(TEST_CUSTOMER_FIRST_NAME);
    }

    @Test
    public void givenCustomers_whenGetCustomerById_thenReturnCustomer() throws Exception {
        createTestCustomer(1);

        mvc.perform(get("/customers/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void givenCustomers_whenGetCustomerByUsername_thenReturnCustomer() throws Exception {
        createTestCustomer();

        mvc.perform(post("/customers/search/findByUsername").contentType(MediaType.APPLICATION_JSON)
                .queryParam("username", TEST_CUSTOMER_USERNAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(TEST_CUSTOMER_USERNAME)));
    }

    @Test
    public void givenCustomers_whenGetCustomerByPassportNumber_thenReturnCustomer() throws Exception {
        createTestCustomer();

        mvc.perform(post("/customers/search/findByPassportNumber").contentType(MediaType.APPLICATION_JSON)
                .queryParam("passportNumber", TEST_CUSTOMER_PASSPORT_NUMBER))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passportNumber", is(TEST_CUSTOMER_PASSPORT_NUMBER)));
    }

    @Test
    public void givenCustomers_whenGetCustomerByFirstNameAndLastName_thenReturnCustomer() throws Exception {
        createTestCustomer();

        mvc.perform(post("/customers/search/findByFirstNameAndLastName").contentType(MediaType.APPLICATION_JSON)
                .queryParam("firstName", TEST_CUSTOMER_FIRST_NAME)
                .queryParam("lastName", TEST_CUSTOMER_LAST_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(TEST_CUSTOMER_FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(TEST_CUSTOMER_LAST_NAME)));
    }

    private void createTestCustomer() {
        createTestCustomer(null);
    }

    private void createTestCustomer(Integer id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setUsername(TEST_CUSTOMER_USERNAME);
        customer.setFirstName(TEST_CUSTOMER_FIRST_NAME);
        customer.setLastName(TEST_CUSTOMER_LAST_NAME);
        customer.setPassportNumber(TEST_CUSTOMER_PASSPORT_NUMBER);
        repository.save(customer);
    }

    private static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}