package com.buyakov.ja.chatop.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class RentalsResponse {
    private List<RentalResponse> rentals;
}
