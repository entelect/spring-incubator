package entelect.training.incubator.spring.bookings;

import entelect.training.incubator.spring.bookings.rewards.CaptureRewardsRequest;
import entelect.training.incubator.spring.bookings.rewards.CaptureRewardsResponse;
import entelect.training.incubator.spring.bookings.rewards.RewardsBalanceRequest;
import entelect.training.incubator.spring.bookings.rewards.RewardsBalanceResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.math.BigDecimal;

public class RewardsClient extends WebServiceGatewaySupport {
    public CaptureRewardsResponse captureRewards(String passportNumber, Float seatCost) {
        CaptureRewardsRequest request = new CaptureRewardsRequest();

        request.setPassportNumber(passportNumber);
        request.setAmount(BigDecimal.valueOf(seatCost));

        CaptureRewardsResponse response = (CaptureRewardsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);

        return response;
    }

    public RewardsBalanceResponse getRewardsBalance(String passportNumber){
        RewardsBalanceRequest request = new RewardsBalanceRequest();
        request.setPassportNumber(passportNumber);

        RewardsBalanceResponse response = (RewardsBalanceResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);

        return response;
    }
}
