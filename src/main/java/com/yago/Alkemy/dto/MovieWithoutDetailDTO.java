package com.yago.Alkemy.dto;

import java.time.LocalDate;

public class MovieWithoutDetailDTO {

    private String image;

    private String title;

    private LocalDate creationDate;

    public MovieWithoutDetailDTO(String image, String title, LocalDate creationDate) {
        this.image = image;
        this.title = title;
        this.creationDate = creationDate;
    }
}
