package entelect.training.incubator.spring.loyalty.ws;

import entelect.training.incubator.spring.loyalty.server.RewardsService;
import entelect.training.incubator.spring.loyalty.ws.model.CaptureRewardsRequest;
import entelect.training.incubator.spring.loyalty.ws.model.CaptureRewardsResponse;
import entelect.training.incubator.spring.loyalty.ws.model.RewardsBalanceRequest;
import entelect.training.incubator.spring.loyalty.ws.model.RewardsBalanceResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigDecimal;

@Endpoint
public class RewardsEndpoint {
    
    private static final String NAMESPACE_URI = "http://entelect.training/incubator/spring-loyalty-service";
    
    private final RewardsService rewardsService;
    
    public RewardsEndpoint(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "captureRewardsRequest")
    @ResponsePayload
    public CaptureRewardsResponse captureRewards(@RequestPayload CaptureRewardsRequest request) {
        final BigDecimal balance = this.rewardsService.updateBalance(request.getPassportNumber(), request.getAmount());
    
        final CaptureRewardsResponse response = new CaptureRewardsResponse();
        response.setBalance(balance);
        return response;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "rewardsBalanceRequest")
    @ResponsePayload
    public RewardsBalanceResponse rewardsBalance(@RequestPayload RewardsBalanceRequest request) {
        final BigDecimal balance = this.rewardsService.getBalance(request.getPassportNumber());
        
        final RewardsBalanceResponse response = new RewardsBalanceResponse();
        response.setBalance(balance);
        return response;
    }
}
