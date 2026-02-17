package entelect.training.incubator.spring.booking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientsConfig {

    @Bean(name = "customerRestClient")
    RestClient customerRestClient(@Value("${services.customer.base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor((request, body, execution) -> {
                    request.getHeaders().setBasicAuth("user", "the_cake");
                    return execution.execute(request, body);
                })
                .build();
    }

    @Bean(name = "flightRestClient")
    RestClient flightRestClient(@Value("${services.flight.base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor((request, body, execution) -> {
                    request.getHeaders().setBasicAuth("user", "the_cake");
                    return execution.execute(request, body);
                })
                .build();
    }
}
