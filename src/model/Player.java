package model;

import java.util.*;

public class Player extends Entity {
    private List<Item> inventory;
    private Map<ToolType, Tool> tools;
    private String username;
    private String level;
    private String password;
    private int numPlots;

    public Player(String username, String password, String level, int numPlots, double health) {
        super(username, health);
        this.password = password;
        this.level = level;
        this.numPlots = numPlots;
    }

    public Player(String name, String password, int numPlots) {
        super(name);
        this.password = password;
        this.numPlots = numPlots;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public int getNumPlots() {
        return numPlots;
    }
    public void setNumPlots(int numPlots) {
        this.numPlots = numPlots;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    /*
    This is for when the players get killed so that they drop their inventory because keepInventory = false;
     */
    public List<Item> dropItem() {
        return inventory; // potentially wanna clear the players inventory too, I may need an update method
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return numPlots == player.numPlots && Objects.equals(inventory, player.inventory) && Objects.equals(tools, player.tools) && Objects.equals(username, player.username) && Objects.equals(level, player.level) && Objects.equals(password, player.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventory, tools, username, level, password, numPlots);
    }

    @Override
    public String toString() {
        return username + ", is ranked " + level + " and they have " + numPlots + " plots";
    }
}
