package com.yago.Alkemy.dto;

import javax.validation.constraints.NotNull;

public class CharacterDTO {

    private Long id;

    @NotNull (message = "Image cannot be null.")
    private String image;

    @NotNull (message = "Name cannot be null.")
    private String name;

    @NotNull (message = "Age cannot be null.")
    private int age;

    @NotNull (message = "Weight cannot be null.")
    private float weight;

    @NotNull (message = "Story cannot be null.")
    private String story;

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public float getWeight() {
        return weight;
    }

    public String getStory() {
        return story;
    }
}
