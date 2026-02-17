package entelect.training.incubator.spring.booking.service;

import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.BookingSearchRequest;
import entelect.training.incubator.spring.booking.model.CustomerResponse;
import entelect.training.incubator.spring.booking.model.FlightResponse;
import entelect.training.incubator.spring.booking.model.SearchType;
import entelect.training.incubator.spring.booking.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Supplier;

@Service
public class BookingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

    // Domain knowledge applied based on Comair Liquidation experience at Insol Tech.
    // What if we want to add more Airlines and their Prefixes? Depending on how granular we want microservices,
    // we would either create an airline-service or add an Enum in the flight-service
    private final String AIRLINE_REFERENCE_PREFIX = "MN";

    private static final String ALPHABET_NUMBERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int RANDOM_SUFFIX_LENGTH = 4;

    private final SecureRandom random = new SecureRandom();

    private final BookingRepository bookingRepository;
    private final CustomerApiClient customerApiClient;
    private final FlightApiClient flightApiClient;
    private final LoyaltyApiClient loyaltyApiClient;

    public BookingService(BookingRepository bookingRepository, CustomerApiClient customerApiClient,
                          FlightApiClient flightApiClient, LoyaltyApiClient loyaltyApiClient) {
        this.bookingRepository = bookingRepository;
        this.customerApiClient = customerApiClient;
        this.flightApiClient = flightApiClient;
        this.loyaltyApiClient = loyaltyApiClient;
    }

    public Booking createBooking(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking is required.");
        }
        if (booking.getCustomerId() == null) {
            throw new IllegalArgumentException("customerId is required.");
        }
        if (booking.getFlightId() == null) {
            throw new IllegalArgumentException("flightId is required.");
        }

        boolean customerExists = customerApiClient.customerExists(booking.getCustomerId());
        if (!customerExists) {
            throw new IllegalArgumentException("Customer not found: " + booking.getCustomerId());
        }

        boolean flightExists = flightApiClient.flightExists(booking.getFlightId());
        if (!flightExists) {
            throw new IllegalArgumentException("Flight not found: " + booking.getFlightId());
        }

        generateAndSetBookingReference(booking);
        Booking savedBooking = bookingRepository.save(booking);

        try {
            CustomerResponse customer = customerApiClient.getCustomer(booking.getCustomerId());
            FlightResponse flight = flightApiClient.getFlight(booking.getFlightId());

            BigDecimal rewardAmount = BigDecimal.valueOf(flight.getSeatCost());
            loyaltyApiClient.captureRewards(customer.getPassportNumber(), rewardAmount);

            LOGGER.info("Loyalty rewards captured for booking {}", savedBooking.getReferenceNumber());
        } catch (Exception e) {
            LOGGER.error("Failed to capture loyalty rewards for booking {}: {}",
                    savedBooking.getReferenceNumber(), e.getMessage(), e);
        }

        return savedBooking;
    }

    public List<Booking> getBookings() {
        Iterable<Booking> bookingIterable = bookingRepository.findAll();

        List<Booking> result = new ArrayList<>();
        bookingIterable.forEach(result::add);

        return result;
    }

    public Booking getBookingById(Integer id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        return bookingOptional.orElse(null);
    }

    public Booking searchBookings(BookingSearchRequest searchRequest) {
        Map<SearchType, Supplier<Optional<Booking>>> searchStrategies = new HashMap<>();

        searchStrategies.put(SearchType.REFERENCE_NUMBER_SEARCH,
                () -> bookingRepository.findByReferenceNumber(searchRequest.getReferenceNumber()));

        searchStrategies.put(SearchType.CUSTOMER_ID_SEARCH,
                () -> bookingRepository.findByCustomerId(searchRequest.getCustomerId()));

        Optional<Booking> bookingOptional = searchStrategies
                .get(searchRequest.getSearchType())
                .get();

        return bookingOptional.orElse(null);
    }

    private void generateAndSetBookingReference(Booking booking) {
        String base = AIRLINE_REFERENCE_PREFIX + booking.getCustomerId() + booking.getFlightId();

        for (int attempt = 0; attempt < 10; attempt++) {
            String candidate = base + generateRandomSuffix(RANDOM_SUFFIX_LENGTH);

            boolean referenceAlreadyExists = bookingRepository.findByReferenceNumber(candidate).isPresent();
            if (!referenceAlreadyExists) {
                booking.setReferenceNumber(candidate);
                return;
            }
        }

        throw new IllegalStateException("Unable to generate unique booking reference number");
    }

    private String generateRandomSuffix(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(ALPHABET_NUMBERS.charAt(random.nextInt(ALPHABET_NUMBERS.length())));
        }
        return stringBuilder.toString();
    }
}
