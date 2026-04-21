package hu.szte.flight_service.repository;

import hu.szte.flight_service.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {

    @Query("""
            SELECT f FROM Flight f
            WHERE f.from = :depart
            AND (:destination IS NULL OR f.destination = :destination)
            AND (CAST(:departTime AS java.time.OffsetDateTime) IS NULL OR f.departTime >= :departTime)
            """)
    Page<Flight> searchFlights(
            @Param("depart") String depart,
            @Param("destination") String destination,
            @Param("departTime") OffsetDateTime departTime,
            Pageable pageable
    );
}