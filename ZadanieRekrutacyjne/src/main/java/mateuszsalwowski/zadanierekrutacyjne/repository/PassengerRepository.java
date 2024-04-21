package mateuszsalwowski.zadanierekrutacyjne.repository;
import mateuszsalwowski.zadanierekrutacyjne.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PassengerRepository extends JpaRepository<Passenger,Long> {
}
