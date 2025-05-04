package model;

import java.util.*;

public class Item extends WorldObject {
//    private String description;

    public Item(String name, boolean canPickUp, String description) {
        super(name, canPickUp, description);
    }
    public Item(String name, boolean canPickUp) {
        super(name, canPickUp, "A mysterious item...");
    }
    public Item(String name) {
        super(name, true, "A mysterious item...");
    }

    @Override
    public void interact(Player player) {
        System.out.println("You inspect the " + getName());
    }

    @Override
    public String toString() {
        return getName() + " - " + getDescription();
    }
}
