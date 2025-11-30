package com.buyakov.ja.chatop.api.service;

import com.buyakov.ja.chatop.api.dto.RentalDto;
import com.buyakov.ja.chatop.api.dto.RentalResponse;
import com.buyakov.ja.chatop.api.dto.RentalsResponse;
import com.buyakov.ja.chatop.api.mapper.RentalMapper;
import com.buyakov.ja.chatop.api.model.Rental;
import com.buyakov.ja.chatop.api.model.User;
import com.buyakov.ja.chatop.api.repository.RentalRepository;
import com.buyakov.ja.chatop.api.security.AuthUserProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final AuthUserProvider authUserProvider;
    private final RentalMapper rentalMapper;
    private final FileStorageService fileStorageService;

    public Rental create(RentalDto rentalDto) throws IOException {
        log.info("Creating rental with dto: {}", rentalDto);
        User currentUser = authUserProvider.getCurrentUser();
        String storedFilename = fileStorageService.store(rentalDto.getPicture(), "/rentals");

        Rental rental = rentalMapper.createToEntity(rentalDto, storedFilename,  currentUser);
        return rentalRepository.save(rental);
    }

    public Rental update(RentalDto rentalDto, Long id) throws IOException, PermissionDeniedDataAccessException
            , EntityNotFoundException  {
        log.info("Updating rental with  and dto: {}", rentalDto);
        User currentUser = authUserProvider.getCurrentUser();

        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found: "));

        if (!currentUser.getId().equals(rental.getOwner().getId())) {
            throw new PermissionDeniedDataAccessException("You are not the owner of this rental", null);
        }

        rentalMapper.updateEntityFromDto(rentalDto, rental);

        if(rentalDto.getPicture() != null && !rentalDto.getPicture().isEmpty()) {
            String storedFilename = fileStorageService.store(rentalDto.getPicture(), "/rentals");
            rental.setPicture(storedFilename);
        }

        return rentalRepository.save(rental);
    }

    public RentalsResponse all() {
        List<Rental> rentals = rentalRepository.findAll();
        List<RentalResponse> mappedRentals = rentalMapper.toResponseList(rentals);

        RentalsResponse rentalsResponse = new RentalsResponse();
        rentalsResponse.setRentals(mappedRentals);
        return rentalsResponse;
    }

    public RentalResponse getById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found: "));
        return rentalMapper.toResponse(rental);
    }
}
