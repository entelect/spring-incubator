package entelect.training.incubator.spring.booking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import entelect.training.incubator.spring.booking.model.Booking;

@Repository
public interface BookingRepository {
	
    Optional<List<Booking>> findByCustomerId(Integer customerId);

    Optional<List<Booking>> findByReferenceNumber(Integer referenceNumber);

}
