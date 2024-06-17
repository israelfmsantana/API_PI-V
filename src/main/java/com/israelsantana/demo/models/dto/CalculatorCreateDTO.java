package com.israelsantana.demo.models.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalculatorCreateDTO {

   @NotNull
    private Long userId;

    @NotNull
    private Long actionId;

    @NotNull
    private String symbolAction;

    @NotNull
    private Double valueStock;

    @NotNull
    private String date;
}
