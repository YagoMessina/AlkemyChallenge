package com.yago.Alkemy.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String image;

    @Column
    private String name;

    @Column
    private int age;

    @Column
    private float weight;

    @Column
    private String story;

    @Column
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Movie> movies;

    public Character() {
    }

    public Character(Long id, String image, String name,
                     int age, float weight,
                     String story, Set<Movie> movies) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.story = story;
        this.movies = movies;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
