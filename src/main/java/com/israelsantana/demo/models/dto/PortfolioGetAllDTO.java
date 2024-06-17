package com.israelsantana.demo.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PortfolioGetAllDTO {
    
    @JsonProperty("symbolAction")
    private String symbolAction;

    @JsonProperty("valueStock")
    private double valueStock;

    @JsonProperty("valuePurchased")
    private double valuePurchased;

    @JsonProperty("numberStockPurchased")
    private double numberStockPurchased;

    @JsonProperty("updateLastStock")
    private String updateLastStock;
}
