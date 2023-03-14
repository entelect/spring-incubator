package entelect.training.incubator.booking.logic;

import entelect.training.incubator.booking.controller.BookingsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookingVerifier {
    private final Logger LOGGER = LoggerFactory.getLogger(BookingVerifier.class);
    private final CustomerServiceCaller customerServiceCaller;
    private final FlightServiceCaller flightServiceCaller;

    @Autowired
    public BookingVerifier(CustomerServiceCaller customerServiceCaller, FlightServiceCaller flightServiceCaller) {
        this.customerServiceCaller = customerServiceCaller;
        this.flightServiceCaller = flightServiceCaller;
    }

    public boolean verifyBooking(Integer customerId, Integer flightId){
        boolean CustomerIsFound = customerServiceCaller.callCustomerSearchById(customerId);
        LOGGER.info("CustomerIsFound : " + CustomerIsFound);
        boolean FlightIsFound = flightServiceCaller.callFlightSearchById(flightId);
        LOGGER.info("FlightIsFound : " + FlightIsFound);
        if (CustomerIsFound && FlightIsFound){
            return true;
        }
        return false;
    }
}
