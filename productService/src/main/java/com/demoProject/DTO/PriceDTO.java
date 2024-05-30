package com.demoProject.DTO;

import com.demoProject.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceDTO {
    private UUID id;
    Currency currency;
    double price;
}
