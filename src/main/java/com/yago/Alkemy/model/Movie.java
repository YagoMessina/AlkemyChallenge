package com.yago.Alkemy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String image;

    @Column
    private String title;

    @Column
    private LocalDate creationDate;

    @Column
    private float qualification;

    @Column
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("movies")
    private Set<Character> characters;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("movies")
    private Genre genre;

    public Movie() {
    }

    public Movie(Long id, String image,
                 String title, LocalDate creationDate,
                 float qualification, Set<Character> characters,
                 Genre genre) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.creationDate = creationDate;
        this.qualification = qualification;
        this.characters = characters;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public float getQualification() {
        return qualification;
    }

    public void setQualification(float qualification) {
        this.qualification = qualification;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }

    public Genre getGenre() {
        return genre;
    }
}
