package hu.szte.flight_service.service;

import hu.szte.flight_service.dto.SearchFlightRequest;
import hu.szte.flight_service.dto.SearchFlightResponse;
import hu.szte.flight_service.entity.Flight;
import hu.szte.flight_service.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public Page<Flight> getAllFlights(Pageable pageable) {
        return flightRepository.findAll(pageable);
    }

    public SearchFlightResponse searchFlights(SearchFlightRequest request, Pageable pageable) {
        Page<Flight> departList = flightRepository.searchFlights(
                request.getDepart(),
                request.getDestination(),
                request.getDepartTime(),
                pageable
        );

        Page<Flight> retourList = null;
        if (request.isRetour() && request.getDestination() != null) {
            retourList = flightRepository.searchFlights(
                    request.getDestination(),
                    request.getDepart(),
                    request.getRetourTime(),
                    pageable
            );
        }

        return SearchFlightResponse.builder()
                .departList(departList)
                .retourList(retourList)
                .build();
    }
}