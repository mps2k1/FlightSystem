package mateuszsalwowski.zadanierekrutacyjne.controller;
import mateuszsalwowski.zadanierekrutacyjne.dto.PassengerDTO;
import mateuszsalwowski.zadanierekrutacyjne.exception.FlightNotFoundException;
import mateuszsalwowski.zadanierekrutacyjne.exception.NoChangesDetectedException;
import mateuszsalwowski.zadanierekrutacyjne.exception.PassengerValidationException;
import mateuszsalwowski.zadanierekrutacyjne.service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/passengers")
public class PassengerController {
    private final PassengerService passengerService;
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }
    @PostMapping
    public ResponseEntity<String> savePassenger(@RequestBody PassengerDTO passengerDTO) {
        try {
            PassengerDTO dto = passengerService.savePassenger(passengerDTO);
            return new ResponseEntity(dto, HttpStatus.CREATED);
        } catch (PassengerValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassenger(@PathVariable Long id) {
        try {
            passengerService.deletePassengerById(id);
            return new ResponseEntity<>("Successfull deleted passenger with id: " + id, HttpStatus.OK);
        } catch (FlightNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<PassengerDTO> updatePassenger(@PathVariable Long id, @RequestParam(required = false) String phoneNumber,@RequestParam(required = false) String name, @RequestParam(required = false) String lastName) {
        try {
           PassengerDTO passengerDTO= passengerService.updatePassenger(id,phoneNumber,name,lastName);
            return new ResponseEntity<>(passengerDTO,HttpStatus.OK);
        } catch (NoChangesDetectedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}