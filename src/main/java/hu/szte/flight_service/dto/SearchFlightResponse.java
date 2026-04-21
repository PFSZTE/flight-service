package hu.szte.flight_service.dto;

import hu.szte.flight_service.entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchFlightResponse {
    private Page<Flight> departList;
    private Page<Flight> retourList;
}