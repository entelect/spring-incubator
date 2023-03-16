package entelect.training.incubator.spring.bookings.controller;

import entelect.training.incubator.spring.bookings.model.Booking;
import entelect.training.incubator.spring.bookings.model.BookingSearchRequest;
import entelect.training.incubator.spring.bookings.model.Customer;
import entelect.training.incubator.spring.bookings.model.Flight;
import entelect.training.incubator.spring.bookings.repository.BookingsRepository;
import entelect.training.incubator.spring.bookings.service.BookingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("bookings")
public class BookingsController {
    private final Logger LOGGER = LoggerFactory.getLogger(BookingsController.class);
    private final BookingsService bookingsService;
    private final BookingsRepository bookingsRepository;
    //private final RewardsClient rewardsClient;

    @Autowired
    public BookingsController(BookingsService bookingsService,
                              BookingsRepository bookingsRepository){
        this.bookingsService = bookingsService;
        this.bookingsRepository = bookingsRepository;
        //this.rewardsClient = rewardsClient;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        LOGGER.info("Processing booking creation request for booking={}", booking);
        RestTemplate restTemplate = new RestTemplate();
        String customerEndpoint = "http://localhost:8201/customers/" + booking.getCustomerId();
        String flightEndpoint = "http://localhost:8202/flights/" + booking.getFlightId();

        Customer customer = restTemplate.getForObject(customerEndpoint, Customer.class);
        Flight flight = restTemplate.getForObject(flightEndpoint, Flight.class);

        if (customer == null || flight == null){
            LOGGER.info("Customer or flight does not exist");
            return ResponseEntity.badRequest().build();
        }

        booking.setReferenceNumber(generateReferenceNumber(flight.getFlightNumber()));
        //rewardsClient.captureRewards(customer.getPassportNumber(), flight.getSeatCost());

        final Booking savedBooking = bookingsService.createBooking(booking);

        LOGGER.trace("Booking Created");

        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    @GetMapping ResponseEntity<?> getBookings(){
        LOGGER.info("Fetching all bookings");
        List<Booking> bookings = bookingsService.getBookings();

        if (!bookings.isEmpty()){
            LOGGER.trace("Found bookings");
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }

        LOGGER.info("No booking could be found");
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer id){
        LOGGER.info("Processing booking search request for booking id={}", id);
        Booking booking = this.bookingsService.getBooking(id);

        if (booking != null){
            LOGGER.trace("Found booking");
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }

        LOGGER.trace("Booking not found");
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchBookings(@RequestBody BookingSearchRequest searchRequest) {
        LOGGER.info("Processing booking search for request {}", searchRequest);
        Booking booking = bookingsService.searchBookings(searchRequest);

        if (booking != null){
            LOGGER.trace("Found Booking");
            return ResponseEntity.ok(booking);
        }

        LOGGER.trace("Booking not found");
        return ResponseEntity.notFound().build();

    }

    private String generateReferenceNumber(String flightNumber) {
        Random random = new Random();
        String currentDate = LocalDate.now().toString();
        String[] currentDateSplit = currentDate.split("-");

        String referenceNumber = "" + generateRandomLetter(random);
        referenceNumber += currentDateSplit[2];
        referenceNumber += flightNumber;
        referenceNumber += currentDateSplit[1];
        referenceNumber += "" + generateRandomLetter(random);
        referenceNumber += currentDateSplit[0];

        return referenceNumber;
    }

    private char generateRandomLetter(Random random) {
        return (char) (random.nextInt(26) + 'A');
    }
}