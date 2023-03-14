package entelect.training.incubator.booking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    private String referenceNumber;

    private Integer customerId;

    private Integer flightId;


}
