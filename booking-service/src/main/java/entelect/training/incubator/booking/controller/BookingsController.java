package entelect.training.incubator.booking.controller;

import entelect.training.incubator.booking.logic.BookingVerifier;
import entelect.training.incubator.booking.model.Booking;
import entelect.training.incubator.booking.model.BookingSearchRequest;
import entelect.training.incubator.booking.service.BookingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookings")
public class BookingsController {
    private final Logger LOGGER = LoggerFactory.getLogger(BookingsController.class);

    private final BookingsService bookingsService;
    private final BookingVerifier bookingVerifier;

    @Autowired
    public BookingsController(BookingsService bookingsService, BookingVerifier bookingVerifier) {
        this.bookingsService = bookingsService;
        this.bookingVerifier = bookingVerifier;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        boolean verified = bookingVerifier.verifyBooking(booking.getCustomerId(),booking.getFlightId());
        LOGGER.info("Processing booking creation request for customer={}", booking.getCustomerId());
        if (verified){
            final Booking savedBooking = bookingsService.createBooking(booking);
            LOGGER.info("Booking created");
            return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
        } else {
            LOGGER.info("Booking verification failed");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getBookings() {
        LOGGER.info("Fetching all bookings");
        List<Booking> bookings = bookingsService.getBookings();

        if (!bookings.isEmpty()) {
            LOGGER.trace("Found bookings");
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }

        LOGGER.info("No bookings could be found");
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/search")
    public ResponseEntity<?> searchCustomers(@RequestBody BookingSearchRequest searchRequest) {
        LOGGER.info("Processing booking search request for request {}", searchRequest);

        List<Booking> booking = bookingsService.searchBookings(searchRequest);

        if (booking != null) {
            return ResponseEntity.ok(booking);
        }

        LOGGER.trace("Booking not found");
        return ResponseEntity.notFound().build();
    }
}
