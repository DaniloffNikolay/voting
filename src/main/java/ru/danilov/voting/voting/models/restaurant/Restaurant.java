package ru.danilov.voting.voting.models.restaurant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Restaurant")
public class Restaurant {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<LunchMenu> lunchMenus;

    public Restaurant() {
    }

    public Restaurant(int id, String name, List<LunchMenu> lunchMenus) {
        this.id = id;
        this.name = name;
        this.lunchMenus = lunchMenus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LunchMenu> getLunchMenus() {
        return lunchMenus;
    }

    public void setLunchMenus(List<LunchMenu> lunchMenus) {
        this.lunchMenus = lunchMenus;
    }
}
