package ru.danilov.voting.voting.models;

import jakarta.persistence.*;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.restaurant.Restaurant;

import java.time.LocalDateTime;

@Entity
@Table(name = "Vote")
public class Vote {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @OneToOne()
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(int id, LocalDateTime dateTime, Person person, Restaurant restaurant) {
        this.id = id;
        this.dateTime = dateTime;
        this.person = person;
        this.restaurant = restaurant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
