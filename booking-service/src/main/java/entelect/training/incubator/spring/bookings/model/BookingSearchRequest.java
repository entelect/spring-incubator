package entelect.training.incubator.spring.bookings.model;

import lombok.Data;

@Data
public class BookingSearchRequest {
    private SearchType searchType;
    private Integer id;
    private Integer customerId;
    private Integer flightId;
    private String referenceNumber;
}
