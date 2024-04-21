package mateuszsalwowski.zadanierekrutacyjne.service;
import jakarta.transaction.Transactional;
import mateuszsalwowski.zadanierekrutacyjne.dto.PassengerDTO;
import mateuszsalwowski.zadanierekrutacyjne.exception.*;
import mateuszsalwowski.zadanierekrutacyjne.mapper.PassengerMapper;
import mateuszsalwowski.zadanierekrutacyjne.model.Passenger;
import mateuszsalwowski.zadanierekrutacyjne.repository.FlightRepository;
import mateuszsalwowski.zadanierekrutacyjne.repository.PassengerRepository;
import org.springframework.stereotype.Service;
@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;
    private final FlightService flightService;
    private final PassengerMapper passengerMapper;
    public PassengerService(PassengerRepository passengerRepository, FlightRepository flightRepository, FlightService flightService, PassengerMapper passengerMapper) {
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
        this.flightService = flightService;
        this.passengerMapper = passengerMapper;
    }
    @Transactional
    public PassengerDTO savePassenger(PassengerDTO passengerDTO){
        validatePassenger(passengerDTO);
        Passenger passenger = passengerMapper.mapToDao(passengerDTO);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return passengerMapper.mapToDto(savedPassenger);
    }
    public void validatePassenger(PassengerDTO passengerDTO){
        if (passengerDTO.getName().isBlank() || passengerDTO.getName()==null){
            throw new PassengerValidationException("Name must not be null or blank!");
        }
        if (passengerDTO.getLastName().isBlank() || passengerDTO.getLastName()==null){
            throw new PassengerValidationException("Name must not be null or blank!");
        }
        if (passengerDTO.getPhoneNumber().isBlank() || passengerDTO.getPhoneNumber()==null){
            throw new PassengerValidationException("Phone number must not be null or blank");
        }
        if (!validatePhoneNumber(passengerDTO.getPhoneNumber())){
            throw new PassengerValidationException("Phone number must have 9 chars, or valid format");
        }
    }
    private boolean validatePhoneNumber(String phoneNumber){
        phoneNumber = phoneNumber.replaceAll("\\s", "");
        if (phoneNumber.matches("\\d{9}")) {
            return true;
        } else {
            return false;
        }
    }
    @Transactional
    public void deletePassengerById(Long id){
        passengerRepository.findById(id).orElseThrow(() -> new PassengerNotFoundException("Flight with id " + id + " not found"));
        passengerRepository.deleteById(id);
    }
@Transactional
public PassengerDTO updatePassenger(Long id, String phoneNumber, String name, String lastName) {
    Passenger passenger = findById(id);
    if (validatePhoneNumber(phoneNumber)) {
        passenger.setPhoneNumber(phoneNumber);
    }
    passenger.setLastName(lastName);
    passenger.setName(name);
    Passenger updatedPassenger = passengerRepository.save(passenger);
    return passengerMapper.mapToDto(updatedPassenger);
}

public Passenger findById(Long id){
    return passengerRepository.findById(id).orElseThrow(() -> new PassengerNotFoundException("Passenger with id " + id + " not found"));
}
}

