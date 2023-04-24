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
}
