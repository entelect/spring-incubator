package entelect.training.incubator.spring.booking.repository;

import entelect.training.incubator.spring.booking.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {
    List<Booking> findByCustomerId(int customerId);
    Optional<Booking> findByFlightId(int flightId);

    List<Booking> findBookingByReferenceNumber(String referenceNumber);

}
