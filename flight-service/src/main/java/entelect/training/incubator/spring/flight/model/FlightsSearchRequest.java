package entelect.training.incubator.spring.flight.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class FlightsSearchRequest {
	
    private SearchType searchType;

    private Integer daysToDeparture;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime departureDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime departureDateTo;

    private String origin;

    private String destination;
}
