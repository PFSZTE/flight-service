package hu.szte.flight_service.controller;

import hu.szte.flight_service.dto.SearchFlightRequest;
import hu.szte.flight_service.dto.SearchFlightResponse;
import hu.szte.flight_service.entity.Flight;
import hu.szte.flight_service.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Flight service is up and running!");
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Flight>> getAllFlights(Pageable pageable) {
        return ResponseEntity.ok(flightService.getAllFlights(pageable));
    }

    @PostMapping("/search")
    public ResponseEntity<SearchFlightResponse> searchFlights(
            @RequestBody SearchFlightRequest request,
            Pageable pageable) {
        return ResponseEntity.ok(flightService.searchFlights(request, pageable));
    }
}