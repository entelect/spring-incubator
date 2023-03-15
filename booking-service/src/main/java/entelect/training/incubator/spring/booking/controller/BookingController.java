package entelect.training.incubator.spring.booking.controller;


import entelect.training.incubator.spring.booking.model.Booking;
//import entelect.training.incubator.spring.booking.model.BookingSearchRequest;
import entelect.training.incubator.spring.booking.service.BookingService;
import entelect.training.incubator.spring.customer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookings")
public class BookingController {
    private final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        LOGGER.info("Processing booking creation request for booking={}", booking);

        final Booking savedBooking = bookingService.createBooking(booking);

        LOGGER.trace("Booking created");
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

//    @PostMapping
//    public ResponseEntity<?> makeBooking(@RequestBody Booking booking) {
//        LOGGER.info("Processing booking creation request for booking={}", booking);
//
//        final Booking savedBooking = bookingService.makeBooking(booking);
//
//        LOGGER.trace("Booking created");
//        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
//    }

    @GetMapping()
    public ResponseEntity<?> getBooking() {
        LOGGER.info("Fetching all bookings");
        List<Booking> bookings = this.bookingService.getBooking();

        if (!bookings.isEmpty()) {
            LOGGER.trace("Found bookings");
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }

        LOGGER.trace("No bookings found");
        return ResponseEntity.notFound().build();
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        LOGGER.info("Processing customer search request for customer id={}", id);
        Booking booking = this.bookingService.getBookingById(id);

        if (booking != null) {
            LOGGER.trace("Found customer");
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }

        LOGGER.trace("Booking not found");
        return ResponseEntity.notFound().build();
    }


//    @PostMapping("/search")
//    public ResponseEntity<?> searchBookings(@RequestBody BookingSearchRequest searchRequest) {
//        LOGGER.info("Processing booking search request for request {}", searchRequest);
//
//        Booking booking = bookingService.searchBookings(searchRequest);
//
//        if (booking != null) {
//            return ResponseEntity.ok(booking);
//        }
//
//        LOGGER.trace("Booking not found");
//        return ResponseEntity.notFound().build();
//    }
}
