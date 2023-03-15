package entelect.training.incubator.spring.booking.service;


import entelect.training.incubator.spring.booking.model.Booking;
//import entelect.training.incubator.spring.booking.model.BookingSearchRequest;
//import entelect.training.incubator.spring.booking.model.BookingSearchType;
import entelect.training.incubator.spring.booking.model.Customer;
import entelect.training.incubator.spring.booking.model.Flight;
import entelect.training.incubator.spring.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Supplier;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;


    public BookingService(BookingRepository bookingRepository){
        this.bookingRepository=bookingRepository;
    }

    public Booking createBooking(Booking booking) {
        RestTemplate restTemplate=new RestTemplate();
        String customerRest= "http://localhost:8201/customers/" + booking.getCustomerId();
        String flightRest="http://localhost:8202/flights/" + booking.getFlightId();
        Customer customer= restTemplate.getForObject(customerRest,Customer.class);
        Flight flight=restTemplate.getForObject(flightRest, Flight.class);

        if(customer ==null || flight== null) {
            return null;
        }

        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Integer id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        return bookingOptional.orElse(null);
    }

    public List<Booking> getBooking() {
        Iterable<Booking> bookingIterable = bookingRepository.findAll();

        List<Booking> result = new ArrayList<>();
        bookingIterable.forEach(result::add);

        return result;
    }

//    public Booking searchBookings(BookingSearchRequest searchRequest) {
//        Map<BookingSearchType, Supplier<Optional<Booking>>> searchStrategies = new HashMap<>();
//
//        searchStrategies.put(BookingSearchType.CUSTOMER_SEARCH, () -> bookingRepository.searchBookingByCustomer(searchRequest.getCustomerId()));
//        searchStrategies.put(BookingSearchType.REFERENCE_SEARCH, () -> bookingRepository.searchBookingByReference(searchRequest.getReferenceNumber()));
//
//        Optional<Booking> customerOptional = searchStrategies.get(searchRequest.getSearchType()).get();
//
//        return customerOptional.orElse(null);
//    }
}
