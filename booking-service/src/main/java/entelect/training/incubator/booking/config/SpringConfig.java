package entelect.training.incubator.booking.config;

import entelect.training.incubator.booking.logic.CustomerServiceCaller;
import entelect.training.incubator.booking.logic.FlightServiceCaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
