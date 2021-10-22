package com.yago.Alkemy.dto;

import com.yago.Alkemy.model.Character;

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
    private float qualification;

    @NotNull (message = "Characters cannot be null.")
    private Set<Character> characters;

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

    public Set<Character> getCharacters() {
        return characters;
    }
}
