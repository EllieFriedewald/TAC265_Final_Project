package model;

import java.util.*;

public class Tool extends WorldObject implements Craftable {
    private int durability;
    private ToolType toolType;
    private String level;
    private Map<String, Integer> recipe;

    public Tool(String name, int x, int y, boolean canPickUp, String description, ToolType toolType, String level) {
        super(name, x, y, canPickUp, description);
        this.toolType = toolType;
        this.level = level;
        this.recipe = new HashMap<>();
    }
    public Tool(String name, boolean canPickUp, String description, ToolType toolType, String level) {
        super(name,canPickUp,description);
        this.toolType = toolType;
        this.level = level;
        this.recipe = new HashMap<>();
    }
    public Tool(String name, String description, ToolType toolType, String level, HashMap<String, Integer> recipe) {
        super(name, description);
        this.recipe = new HashMap<>(recipe);
        this.toolType = toolType;
        this.level = level;
    }



    public ToolType getType() {
        return toolType;
    }
    public String getLevel() {
        return level;
    }
    public int getDurability() {
        return durability;
    }



    @Override
    public void interact(Player player) {
        System.out.println("You are inspecting the " + toolType);
    }

    @Override
    public Map<String, Integer> getRecipe() {
        return recipe;
    } //CRAFTABLE INTERFACE

    @Override
    public String getCraftedItemName() {
        return getName();
    } //CRAFTABLE INTERFACE
    // when you use the tool, the durability will go down by 1
    public void use(){
        durability--;
    }


    @Override
    public String toString() {
        return getLevel() + " " + getName() + "[" + getDurability() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tool tool = (Tool) o;
        return durability == tool.durability && toolType == tool.toolType && Objects.equals(level, tool.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(durability, toolType, level);
    }
}
