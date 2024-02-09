package com.example.boardcamp.services;

import com.example.boardcamp.dtos.RentalDTO;
import com.example.boardcamp.exceptions.CustomerNotFoundException;
import com.example.boardcamp.exceptions.GameNotFoundException;
import com.example.boardcamp.exceptions.RentalNotFoundException;
import com.example.boardcamp.exceptions.RentalUnprocessableException;
import com.example.boardcamp.models.Customer;
import com.example.boardcamp.models.Game;
import com.example.boardcamp.models.Rental;
import com.example.boardcamp.repositories.CustomerRepository;
import com.example.boardcamp.repositories.GamesRepository;
import com.example.boardcamp.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GamesRepository gameRepository;

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental createRental(RentalDTO rentalDTO) {
        Customer customer = customerRepository.findById(rentalDTO.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found", HttpStatus.NOT_FOUND));

        Game game = gameRepository.findById(rentalDTO.gameId())
                .orElseThrow(() -> new GameNotFoundException("Game not found", HttpStatus.NOT_FOUND));

        validateAvailableStock(game);

        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setGame(game);
        rental.setRentDate(LocalDate.now());
        rental.setDaysRented(rentalDTO.daysRented());
        rental.setReturnDate(null);
        rental.setOriginalPrice(rentalDTO.daysRented() * game.getPricePerDay());
        rental.setDelayFee(0);

        return rentalRepository.save(rental);
    }

    public Rental returnRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RentalNotFoundException("Rental não encontrado", HttpStatus.NOT_FOUND));

        if (rental.getReturnDate() != null) {
            throw new RentalUnprocessableException("Rental já está finalizado", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        rental.setReturnDate(LocalDate.now());
        rental.setDelayFee(calculateDelayFee(rental));

        return rentalRepository.save(rental);
    }

    private void validateAvailableStock(Game game) {
        List<Rental> openRentals = rentalRepository.findByGameAndReturnDateIsNull(game);
        int rentedStock = openRentals.stream().mapToInt(Rental::getDaysRented).sum();
        if (rentedStock >= game.getStockTotal()) {
            throw new RentalUnprocessableException("Game sem estoque", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private int calculateDelayFee(Rental rental) {
        LocalDate returnDate = rental.getReturnDate();
        LocalDate expectedReturnDate = rental.getRentDate().plusDays(rental.getDaysRented());

        if (returnDate.isAfter(expectedReturnDate)) {
            int daysOfDelay = (int) returnDate.datesUntil(expectedReturnDate).count();
            return daysOfDelay * rental.getGame().getPricePerDay();
        }

        return 0;
    }
}
