package hu.szte.flight_service;

import hu.szte.flight_service.dto.SearchFlightRequest;
import hu.szte.flight_service.dto.SearchFlightResponse;
import hu.szte.flight_service.entity.Flight;
import hu.szte.flight_service.repository.FlightRepository;
import hu.szte.flight_service.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    private Flight testFlight;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);
        testFlight = Flight.builder()
                .id(UUID.randomUUID())
                .from("Budapest")
                .destination("London")
                .baseCost(new BigDecimal("120.00"))
                .departTime(ZonedDateTime.now().plusDays(1))
                .landTime(ZonedDateTime.now().plusDays(1).plusHours(2))
                .build();
    }

    @Test
    void getAllFlights_shouldReturnPageOfFlights() {
        Page<Flight> page = new PageImpl<>(List.of(testFlight));
        when(flightRepository.findAll(pageable)).thenReturn(page);

        Page<Flight> result = flightService.getAllFlights(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getFrom()).isEqualTo("Budapest");
    }

    @Test
    void searchFlights_shouldReturnDepartList() {
        SearchFlightRequest request = new SearchFlightRequest();
        request.setDepart("Budapest");
        request.setDestination("London");

        Page<Flight> page = new PageImpl<>(List.of(testFlight));
        when(flightRepository.searchFlights(eq("Budapest"), eq("London"), any(), eq(pageable)))
                .thenReturn(page);

        SearchFlightResponse response = flightService.searchFlights(request, pageable);

        assertThat(response.getDepartList()).isNotNull();
        assertThat(response.getDepartList().getTotalElements()).isEqualTo(1);
        assertThat(response.getRetourList()).isNull();
    }

    @Test
    void searchFlights_withRetour_shouldReturnBothLists() {
        SearchFlightRequest request = new SearchFlightRequest();
        request.setDepart("Budapest");
        request.setDestination("London");
        request.setRetour(true);

        Flight retourFlight = Flight.builder()
                .id(UUID.randomUUID())
                .from("London")
                .destination("Budapest")
                .baseCost(new BigDecimal("115.00"))
                .departTime(ZonedDateTime.now().plusDays(5))
                .landTime(ZonedDateTime.now().plusDays(5).plusHours(2))
                .build();

        Page<Flight> departPage = new PageImpl<>(List.of(testFlight));
        Page<Flight> retourPage = new PageImpl<>(List.of(retourFlight));

        when(flightRepository.searchFlights(eq("Budapest"), eq("London"), any(), eq(pageable)))
                .thenReturn(departPage);
        when(flightRepository.searchFlights(eq("London"), eq("Budapest"), any(), eq(pageable)))
                .thenReturn(retourPage);

        SearchFlightResponse response = flightService.searchFlights(request, pageable);

        assertThat(response.getDepartList()).isNotNull();
        assertThat(response.getDepartList().getTotalElements()).isEqualTo(1);
        assertThat(response.getRetourList()).isNotNull();
        assertThat(response.getRetourList().getTotalElements()).isEqualTo(1);
    }

    @Test
    void searchFlights_withoutDestination_shouldReturnNullRetourList() {
        SearchFlightRequest request = new SearchFlightRequest();
        request.setDepart("Budapest");
        request.setRetour(true);

        Page<Flight> page = new PageImpl<>(List.of(testFlight));
        when(flightRepository.searchFlights(eq("Budapest"), any(), any(), eq(pageable)))
                .thenReturn(page);

        SearchFlightResponse response = flightService.searchFlights(request, pageable);

        assertThat(response.getDepartList()).isNotNull();
        assertThat(response.getRetourList()).isNull();
    }
}