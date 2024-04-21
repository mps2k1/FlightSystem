package mateuszsalwowski.zadanierekrutacyjne.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "flights")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "flight_number")
    private String flightNumber;
    @Column(name = "route_from")
    private String routeFrom;
    @Column(name = "route_to")
    private String routeTo;
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    @Column(name = "available_places")
    private int availablePlaces;
    @ManyToMany(mappedBy = "flights", cascade = CascadeType.MERGE)
    private List<Passenger> passengers = new ArrayList<>();
}
