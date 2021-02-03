package entelect.training.incubator.spring.flight.controller;

import entelect.training.incubator.spring.flight.model.Flight;
import entelect.training.incubator.spring.flight.repository.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("flights")
public class FlightsController {
    
    private final Logger logger = LoggerFactory.getLogger(FlightsController.class);
    
    @Value("${loyalty.discount.flights.selection.futureDays}")
    private int discountedFlightFutureDays;
    
    @Value("${loyalty.discount.flights.limit}")
    private int discountedFlightLimit;
    
    @Value("${loyalty.discount.fares.rate}")
    private float discountedRate;
    
    private final FlightRepository flightRepository;
    
    public FlightsController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    
    @PostMapping
    public ResponseEntity<?> createFlight(@RequestBody Flight flight) {
        logger.info("Processing flight creation request for flight={}", flight);
        
        final Flight savedFlight =  this.flightRepository.save(flight);
        
        logger.trace("Flight created");
        return new ResponseEntity<>(savedFlight, HttpStatus.OK);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<?> getFlightById(@PathVariable Integer id) {
        logger.info("Processing flight search request for flight id={}", id);
        Optional<Flight> flightOptional =  this.flightRepository.findById(id);
        
        if (flightOptional.isPresent()) {
            logger.trace("Found flight: {}", flightOptional.get());
            return new ResponseEntity<>(flightOptional.get(), HttpStatus.OK);
        }
        
        logger.trace("Flight not found");
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/search/findByDepartureTimeWithinDays")
    public ResponseEntity<?> getFlightByDepartureTimeWithinDay(@RequestParam(value = "days") Integer days) {
        logger.info("Processing flight search request for flights departing withing days={}", days);
        
        List<Flight> flights =  this.flightRepository.findByDepartureTimeWithinDays(days);
        
        if (!flights.isEmpty()) {
            logger.trace("Found flights: {}", flights);
            return new ResponseEntity<>(flights, HttpStatus.OK);
        }
        
        logger.trace("No flights found");
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/search/findByDepartureTimeBetween")
    public ResponseEntity<?> getFlightByDepartureTimeBetween(
        @RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        logger.info("Processing flight search request for flights departing between {} and {}", startDate, endDate);
        
        List<Flight> flights =  this.flightRepository.findByDepartureTimeBetween(startDate, endDate);
        
        if (!flights.isEmpty()) {
            logger.trace("Found flights: {}", flights);
            return new ResponseEntity<>(flights, HttpStatus.OK);
        }
        
        logger.trace("No flights found");
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/search/findByOriginAndDestination")
    public ResponseEntity<?> getFlightByOriginAndDestination(
        @RequestParam(value = "origin") String origin,
        @RequestParam(value = "destination") String destination) {
        logger.info("Processing flight search request for flights from {} to {}", origin, destination);
        
        List<Flight> flights =  this.flightRepository.findByOriginAndDestination(origin, destination);
        
        if (!flights.isEmpty()) {
            logger.trace("Found flights: {}", flights);
            return new ResponseEntity<>(flights, HttpStatus.OK);
        }
        
        logger.trace("No flights found");
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/specials")
    public List<Flight> getFlightSpecials() {
        logger.info("Processing flight specials request");
    
        final List<Flight> futureFlights = this.flightRepository.findByDepartureTimeWithinDays(discountedFlightFutureDays);
    
        // pick random flights
        final List<Flight> discountedFlights = futureFlights.stream()
        .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
            Collections.shuffle(collected);
            return collected.stream();
        }))
            .limit(discountedFlightLimit)
            .map(flight -> {
                flight.setSeatCost(flight.getSeatCost() * discountedRate);
                return flight;
            })
            .collect(Collectors.toList());
    
        logger.trace("Flight specials: {}", discountedFlights);
        return discountedFlights;
    }
}
