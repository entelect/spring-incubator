package entelect.training.incubator.spring.flight.repository;

import entelect.training.incubator.spring.flight.model.Flight;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Integer> {
    
    List<Flight> findByDepartureTimeBetween(@Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                            @Param("endDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime endDate);
    
    List<Flight> findByOriginAndDestination(@Param("origin") String origin, @Param("destination") String destination);
    
    @Query(value = "select * from flight f where f.departure_time between NOW() and DATE_ADD(NOW(), INTERVAL :days DAY)", nativeQuery = true)
    List<Flight> findByDepartureTimeWithinDays(@Param("days") Integer days);
}
