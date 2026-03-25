package hu.szte.flight_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "flight", schema = "flight")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "from_location", nullable = false)
    private String from;

    @Column(nullable = false)
    private String destination;

    @ManyToOne
    @JoinColumn(name = "plane_id", nullable = false)
    private Plane plane;

    @Column(nullable = false)
    private BigDecimal baseCost;

    @Column(nullable = false)
    private ZonedDateTime departTime;

    @Column(nullable = false)
    private ZonedDateTime landTime;
}