package entelect.training.incubator.spring.booking.service;

import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(int customerId , int flightId) {
        Booking booking=new Booking(customerId,flightId,"dvefhjsdbc");

        return bookingRepository.save(booking);
    }


    public Booking getBooking(Integer id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        return bookingOptional.orElse(null);
    }

    public List<Booking> findBookingByCustomerId(Integer id){
        return  bookingRepository.findByCustomerId(id);
    }
    public List<Booking> findBookingByReferenceNumber(String referenceNumber){
        return  bookingRepository.findBookingByReferenceNumber(referenceNumber);
    }
}