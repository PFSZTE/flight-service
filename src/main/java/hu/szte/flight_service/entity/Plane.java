package hu.szte.flight_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "plane", schema = "flight")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Integer capacity;

    @OneToMany(mappedBy = "plane", cascade = CascadeType.ALL)
    private List<Seat> seats;
}