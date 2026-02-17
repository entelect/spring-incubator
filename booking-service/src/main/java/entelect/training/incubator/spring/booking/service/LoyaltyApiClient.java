package entelect.training.incubator.spring.booking.service;

import entelect.training.incubator.spring.booking.ws.model.CaptureRewardsRequest;
import entelect.training.incubator.spring.booking.ws.model.CaptureRewardsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.math.BigDecimal;

@Component
public class LoyaltyApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoyaltyApiClient.class);

    private final WebServiceTemplate loyaltyWebServiceTemplate;

    public LoyaltyApiClient(WebServiceTemplate loyaltyWebServiceTemplate) {
        this.loyaltyWebServiceTemplate = loyaltyWebServiceTemplate;
    }

    public BigDecimal captureRewards(String passportNumber, BigDecimal amount) {
        LOGGER.info("Capturing rewards for passportNumber={}, amount={}", passportNumber, amount);

        CaptureRewardsRequest request = new CaptureRewardsRequest();
        request.setPassportNumber(passportNumber);
        request.setAmount(amount);

        CaptureRewardsResponse response = (CaptureRewardsResponse)
                loyaltyWebServiceTemplate.marshalSendAndReceive(request);

        LOGGER.info("Rewards captured. New balance={}", response.getBalance());
        return response.getBalance();
    }
}
