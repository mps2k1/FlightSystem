package mateuszsalwowski.zadanierekrutacyjne.controller;
import mateuszsalwowski.zadanierekrutacyjne.dto.FlightDTO;
import mateuszsalwowski.zadanierekrutacyjne.dto.PassengerDTO;
import mateuszsalwowski.zadanierekrutacyjne.exception.FlightNotFoundException;
import mateuszsalwowski.zadanierekrutacyjne.exception.FlightValidationException;
import mateuszsalwowski.zadanierekrutacyjne.exception.PassengerNotFoundException;
import mateuszsalwowski.zadanierekrutacyjne.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {
    private final FlightService flightService;
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<String> saveFlight(@RequestBody FlightDTO flightDTO) {
        try {
            FlightDTO dto = flightService.saveFlight(flightDTO);
            return new ResponseEntity(dto, HttpStatus.CREATED);
        } catch (FlightValidationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/add-passenger")
    public ResponseEntity<String> addPassengerToFlight(@RequestParam Long flightId,@RequestParam Long passengerId) {
        try {
            flightService.addPassengerToFlight(flightId, passengerId);
            return new ResponseEntity<>("Successfull added passenger with id: "+passengerId+" "+"to flight with id: "+flightId,HttpStatus.CREATED);
        } catch (PassengerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (FlightNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (FlightValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping ("/delete-passenger")
    public ResponseEntity<String> deletePassengerFromFlight(@RequestParam Long flightId,@RequestParam Long passengerId) {
        try {
            flightService.deletePassengerFromFlight(flightId, passengerId);
            return new ResponseEntity<>("Successfull delete passenger with id: "+passengerId+" "+"from flight with id: "+flightId,HttpStatus.CREATED);
        } catch (PassengerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (FlightNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id){
        try {
            flightService.deleteFlightById(id);
            return new ResponseEntity<>("Successfull deleted flight with id: "+id,HttpStatus.OK);
        }
        catch (FlightNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<List<FlightDTO>> findAllFlights(){
        List<FlightDTO> flights = flightService.findAllFlights();
        return new ResponseEntity<>(flights,HttpStatus.OK);
    }
    @GetMapping("/available")
    public ResponseEntity<List<FlightDTO>> findAllAvailableFlights(){
        List<FlightDTO> flights = flightService.findAllFlightsWithAvailablePlaces();
        return new ResponseEntity<>(flights,HttpStatus.OK);
    }
    @GetMapping("/by-route")
    public ResponseEntity<List<FlightDTO>> findFlightsByRoute(@RequestParam String routeTo,
                                                              @RequestParam String routeFrom){
        try {
            List<FlightDTO> dtos = flightService.findFlightsByRoute(routeTo,routeFrom);
            return new ResponseEntity<>(dtos,HttpStatus.OK);
        }
        catch (FlightNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/by-date")
    public ResponseEntity<List<FlightDTO>> findFlightsByDate(@RequestParam int year,
                                                             @RequestParam int month,
                                                             @RequestParam int day,
                                                             @RequestParam int hour
    ){
        try {
            List<FlightDTO> dtos = flightService.findFlightsByDate(year,month,day,hour);
            return new ResponseEntity<>(dtos,HttpStatus.OK);
        }
        catch (FlightNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}/passengers")
    public ResponseEntity<List<PassengerDTO>> showPassengersFromFlight(@PathVariable(value = "id") Long id){
        try {
            List<PassengerDTO> dtos = flightService.showPassengersFromFlight(id);
            return new ResponseEntity<>(dtos,HttpStatus.OK);
        }
        catch (FlightNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}/route")
    public ResponseEntity<FlightDTO> changeFlightsRoute(@PathVariable Long id,
                                                        @RequestParam String routeTo,
                                                        @RequestParam String routeFrom){
        try {
            FlightDTO updatedFlight = flightService.changeFlightsRoute(id,routeTo,routeFrom);
            return new ResponseEntity<>(updatedFlight,HttpStatus.OK);
        }
        catch (FlightNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}/date")
    public ResponseEntity<FlightDTO> changeFlightsDate(@PathVariable Long id,
                                                       @RequestParam int year,
                                                       @RequestParam int month,
                                                       @RequestParam int day,
                                                       @RequestParam int hour){
        try {
            FlightDTO updatedFlight = flightService.changeFlightsDate(id,year,month,day,hour);
            return new ResponseEntity<>(updatedFlight,HttpStatus.OK);
        }
        catch (FlightNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
