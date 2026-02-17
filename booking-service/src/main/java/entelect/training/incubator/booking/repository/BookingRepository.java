package entelect.training.incubator.booking.repository;

import entelect.training.incubator.booking.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

    Optional<List<Booking>> findByCustomerId(Integer customerId);

    Optional<List<Booking>> findByReferenceNumber(String referenceNumber);
}
