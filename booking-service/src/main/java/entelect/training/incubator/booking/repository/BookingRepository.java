package entelect.training.incubator.booking.repository;

import entelect.training.incubator.booking.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

    Optional<Booking> findByCustomerId(Integer customerId);
    
    Optional<Booking> findByReferenceNumber(String referenceNumber);
}
