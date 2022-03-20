package entelect.training.incubator.spring.booking.model;

import lombok.Data;

@Data
public class BookingSearchRequest {
	
	private SearchType searchType;
    private Integer customerId;
    private String referenceNumber;

}
