package ru.danilov.voting.voting.models.restaurant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Lunch_menu")
public class LunchMenu {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "lunchMenu")
    @JsonIgnore
    private List<LunchMenuItem> lunchMenuItems;

    public LunchMenu() {
    }

    public LunchMenu(int id, LocalDate date, Restaurant restaurant, List<LunchMenuItem> lunchMenuItems) {
        this.id = id;
        this.date = date;
        this.restaurant = restaurant;
        this.lunchMenuItems = lunchMenuItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<LunchMenuItem> getLunchMenuItems() {
        return lunchMenuItems;
    }

    public void setLunchMenuItems(List<LunchMenuItem> lunchMenuItems) {
        this.lunchMenuItems = lunchMenuItems;
    }
}
