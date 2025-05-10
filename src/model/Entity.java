package model;

import java.util.*;

public abstract class Entity {
    private String name; // username in player
    private double health;


    public Entity(String name, double health) {
        this.name = name;
        this.health = health;
    }

    public Entity(String name) {
        health = 10.0;
        this.name = name;
    }

    public abstract List<Item> dropItem();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHealth() {
        return health;
    }
    public void setHealth(double health) {
        this.health = health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString() {
        return name + " has " + health + " hearts";
    }
}
