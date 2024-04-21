package mateuszsalwowski.zadanierekrutacyjne.repository;
import mateuszsalwowski.zadanierekrutacyjne.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
