package com.example.boardcamp;

import com.example.boardcamp.dtos.CustomerDTO;
import com.example.boardcamp.dtos.GameDTO;
import com.example.boardcamp.dtos.RentalDTO;
import com.example.boardcamp.models.Customer;
import com.example.boardcamp.models.Game;
import com.example.boardcamp.models.Rental;
import com.example.boardcamp.repositories.CustomerRepository;
import com.example.boardcamp.repositories.GamesRepository;
import com.example.boardcamp.repositories.RentalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RecipeIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GamesRepository gamesRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @AfterEach
    public void cleanUpDatabase() {
        rentalRepository.deleteAll();
        customerRepository.deleteAll();
        gamesRepository.deleteAll();
    }

    @Test
    void givenValidCustomerDTO_whenCreatingCustomer_thenReturnsCreatedCustomer() {
        CustomerDTO customerDTO = new CustomerDTO("João", "12345678901");

        ResponseEntity<Customer> response = restTemplate.postForEntity(
                "/customers",
                customerDTO,
                Customer.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customerDTO.name(), response.getBody().getName());
        assertEquals(customerDTO.cpf(), response.getBody().getCpf());
    }

    @Test
    void givenValidGamerDTO_whenCreatingGame_thenReturnsCreatedGame() {
        GameDTO gameDTO = new GameDTO(
                "BancoImobiliário",
                "http: //www.imagem.com.br/banco_imobiliario.jpg",
                3,
                1500);

        ResponseEntity<Game> response = restTemplate.postForEntity(
                "/games",
                gameDTO,
                Game.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(gameDTO.name(), response.getBody().getName());
        assertEquals(gameDTO.image(), response.getBody().getImage());
        assertEquals(gameDTO.stockTotal(), response.getBody().getStockTotal());
        assertEquals(gameDTO.pricePerDay(), response.getBody().getPricePerDay());
    }

    @Test
    void givenGamesInDatabase_whenFetchingAllGames_thenReturnsListOfGames() {
        Long gameId1 = 1L;
        Game game1 = new Game(gameId1, "Game Title 1", "Image URL 1", 10, 5000);

        gamesRepository.save(game1);

        ParameterizedTypeReference<List<Game>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Game>> response = restTemplate.exchange(
                "/games",
                HttpMethod.GET,
                null,
                responseType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }


    @Test
    void givenValidRentalDTO_whenCreatingRental_thenReturnsCreatedRental() {
        CustomerDTO customerDTO = new CustomerDTO("João", "12345678901");
        Customer customer = new Customer(1L, customerDTO.name(), customerDTO.cpf());
        this.customerRepository.save(customer);

        GameDTO gameDTO = new GameDTO(
                "BancoImobiliário",
                "http: //www.imagem.com.br/banco_imobiliario.jpg",
                3,
                1500);
        Game game = new Game(1L, gameDTO.name(), gameDTO.image(), gameDTO.stockTotal(), gameDTO.pricePerDay());
        this.gamesRepository.save(game);

        RentalDTO rentalDTO = new RentalDTO(customer.getId(), game.getId(), 3);

        ResponseEntity<Rental> response = restTemplate.postForEntity(
                "/rentals",
                rentalDTO,
                Rental.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(rentalDTO.daysRented(), response.getBody().getDaysRented());
        assertEquals(rentalDTO.gameId(), response.getBody().getGame().getId());
        assertEquals(rentalDTO.customerId(), response.getBody().getCustomer().getId());
    }
}
