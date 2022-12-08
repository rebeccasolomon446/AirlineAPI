package com.example.airline_api.controllers;

import com.example.airline_api.models.Flight;
import com.example.airline_api.models.Passenger;
import com.example.airline_api.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    FlightRepository flightRepository;

    // Display all available flights
    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights(){
        return new ResponseEntity(flightRepository.findAll(), HttpStatus.OK);
    }

    // Display a specific flight
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Flight>> getFlightById(@PathVariable Long id) {
        return new ResponseEntity(flightRepository.findById(id), HttpStatus.OK);
    }
    // Add details of a new flight
    @PostMapping
    public ResponseEntity<Flight> addNewFlight(@RequestBody Flight flight){
        flightRepository.save(flight);
        return new ResponseEntity(flight, HttpStatus.CREATED);
    }

    // Book passenger on a flight
    @PatchMapping(value = "/{id}") // adding a new passenger to an already existing flight
    public ResponseEntity<Flight> addPassengerToFlight(@RequestBody Flight flight, @PathVariable Long id) {
        Flight flightToAddPassengerTo = flightRepository.findById(id).get();
        flightToAddPassengerTo.setDestination(flight.getDestination());
        flightToAddPassengerTo.setCapacity(flight.getCapacity());
        flightToAddPassengerTo.setDepartureDate(flight.getDepartureDate());
        flightToAddPassengerTo.setDepartureTime(flight.getDepartureTime());
        flightToAddPassengerTo.setPassengers(flight.getPassengers());
        flightRepository.save(flightToAddPassengerTo);
        return new ResponseEntity<>(flightToAddPassengerTo,HttpStatus.OK);
    }

    // Cancel flight
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> cancelFlight(@PathVariable Long id){
        flightRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
