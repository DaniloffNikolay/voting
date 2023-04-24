package ru.danilov.voting.voting.models.restaurant;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private LunchMenu lunchMenu;

    public LunchMenuItem() {
    }

    public LunchMenuItem(int id, Dish dish, double price, LunchMenu lunchMenu) {
        this.id = id;
        this.dish = dish;
        this.price = price;
        this.lunchMenu = lunchMenu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LunchMenu getLunchMenu() {
        return lunchMenu;
    }

    public void setLunchMenu(LunchMenu lunchMenu) {
        this.lunchMenu = lunchMenu;
    }
}
