package mateuszsalwowski.zadanierekrutacyjne.service;
import jakarta.transaction.Transactional;
import mateuszsalwowski.zadanierekrutacyjne.dto.FlightDTO;
import mateuszsalwowski.zadanierekrutacyjne.dto.PassengerDTO;
import mateuszsalwowski.zadanierekrutacyjne.exception.FlightNotFoundException;
import mateuszsalwowski.zadanierekrutacyjne.exception.FlightValidationException;
import mateuszsalwowski.zadanierekrutacyjne.exception.PassengerNotFoundException;
import mateuszsalwowski.zadanierekrutacyjne.mapper.FlightMapper;
import mateuszsalwowski.zadanierekrutacyjne.mapper.PassengerMapper;
import mateuszsalwowski.zadanierekrutacyjne.model.Flight;
import mateuszsalwowski.zadanierekrutacyjne.model.Passenger;
import mateuszsalwowski.zadanierekrutacyjne.repository.FlightRepository;
import mateuszsalwowski.zadanierekrutacyjne.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final DepartureTimeService departureTimeService;
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private static final String AIRLINE_CODE = "LO";

    public FlightService(FlightRepository flightRepository, FlightMapper flightMapper, DepartureTimeService departureTimeService, PassengerRepository passengerRepository, PassengerMapper passengerMapper) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
        this.departureTimeService = departureTimeService;
        this.passengerRepository = passengerRepository;
        this.passengerMapper = passengerMapper;
    }

    @Transactional
    public FlightDTO saveFlight(FlightDTO flightDTO) {
        validateFlight(flightDTO);
        flightDTO.setFlightNumber(generateFlightNumber());
        Flight flight = flightMapper.mapToDao(flightDTO);
        Flight savedFlight = flightRepository.save(flight);
        return flightMapper.mapToDto(savedFlight);
    }

    private void validateFlight(FlightDTO flightDTO) {
        departureTimeService.validateDepartureTime(
                flightDTO.getDepartureTimeYear(),
                flightDTO.getDepartureTimeMonth(),
                flightDTO.getDepartureTimeDay(),
                flightDTO.getDepartureTimeHour());
        if (flightDTO.getRouteTo().isBlank() || flightDTO.getRouteTo() == null) {
            throw new FlightValidationException("Route to must not be blank or null!");
        }
        if (flightDTO.getRouteFrom().isBlank() || flightDTO.getRouteFrom() == null) {
            throw new FlightValidationException("Route from must not be blank or null!");
        }
        if (flightDTO.getAvailablePlaces() <= 0) {
            throw new FlightValidationException("Available places must equals more than 0!");
        }
    }

    private String generateFlightNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        String flightNumber = AIRLINE_CODE + randomNumber;
        return flightNumber;
    }

    @Transactional
    public void deleteFlightById(Long id) {
        flightRepository.findById(id).orElseThrow(() -> new FlightNotFoundException("Flight with id " + id + " not found"));
        flightRepository.deleteById(id);
    }

    public boolean validateAvailablePlaces(Long id) {
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new FlightNotFoundException("Flight with id " + id + " not found"));
        if (flight.getAvailablePlaces() <= 0) {
            return false;
        }
        return true;
    }

    public List<FlightDTO> findAllFlights() {
        return flightRepository.findAll().stream().map(flightMapper::mapToDto).collect(Collectors.toList());
    }

    public List<FlightDTO> findAllFlightsWithAvailablePlaces() {
        return flightRepository.findAll().
                stream().
                filter(flight -> flight.getAvailablePlaces() > 0)
                .map(flightMapper::mapToDto)
                .collect(Collectors.toList());

    }
    public List<FlightDTO> findFlightsByRoute(String routeTo, String routeFrom){
        return flightRepository.findAll().
                stream().filter(flight -> flight.getRouteTo()
                        .equals(routeTo))
                .filter(flight -> flight.getRouteFrom()
                        .equals(routeFrom))
                .map(flightMapper::mapToDto).collect(Collectors.toList());
    }
    public List<FlightDTO> findFlightsByDate(int year, int month, int day, int hour){
        return flightRepository.findAll().
                stream()
                .filter(flight -> flight.getDepartureTime()
                        .equals(LocalDateTime.of(year,month,day,hour,0)))
                .map(flightMapper::mapToDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public void addPassengerToFlight(Long flightId, Long passengerId) {
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new FlightNotFoundException("Flight with id " + flightId + " not found"));
        if (!validateAvailablePlaces(flightId)) {
            throw new FlightValidationException("No available places in flight with id: " + flightId);
        }
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new PassengerNotFoundException("Passenger with id " + passengerId + " not found"));
        passenger.getFlights().add(flight);
        flight.getPassengers().add(passenger);
        flight.setAvailablePlaces(flight.getAvailablePlaces() - 1);
        flightRepository.save(flight);
    }
    @Transactional
    public void deletePassengerFromFlight(Long flightId, Long passengerId) {
        Flight flight = findFlightById(flightId);
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new PassengerNotFoundException("Flight with id " + passengerId + " not found"));
        passenger.getFlights().remove(flight);
        flight.getPassengers().remove(passenger);
        flight.setAvailablePlaces(flight.getAvailablePlaces() + 1);
        flightRepository.save(flight);
    }

    public List<PassengerDTO> showPassengersFromFlight(Long flightId) {
        Flight flight = findFlightById(flightId);

        List<PassengerDTO> passengerDTOs = flight.getPassengers().stream()
                .map(passengerMapper::mapToDto)
                .collect(Collectors.toList());

        return passengerDTOs;
    }
@Transactional
    public FlightDTO changeFlightsRoute(Long flightId, String routeTo, String routeFrom) {
        Flight flight = findFlightById(flightId);
flight.setRouteTo(routeTo);
flight.setRouteFrom(routeFrom);
flightRepository.save(flight);
return flightMapper.mapToDto(flight);
    }
    @Transactional
    public FlightDTO changeFlightsDate(Long id,int year, int month, int day, int hour) {
        Flight flight = findFlightById(id);
        flight.setDepartureTime(LocalDateTime.of(year,month,day,hour,0));
        flightRepository.save(flight);
        return flightMapper.mapToDto(flight);
    }

    private Flight findFlightById(Long id){
        return flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight with id " + id + " not found"));
    }
    }

