package mateuszsalwowski.zadanierekrutacyjne;
import mateuszsalwowski.zadanierekrutacyjne.dto.PassengerDTO;
import mateuszsalwowski.zadanierekrutacyjne.mapper.PassengerMapper;
import mateuszsalwowski.zadanierekrutacyjne.model.Passenger;
import mateuszsalwowski.zadanierekrutacyjne.repository.PassengerRepository;
import mateuszsalwowski.zadanierekrutacyjne.service.PassengerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PassengerServiceTest {
    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerMapper passengerMapper;

    @InjectMocks
    private PassengerService passengerService;

    @Test
    void savePassenger() {
        PassengerDTO passengerDTO = PassengerDTO.builder()
                .name("John")
                .lastName("Doe")
                .phoneNumber("555666777")
                .build();
        Passenger passenger = new Passenger();
        when(passengerMapper.mapToDao(passengerDTO)).thenReturn(passenger);

        when(passengerRepository.save(passenger)).thenReturn(passenger);

        when(passengerMapper.mapToDto(passenger)).thenReturn(passengerDTO);

        PassengerDTO savedPassengerDTO = passengerService.savePassenger(passengerDTO);

        assertNotNull(savedPassengerDTO);

        assertEquals(passengerDTO, savedPassengerDTO);

    }
    @Test
    void deletePassengerById(){
        Long id = 1L;
        Passenger passenger = new Passenger();
        when(passengerRepository.findById(id)).thenReturn(Optional.of(passenger));
        passengerService.deletePassengerById(id);
        verify(passengerRepository, times(1)).deleteById(id);
    }

    @Test
    void updatePassenger(){
        Long id = 1L;
        String phoneNumber = "555666777";
        String name = "John";
        String lastName = "Doe";
        String newName = "Jane";
        String newLastName = "Smith";
        Passenger passenger = new Passenger();
        passenger.setId(id);
        passenger.setName(name);
        passenger.setLastName(lastName);
        passenger.setPhoneNumber(phoneNumber);

        when(passengerService.findById(id)).thenReturn(passenger);

        PassengerDTO updatedPassengerDTO = passengerService.updatePassenger(id, phoneNumber, newName, newLastName);

        assertEquals(newName, updatedPassengerDTO.getName());
        assertEquals(newLastName, updatedPassengerDTO.getLastName());

    }
}

