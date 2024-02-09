package com.example.boardcamp.controllers;

import com.example.boardcamp.dtos.GameDTO;
import com.example.boardcamp.models.Game;
import com.example.boardcamp.services.GamesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GamesController {

    @Autowired
    private GamesService gamesService;

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gamesService.getAllGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Game> createGame(@Valid @RequestBody GameDTO gameDTO) {
        Game createdGame = gamesService.createGame(gameDTO);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }
}

