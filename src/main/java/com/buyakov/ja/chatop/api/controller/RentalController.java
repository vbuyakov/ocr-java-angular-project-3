package com.buyakov.ja.chatop.api.controller;

import com.buyakov.ja.chatop.api.dto.RentalDto;
import com.buyakov.ja.chatop.api.dto.RentalResponse;
import com.buyakov.ja.chatop.api.dto.RentalsResponse;
import com.buyakov.ja.chatop.api.dto.ResponseMessage;
import com.buyakov.ja.chatop.api.dto.validation.OnCreate;
import com.buyakov.ja.chatop.api.dto.validation.OnUpdate;
import com.buyakov.ja.chatop.api.service.RentalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<RentalsResponse> getAll() {
        return ResponseEntity.ok().body(rentalService.all());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RentalResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(rentalService.getById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseMessage> createRental(@Validated(OnCreate.class) @ModelAttribute RentalDto rentalDto) {

        try {
            rentalService.create(rentalDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Rental created !"));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>(new ResponseMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseMessage> updateRental(@PathVariable Long id, @Validated(OnUpdate.class) @ModelAttribute RentalDto rentalDto) {
        try {
            rentalService.update(rentalDto, id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Rental updated !"));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>(new ResponseMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


}