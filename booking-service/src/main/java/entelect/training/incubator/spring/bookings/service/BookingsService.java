package entelect.training.incubator.spring.bookings.service;

import entelect.training.incubator.spring.bookings.model.Booking;
import entelect.training.incubator.spring.bookings.model.BookingSearchRequest;
import entelect.training.incubator.spring.bookings.model.SearchType;
import entelect.training.incubator.spring.bookings.repository.BookingsRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;

@Service
public class BookingsService {
    private final BookingsRepository bookingsRepository;

    public BookingsService(BookingsRepository bookingsRepository){
        this.bookingsRepository = bookingsRepository;
    }

    public Booking createBooking(Booking booking){
        return bookingsRepository.save(booking);
    }

    public List<Booking> getBookings(){
        Iterable<Booking> bookingIterable = bookingsRepository.findAll();

        List<Booking> result = new ArrayList<>();
        bookingIterable.forEach(result::add);

        return result;
    }

    public Booking getBooking(Integer id){
        Optional<Booking> bookingOptional = bookingsRepository.findById(id);
        return bookingOptional.orElse(null);
    }

    public Booking searchBookings(BookingSearchRequest searchRequest) {
        Map<SearchType, Supplier<Optional<Booking>>> searchStrategies = new HashMap<>();

        searchStrategies.put(SearchType.CUSTOMER_ID_SEARCH, () -> bookingsRepository.findByCustomerId(searchRequest.getCustomerId()));
        searchStrategies.put(SearchType.FLIGHT_ID_SEARCH, () -> bookingsRepository.findByFlightId(searchRequest.getFlightId()));
        searchStrategies.put(SearchType.REFERENCE_NUMBER_SEARCH, () -> bookingsRepository.findByReferenceNumber(searchRequest.getReferenceNumber()));

        Optional<Booking> bookingOptional = searchStrategies.get(searchRequest.getSearchType()).get();

        return bookingOptional.orElse(null);
    }
}
