package ru.rompet.shop.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProductDto {

    private Long id;

    @NotNull
    @NotEmpty
    private String article;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @Min(0)
    private String price;

    @NotNull
    private String category;

    @NotNull
    private String brand;

    @NotNull
    private String model;

    @NotNull
    private String color;

    @NotNull
    private String imageUrl;
}
