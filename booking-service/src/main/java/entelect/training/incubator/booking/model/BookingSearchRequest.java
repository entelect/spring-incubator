package entelect.training.incubator.booking.model;

import lombok.Data;

@Data
public class BookingSearchRequest {
    private Integer customerId;
    private String referenceNumber;
}
