package mateuszsalwowski.zadanierekrutacyjne.dto;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class PassengerDTO {
    private Long id;
    private String name;
    private String lastName;
    private String phoneNumber;
}
