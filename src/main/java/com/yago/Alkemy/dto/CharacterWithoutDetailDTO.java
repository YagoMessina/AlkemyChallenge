package com.yago.Alkemy.dto;

public class CharacterWithoutDetailDTO {

    private String image;

    private String name;

    public CharacterWithoutDetailDTO(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
