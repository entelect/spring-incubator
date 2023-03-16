package entelect.training.incubator.spring.bookings.repository;

import entelect.training.incubator.spring.bookings.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingsRepository extends CrudRepository<Booking, Integer> {
    Optional<Booking> findById (Integer id);
    Optional<Booking> findByCustomerId (Integer customerId);
    Optional<Booking> findByFlightId (Integer flightId);
    Optional<Booking> findByReferenceNumber (String referenceNumber);
}
