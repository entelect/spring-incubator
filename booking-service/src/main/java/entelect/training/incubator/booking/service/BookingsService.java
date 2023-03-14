package entelect.training.incubator.booking.service;

import entelect.training.incubator.booking.model.Booking;
import entelect.training.incubator.booking.model.BookingSearchRequest;
import entelect.training.incubator.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookingsService {
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingsService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(Booking booking) {
        booking.setReferenceNumber(generateReferenceCode());
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookings(){
        Iterable<Booking> bookingIterable = bookingRepository.findAll();

        List<Booking> result = new ArrayList<>();
        bookingIterable.forEach(result::add);

        return result;
    }

    public Booking getBooking(Integer id){
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        return bookingOptional.orElse(null);
    }

    public List<Booking> searchBookings(BookingSearchRequest searchRequest) {
        if (searchRequest.getCustomerId() == null && searchRequest.getReferenceNumber() != null){
            return bookingRepository.findByReferenceNumber(searchRequest.getReferenceNumber()).get();
        } else {
            if (searchRequest.getReferenceNumber() == null && searchRequest.getCustomerId() != null) {
                return bookingRepository.findByCustomerId(searchRequest.getCustomerId()).get();
            }
        }
        return null;
    }

    private String generateReferenceCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        // Generate the first 3 capital letters
        for (int i = 0; i < 3; i++) {
            char ch = (char) (random.nextInt(26) + 'A');
            sb.append(ch);
        }
        // Generate the next 3 numbers
        for (int i = 0; i < 3; i++) {
            int num = random.nextInt(10);
            sb.append(num);
        }
        return sb.toString();
    }
}
