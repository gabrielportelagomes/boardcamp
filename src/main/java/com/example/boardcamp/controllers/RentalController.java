package com.example.boardcamp.controllers;

import com.example.boardcamp.dtos.RentalDTO;
import com.example.boardcamp.models.Rental;
import com.example.boardcamp.services.RentalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Rental> createRental(@Valid  @RequestBody RentalDTO rentalDTO) {
        Rental createdRental = rentalService.createRental(rentalDTO);
        return ResponseEntity.status(201).body(createdRental);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Rental> returnRental(@PathVariable Long id) {
        Rental returnedRental = rentalService.returnRental(id);
        return ResponseEntity.ok(returnedRental);
    }
}
