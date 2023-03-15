package entelect.training.incubator.spring.booking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer customerId;

    private Integer flightId;

    private String referenceNumber;

    public Booking(Integer customerId, Integer flightId, String referenceNumber) {
        this.customerId = customerId;
        this.flightId = flightId;
        this.referenceNumber = referenceNumber;
    }
}
