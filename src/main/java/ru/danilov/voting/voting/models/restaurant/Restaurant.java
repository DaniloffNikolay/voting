package ru.danilov.voting.voting.models.restaurant;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Restaurant")
public class Restaurant {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "restaurant")
    private List<LunchMenu> lunchMenus;
}
