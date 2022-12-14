package me.leshchenkor.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PLACE")
public class Place {

    private Long id;
    private String city;
    private String street;
    private String house;

    public Place(String city, String street, String house) {
        this.city = city;
        this.street = street;
        this.house = house;
    }

    public Place() {
    }

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "STREET")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "HOUSE")
    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }
}
