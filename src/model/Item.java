package model;

import java.util.*;

public class Item extends WorldObject {
    private String effect;
    private String description;

    public Item(String name, boolean canPickUp, String description, String effect) {
        super(name, canPickUp, description);
        this.effect = effect;
    }
    public String getEffect() {
        return effect;
    }
    public String getDescription() {
        return description;
    }

}
