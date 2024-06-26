package com.israelsantana.demo.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AboutMeUpdateDTO {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    private int age;
}
