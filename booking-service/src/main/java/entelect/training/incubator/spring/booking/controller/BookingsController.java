package entelect.training.incubator.spring.booking.controller;

import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookings")

public class BookingsController {
    private final Logger LOGGER = LoggerFactory.getLogger(BookingsController.class);

    private final BookingService bookingService;

    public BookingsController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestParam int customerId, @RequestParam int flightId){
        LOGGER.info("Processing booking creation request for booking={}", customerId);



        final Booking savedBooking = bookingService.createBooking(customerId, flightId);

        LOGGER.trace("Customer created");
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);

    }
    @GetMapping("{id}")
    public ResponseEntity<?> getBookingBYId(@PathVariable Integer id) {
        LOGGER.info("Processing customer search request for customer id={}", id);
        Booking booking = this.bookingService.getBooking(id);

        if (booking != null) {
            LOGGER.trace("Found booking");
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }

        LOGGER.trace("Booking not found");
        return ResponseEntity.notFound().build();
    }

 @GetMapping("/customer/{id}")
 public List<Booking> getBookingByCustomerId(@PathVariable Integer id) {
     return bookingService.findBookingByCustomerId(id);

 }
    @GetMapping("/customer/{referenceNumber}")
    public List<Booking> getBookingByReferenceNumber(@PathVariable String referenceNumber) {
        return bookingService.findBookingByReferenceNumber(referenceNumber);

    }

}
