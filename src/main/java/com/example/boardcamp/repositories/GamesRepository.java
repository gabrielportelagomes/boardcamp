package com.example.boardcamp.repositories;

import com.example.boardcamp.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamesRepository extends JpaRepository<Game, Long> {
    boolean existsByName(String name);
}
