package entelect.training.incubator.spring.booking.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.BookingSearchRequest;
import entelect.training.incubator.spring.booking.service.BookingsService;

@RestController
@RequestMapping("bookings")
public class BookingsController {
	
    private final Logger LOGGER = LoggerFactory.getLogger(BookingsController.class);

    private final BookingsService bookingsService;

    public BookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }
    
    @GetMapping("{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer id) {
        LOGGER.info("Processing booking search request for booking id={}", id);
        Booking booking = this.bookingsService.getBookingById(id);

        if (booking != null) {
            LOGGER.trace("Found booking");
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }

        LOGGER.trace("Booking not found");
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/search")
    public ResponseEntity<?> searchCustomers(@RequestBody BookingSearchRequest searchRequest) {
        LOGGER.info("Processing customer search request for request {}", searchRequest);

        List<Booking> bookings = bookingsService.searchBookings(searchRequest);

        if (bookings != null && !bookings.isEmpty()) {
        	LOGGER.info("Found bookings: {}", bookings);
            return ResponseEntity.ok(bookings);
        }

        LOGGER.trace("Customers not found");
        return ResponseEntity.notFound().build();
    }

}
