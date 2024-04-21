package mateuszsalwowski.zadanierekrutacyjne.service;
import mateuszsalwowski.zadanierekrutacyjne.exception.FlightValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class DepartureTimeService {
    public LocalDateTime createDepartureTime(int year, int month, int day, int hour){
        return LocalDateTime.of(year, month, day, hour, 0,0,0);
    }
    public int createDepartureTimeYearDto(LocalDateTime departureTime){
        return departureTime.getYear();
    }
    public int createDepartureTimeMonthDto(LocalDateTime departureTime){
        return departureTime.getMonthValue();
    }
    public int createDepartureTimeDayDto(LocalDateTime departureTime){
        return departureTime.getDayOfMonth();
    }
    public int createDepartureTimeHourDto(LocalDateTime departureTime){
        return departureTime.getHour();
    }
    public void validateDepartureTime(int year, int month, int day, int hour){
        if (createDepartureTime(year,month,day,hour).isBefore(LocalDateTime.now()))
        {
            throw new FlightValidationException("Departure time must not be before local date time now!");
        }

    }
}
