package ru.danilov.voting.voting.models.restaurant;

import jakarta.persistence.*;

@Entity
@Table(name = "Dish")
public class Dish {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
}
