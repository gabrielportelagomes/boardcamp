package com.example.boardcamp.services;

import com.example.boardcamp.dtos.GameDTO;
import com.example.boardcamp.exceptions.GameConflictException;
import com.example.boardcamp.models.Game;
import com.example.boardcamp.repositories.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamesService {

    @Autowired
    private GamesRepository gameRepository;

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game createGame(GameDTO gameDTO) {
        if (gameRepository.existsByName(gameDTO.name())) {
            throw new GameConflictException("Jogo com esse nome já existe", HttpStatus.CONFLICT);
        }

        Game game = convertToEntity(gameDTO);
        return gameRepository.save(game);
    }

    private Game convertToEntity(GameDTO gameDTO) {
        Game game = new Game();
        game.setName(gameDTO.name());
        game.setImage(gameDTO.image());
        game.setStockTotal(gameDTO.stockTotal());
        game.setPricePerDay(gameDTO.pricePerDay());
        return game;
    }
}
