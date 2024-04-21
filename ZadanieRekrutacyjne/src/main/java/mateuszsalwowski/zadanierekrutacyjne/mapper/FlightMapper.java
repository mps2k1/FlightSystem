package mateuszsalwowski.zadanierekrutacyjne.mapper;
import mateuszsalwowski.zadanierekrutacyjne.dto.FlightDTO;
import mateuszsalwowski.zadanierekrutacyjne.model.Flight;
import mateuszsalwowski.zadanierekrutacyjne.service.DepartureTimeService;
import org.springframework.stereotype.Service;
@Service
public class FlightMapper {
    private final DepartureTimeService departureTimeService;

    public FlightMapper(DepartureTimeService departureTimeService) {
        this.departureTimeService = departureTimeService;
    }
    public FlightDTO mapToDto(Flight flight) {
        return FlightDTO.builder()
                .id(flight.getId())
                .routeFrom(flight.getRouteFrom())
                .routeTo(flight.getRouteTo())
                .flightNumber(flight.getFlightNumber())
                .departureTimeDay(departureTimeService.createDepartureTimeDayDto(flight.getDepartureTime()))
                .departureTimeYear(departureTimeService.createDepartureTimeYearDto(flight.getDepartureTime()))
                .departureTimeMonth(departureTimeService.createDepartureTimeMonthDto(flight.getDepartureTime()))
                .departureTimeHour(departureTimeService.createDepartureTimeHourDto(flight.getDepartureTime()))
                .availablePlaces(flight.getAvailablePlaces())
                .build();
    }
    public Flight mapToDao(FlightDTO flightDTO){
        return Flight.builder()
                .id(flightDTO.getId())
                .routeTo(flightDTO.getRouteTo())
                .routeFrom(flightDTO.getRouteFrom())
                .flightNumber(flightDTO.getFlightNumber())
                .departureTime(departureTimeService.createDepartureTime(flightDTO.getDepartureTimeYear(),
                        flightDTO.getDepartureTimeMonth(),
                        flightDTO.getDepartureTimeDay(),
                        flightDTO.getDepartureTimeHour()))
                .availablePlaces(flightDTO.getAvailablePlaces())
                .build();
    }
}
