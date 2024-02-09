package com.example.boardcamp;

import com.example.boardcamp.dtos.CustomerDTO;
import com.example.boardcamp.dtos.GameDTO;
import com.example.boardcamp.exceptions.CustomerConflictException;
import com.example.boardcamp.exceptions.CustomerNotFoundException;
import com.example.boardcamp.exceptions.GameConflictException;
import com.example.boardcamp.models.Customer;
import com.example.boardcamp.models.Game;
import com.example.boardcamp.repositories.CustomerRepository;
import com.example.boardcamp.repositories.GamesRepository;
import com.example.boardcamp.services.CustomerService;
import com.example.boardcamp.services.GamesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RecipeUnitTests {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private GamesRepository gamesRepository;

    @InjectMocks
    private GamesService gamesService;

    @Test
    void testGetCustomerById_ExistingCustomer() {
        Long customerId = 1L;
        Customer existingCustomer = new Customer(customerId, "John Doe", "12345678901");

        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(existingCustomer));

        Customer result = customerService.getCustomerById(customerId);

        assertNotNull(result);
        assertEquals(existingCustomer, result);
    }

    @Test
    void testGetCustomerById_NonExistingCustomer() {
        Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(customerId));
    }

    @Test
    void testCreateCustomer_NewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO("Jane Doe", "98765432109");
        Long customerId = 1L;
        Customer customer = new Customer(customerId, customerDTO.name(), customerDTO.cpf());

        when(customerRepository.existsByCpf(anyString())).thenReturn(false);
        when(customerRepository.save(any())).thenReturn(customer);

        Customer result = customerService.createCustomer(customerDTO);

        assertNotNull(result);
        assertEquals(customerDTO.name(), result.getName());
        assertEquals(customerDTO.cpf(), result.getCpf());
    }

    @Test
    void testCreateCustomer_ExistingCustomer() {
        CustomerDTO existingCustomerDTO = new CustomerDTO("John Doe", "12345678901");
        when(customerRepository.existsByCpf(existingCustomerDTO.cpf())).thenReturn(true);

        assertThrows(CustomerConflictException.class, () -> customerService.createCustomer(existingCustomerDTO));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testGetAllGames() {
        Game game1 = new Game(1L, "Game1", "image1", 10, 5000);
        Game game2 = new Game(2L, "Game2", "image2", 15, 7500);
        List<Game> gamesList = Arrays.asList(game1, game2);

        when(gamesRepository.findAll()).thenReturn(gamesList);

        List<Game> result = gamesService.getAllGames();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(gamesList, result);
    }

    @Test
    void testCreateGame_ExistingGame() {
        GameDTO existingGameDTO = new GameDTO("ExistingGame", "existingImage", 25, 1200);
        when(gamesRepository.existsByName(existingGameDTO.name())).thenReturn(true);

        assertThrows(GameConflictException.class, () -> gamesService.createGame(existingGameDTO));
        verify(gamesRepository, never()).save(any());
    }
}
