package com.yago.Alkemy.dto;

import com.yago.Alkemy.model.Character;
import com.yago.Alkemy.model.Genre;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

public class MovieDTO {

    private Long id;

    @NotNull(message = "Image cannot be null.")
    private String image;

    @NotNull (message = "Title cannot be null.")
    private String title;

    @NotNull (message = "Creation Date cannot be null.")
    private LocalDate creationDate;

    @NotNull (message = "Qualification cannot be null.")
    private Float qualification;

    @NotNull (message = "Characters cannot be null.")
    @Valid
    private Set<CharacterDTO> characters;

    @NotNull (message = "Genre cannot be null.")
    private GenreDTO genre;

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public float getQualification() {
        return qualification;
    }

    public Set<CharacterDTO> getCharacters() {
        return characters;
    }

    public GenreDTO getGenre() {
        return genre;
    }
}
