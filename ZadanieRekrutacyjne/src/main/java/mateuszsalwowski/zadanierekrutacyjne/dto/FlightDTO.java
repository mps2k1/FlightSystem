package mateuszsalwowski.zadanierekrutacyjne.dto;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class FlightDTO {
    private Long id;
    private String flightNumber;
    private String routeFrom;
    private String routeTo;
    private int departureTimeYear;
    private int departureTimeMonth;
    private int departureTimeDay;
    private int departureTimeHour;
    private int availablePlaces;
}
