package entelect.training.incubator.spring.customer.model;

import lombok.Data;

@Data
public class CustomerSearchRequest {
	
    private SearchType searchType;
    private String firstName;
    private String lastName;
    private String passport;
    private String username;
    
}
