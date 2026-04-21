package hu.szte.flight_service.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class SearchFlightRequest {
    private String depart;
    private String destination;
    private OffsetDateTime departTime;
    private boolean retour = false;
    private OffsetDateTime retourTime;
}