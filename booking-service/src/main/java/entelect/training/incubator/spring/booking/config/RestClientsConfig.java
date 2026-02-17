package entelect.training.incubator.spring.booking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestClient;
import org.springframework.ws.client.core.WebServiceTemplate;

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

    @Bean
    public Jaxb2Marshaller loyaltyMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("entelect.training.incubator.spring.booking.ws.model");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate loyaltyWebServiceTemplate(
            @Value("${services.loyalty.base-url}") String loyaltyBaseUrl,
            Jaxb2Marshaller loyaltyMarshaller) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setDefaultUri(loyaltyBaseUrl);
        template.setMarshaller(loyaltyMarshaller);
        template.setUnmarshaller(loyaltyMarshaller);
        return template;
    }
}
