package entelect.training.incubator.spring.booking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import entelect.training.incubator.spring.booking.model.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {
	
    Optional<List<Booking>> findByCustomerId(Integer customerId);

    Optional<List<Booking>> findByReferenceNumber(String referenceNumber);

}
