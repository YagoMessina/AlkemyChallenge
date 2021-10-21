package com.yago.Alkemy.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String image;

    @Column
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Movie> films;

    public Genre() {
    }

    public Genre(String name, String image,
                 Set<Movie> films) {
        this.name = name;
        this.image = image;
        this.films = films;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Movie> getFilms() {
        return films;
    }

    public void setFilms(Set<Movie> films) {
        this.films = films;
    }
}
