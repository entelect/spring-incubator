package entelect.training.incubator.booking.service;

import entelect.training.incubator.booking.model.Booking;
import entelect.training.incubator.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingsService {
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingsService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookings(){
        Iterable<Booking> bookingIterable = bookingRepository.findAll();

        List<Booking> result = new ArrayList<>();
        bookingIterable.forEach(result::add);

        return result;
    }
}
