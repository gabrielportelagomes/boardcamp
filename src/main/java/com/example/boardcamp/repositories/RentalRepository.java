package com.example.boardcamp.repositories;

import com.example.boardcamp.models.Game;
import com.example.boardcamp.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByGameAndReturnDateIsNull(Game game);
}
