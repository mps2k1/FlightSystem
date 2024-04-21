package mateuszsalwowski.zadanierekrutacyjne;
import mateuszsalwowski.zadanierekrutacyjne.dto.FlightDTO;
import mateuszsalwowski.zadanierekrutacyjne.dto.PassengerDTO;
import mateuszsalwowski.zadanierekrutacyjne.mapper.FlightMapper;
import mateuszsalwowski.zadanierekrutacyjne.mapper.PassengerMapper;
import mateuszsalwowski.zadanierekrutacyjne.model.Flight;
import mateuszsalwowski.zadanierekrutacyjne.model.Passenger;
import mateuszsalwowski.zadanierekrutacyjne.repository.FlightRepository;
import mateuszsalwowski.zadanierekrutacyjne.repository.PassengerRepository;
import mateuszsalwowski.zadanierekrutacyjne.service.DepartureTimeService;
import mateuszsalwowski.zadanierekrutacyjne.service.FlightService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FlightServiceTest {
    @Mock
    private  FlightRepository flightRepository;
    @Mock
    private  FlightMapper flightMapper;
    @Mock
    private DepartureTimeService departureTimeService;
    @Mock
    private  PassengerRepository passengerRepository;
    @Mock
    private  PassengerMapper passengerMapper;
    @InjectMocks
    private FlightService flightService;
    private static final String FLIGHT_NUMBER = "LO3454";
    private static final String ROUTE_FROM = "TEST";
    private static final String ROUTE_TO = "TEST";

    @Test
    void saveFlight(){
        FlightDTO flightDTO = FlightDTO.builder()
                .availablePlaces(10)
                .id(1L)
                .flightNumber(FLIGHT_NUMBER)
                .departureTimeDay(1)
                .departureTimeYear(2025)
                .departureTimeMonth(1)
                .departureTimeHour(10)
                .routeFrom(ROUTE_FROM)
                .routeTo(ROUTE_TO)
                .build();
        Flight flight = new Flight();
        when(flightMapper.mapToDao(flightDTO)).thenReturn(flight);
        when(flightRepository.save(flight)).thenReturn(flight);
        when(flightMapper.mapToDto(flight)).thenReturn(flightDTO);
        FlightDTO savedFlight = flightService.saveFlight(flightDTO);
        Assertions.assertNotNull(savedFlight);
        assertEquals(flightDTO,savedFlight);

    }
    @Test
    void deleteFlightById(){
        Long id = 1L;
        Flight flight = new Flight();
        when(flightRepository.findById(id)).thenReturn(Optional.of(flight));
        flightService.deleteFlightById(id);
        verify(flightRepository, times(1)).deleteById(id);

    }
@Test
void findAllFlights(){
    Flight flight1 = new Flight();
    flight1.setId(1L);
    flight1.setFlightNumber(FLIGHT_NUMBER);
    flight1.setRouteTo(ROUTE_TO);
    flight1.setRouteFrom(ROUTE_FROM);
    flight1.setDepartureTime(LocalDateTime.of(2025, 1, 1, 1, 0));
    flight1.setAvailablePlaces(10);

    FlightDTO flightDTO = FlightDTO.builder()
            .routeTo(ROUTE_TO)
            .routeFrom(ROUTE_FROM)
            .flightNumber(FLIGHT_NUMBER)
            .departureTimeYear(2025)
            .departureTimeMonth(1)
            .departureTimeDay(1)
            .departureTimeHour(1)
            .availablePlaces(10)
            .build();

    List<FlightDTO> expectedFlights = new ArrayList<>();
    expectedFlights.add(flightDTO);

    when(flightRepository.findAll()).thenReturn(List.of(flight1));

    when(flightMapper.mapToDto(flight1)).thenReturn(flightDTO);

    List<FlightDTO> actualFlights = flightService.findAllFlights();

    assertEquals(expectedFlights, actualFlights);
}
@Test
    void findAllByAvailablePlaces(){
    Flight flight1 = new Flight();
    flight1.setId(1L);
    flight1.setFlightNumber(FLIGHT_NUMBER);
    flight1.setAvailablePlaces(5);


    FlightDTO flightDto1 = FlightDTO.builder()
            .id(1L)
            .build();

    List<FlightDTO> expectedFlights = Arrays.asList(flightDto1);

    when(flightRepository.findAll()).thenReturn(Arrays.asList(flight1));

    when(flightMapper.mapToDto(flight1)).thenReturn(flightDto1);

    List<FlightDTO> actualFlights = flightService.findAllFlightsWithAvailablePlaces();

    assertEquals(expectedFlights, actualFlights);
}

    @Test
    void testFindFlightsByRoute() {
        Flight flight1 = new Flight();
        flight1.setId(1L);
        flight1.setRouteTo(ROUTE_TO);
        flight1.setRouteFrom(ROUTE_FROM);

        FlightDTO flightDto1 = FlightDTO.builder().build();
        flightDto1.setId(1L);
        flightDto1.setRouteTo(ROUTE_TO);
        flightDto1.setRouteFrom(ROUTE_FROM);

        List<FlightDTO> expectedFlights = Arrays.asList(flightDto1);

        when(flightRepository.findAll()).thenReturn(Arrays.asList(flight1));

        when(flightMapper.mapToDto(flight1)).thenReturn(flightDto1);

        List<FlightDTO> actualFlights = flightService.findFlightsByRoute(ROUTE_TO, ROUTE_FROM);

        assertEquals(expectedFlights, actualFlights);
    }

    @Test
    void testFindFlightsByDate() {
        Flight flight1 = new Flight();
        flight1.setId(1L);
        flight1.setDepartureTime(LocalDateTime.of(2025, 1, 1, 12, 0));

        FlightDTO flightDto1 = FlightDTO.builder().build();
        flightDto1.setId(1L);
        flightDto1.setDepartureTimeYear(2025);
        flightDto1.setDepartureTimeMonth(1);
        flightDto1.setDepartureTimeDay(1);
        flightDto1.setDepartureTimeHour(12);

        List<FlightDTO> expectedFlights = Arrays.asList(flightDto1);

        when(flightRepository.findAll()).thenReturn(Arrays.asList(flight1));

        when(flightMapper.mapToDto(flight1)).thenReturn(flightDto1);

        List<FlightDTO> actualFlights = flightService.findFlightsByDate(2025, 1, 1, 12);

        assertEquals(expectedFlights, actualFlights);
    }
@Test
    void addPassengerToFlight(){
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setAvailablePlaces(10);
        flight.setRouteTo(ROUTE_TO);
        flight.setRouteFrom(ROUTE_FROM);
        when(flightRepository.findById(flight.getId())).thenReturn(Optional.of(flight));
    Passenger passenger = new Passenger();
    passenger.setId(1L);
    passenger.setName("test");
    passenger.setLastName("test");
    when(passengerRepository.findById(passenger.getId())).thenReturn(Optional.of(passenger));
    flightService.addPassengerToFlight(1L,1L);
    assertEquals(flight.getPassengers().get(0),passenger);
}
@Test
    void deletePassengerFromFlight(){
    Flight flight = new Flight();
    flight.setId(1L);
    flight.setAvailablePlaces(10);
    flight.setRouteTo(ROUTE_TO);
    flight.setRouteFrom(ROUTE_FROM);
    when(flightRepository.findById(flight.getId())).thenReturn(Optional.of(flight));
    Passenger passenger = new Passenger();
    passenger.setId(1L);
    passenger.setName("test");
    passenger.setLastName("test");
    when(passengerRepository.findById(passenger.getId())).thenReturn(Optional.of(passenger));
    flightService.deletePassengerFromFlight(1L,1L);
    assertTrue(flight.getPassengers().isEmpty());
}

@Test
    void showPassengersFromFlight(){
    Flight flight = new Flight();
    flight.setId(1L);
    flight.setAvailablePlaces(10);
    flight.setRouteTo(ROUTE_TO);
    flight.setRouteFrom(ROUTE_FROM);
    when(flightRepository.findById(flight.getId())).thenReturn(Optional.of(flight));
    Passenger passenger = new Passenger();
    passenger.setId(1L);
    passenger.setName("test");
    passenger.setLastName("test");
    flight.setPassengers(Collections.singletonList(passenger));
    PassengerDTO passengerDTO = PassengerDTO.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .build();
    when(passengerMapper.mapToDto(passenger)).thenReturn(passengerDTO);
    List<PassengerDTO> passengerDTOS = flightService.showPassengersFromFlight(1L);
    assertTrue(passengerDTOS.contains(passengerDTO));
}
@Test
    void changeFlightsDate(){
    Flight flight = new Flight();
    flight.setId(2L);
    flight.setAvailablePlaces(10);
    flight.setRouteTo(ROUTE_TO);
    flight.setRouteFrom(ROUTE_FROM);
    when(flightRepository.findById(flight.getId())).thenReturn(Optional.of(flight));
    FlightDTO dto = FlightDTO.builder()
            .routeTo(ROUTE_TO)
            .routeFrom(ROUTE_FROM)
            .id(2l)
            .availablePlaces(10)
            .build();
    when(flightMapper.mapToDto(flight)).thenReturn(dto);
    FlightDTO flightDTO = flightService.changeFlightsDate(2L,2026,6,29,10);
    assertTrue(dto.getDepartureTimeYear()==flightDTO.getDepartureTimeYear());

}
}
