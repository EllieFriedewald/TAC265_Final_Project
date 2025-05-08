package model;

import java.util.*;

public class Item extends WorldObject {
    private String from;
    private final BlockType dropSource;

    public Item(String name, boolean canPickUp, String description, String from) {
        super(name, canPickUp, description);
        this.dropSource = BlockType.valueOf(from.toUpperCase());
    }
    public Item(String name, boolean canPickUp) {
        this(name, canPickUp, "A mysterious item...", "Dont know where from");
    }
    public Item(String name) {
        this(name, true, "A mysterious item...", "Dont know where from");
    }

    public BlockType getDropSource() {
        return dropSource;
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
