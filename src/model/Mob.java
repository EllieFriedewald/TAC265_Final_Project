package model;

import java.util.*;

public class Mob extends Entity {
    private List<Item> inventory;
    private boolean hostile;

    public Mob(String name, double health, List<Item> inventory, boolean hostile) {
        super(name, health);
        this.inventory = inventory;
        this.hostile = hostile;
    }
    public Mob(String name, double health) {
        super(name, health);
        this.inventory = new ArrayList<>(5);
        hostile = false;
    }

    public List<Item> dropItem() {
        return inventory;
    }

    public boolean isHostile() {
        return hostile;
    }

    public void setHostile(boolean hostile) {
        this.hostile = hostile;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Mob mob = (Mob) o;
        return Objects.equals(inventory, mob.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(inventory);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
