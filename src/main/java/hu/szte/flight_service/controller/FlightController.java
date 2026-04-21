package hu.szte.flight_service.controller;

import hu.szte.flight_service.dto.SearchFlightRequest;
import hu.szte.flight_service.dto.SearchFlightResponse;
import hu.szte.flight_service.entity.Flight;
import hu.szte.flight_service.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightRepository flightRepository;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Flight service is up and running!");
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Flight>> getAllFlights(Pageable pageable) {
        return ResponseEntity.ok(flightRepository.findAll(pageable));
    }

    @PostMapping("/search")
    public ResponseEntity<SearchFlightResponse> searchFlights(
            @RequestBody SearchFlightRequest request,
            Pageable pageable) {

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

        return ResponseEntity.ok(SearchFlightResponse.builder()
                .departList(departList)
                .retourList(retourList)
                .build());
    }
}