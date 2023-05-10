package entelect.training.incubator.spring.flight.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Flight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String flightNumber;

    private String origin;

    private String destination;
    
    private LocalDateTime departureTime;
    
    private LocalDateTime arrivalTime;
    
    private Integer seatsAvailable;
    
    private Float seatCost;

}
