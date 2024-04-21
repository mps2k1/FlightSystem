package mateuszsalwowski.zadanierekrutacyjne.mapper;
import mateuszsalwowski.zadanierekrutacyjne.dto.PassengerDTO;
import mateuszsalwowski.zadanierekrutacyjne.model.Passenger;
import org.springframework.stereotype.Service;
@Service
public class PassengerMapper {
    public PassengerDTO mapToDto(Passenger passenger){
        return PassengerDTO.builder()
                .id(passenger.getId())
                .name(passenger.getName())
                .lastName(passenger.getLastName())
                .phoneNumber(passenger.getPhoneNumber())
                .build();
    }
    public Passenger mapToDao(PassengerDTO passengerDTO){
        return Passenger.builder()
                .id(passengerDTO.getId())
                .phoneNumber(passengerDTO.getPhoneNumber())
                .name(passengerDTO.getName())
                .lastName(passengerDTO.getLastName())
                .build();
    }
}
