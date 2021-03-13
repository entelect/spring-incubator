package entelect.training.incubator.spring.loyalty.server;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class RewardsServiceImpl implements RewardsService {
    
    private final HashMap<String, BigDecimal> rewardsBalanceMap = new HashMap<>();
    
    @Override
    public BigDecimal updateBalance(String passportNumber, BigDecimal amount) {
        final BigDecimal updatedBalance = this.rewardsBalanceMap.getOrDefault(passportNumber, BigDecimal.ZERO).add(amount);
        this.rewardsBalanceMap.put(passportNumber, updatedBalance);
        return updatedBalance;
    }
    
    @Override
    public BigDecimal getBalance(String passportNumber) {
        return this.rewardsBalanceMap.getOrDefault(passportNumber, BigDecimal.ZERO);
    }
}
