package com.buyakov.ja.chatop.api.dto;

import com.buyakov.ja.chatop.api.model.Rental;
import lombok.Data;

import java.util.List;

@Data
public class RentalsResponse {
    private List<RentalResponse> rentals;
}
