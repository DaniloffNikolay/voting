package ru.danilov.voting.voting.models.restaurant;

import jakarta.persistence.*;

@Entity
@Table(name = "Lunch_menu_item")
public class LunchMenuItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Dish dish;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "lunch_menu_id")
    private LunchMenu lunchMenu;
}
