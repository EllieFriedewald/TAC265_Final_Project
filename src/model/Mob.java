package model;

import java.util.*;

public class Mob extends Entity {
    private int attackDamage;
    private boolean isHostile;

    public Mob(String name, double health, boolean ishostile, int attackDamage) {
        super(name, health);
        this.isHostile = ishostile;
        this.attackDamage = attackDamage;

    }
    public Mob() {
        super("Unknown Mob", 10.0);
        this.attackDamage = 5;
        this.isHostile = false;
    }

    @Override
    public List<Item> dropItem(){
        List<Item> items = new ArrayList<>();
        items.add(new Item("Experience"));
        return items;
    }

    public boolean isHostile() {
        return isHostile;
    }

    public int attack(){
        return attackDamage;
    }

    public void takeDamage(int damage){
        double newHealth = getHealth() - damage;
        setHealth(newHealth);
    }
    public boolean isAlive(){
        return getHealth() > 0;
    }
    public int getAttackDamage(){
        return attackDamage;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
