package com.yago.Alkemy.dto;

import javax.validation.constraints.NotNull;

public class GenreDTO {

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Image cannot be null")
    private String image;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
