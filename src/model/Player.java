package model;

import java.io.Serializable;
import java.util.*;

public class Player extends Entity implements Serializable {
    private Map<String, Integer> inventory;
    private List<Tool> tools;
    private String username;
    private PlayerLevel level;
    private String password;
    private int numPlots;
    private Plot plot;
    private int selectedToolindx;

    public Player(){
        this("3LL13", "apple", PlayerLevel.LEATHER, 0, 100.0);
    }
    public Player(String username, String password, PlayerLevel level, int numPlots, double health) {
        super(username, health);
        this.password = password;
        this.level = level;
        this.numPlots = numPlots;
        this.inventory = new HashMap<>();
    }
    public Player(String username, String password, PlayerLevel level, int numPlots) {
        this(username, password, level, numPlots, 100.0);
    }
    public Player(String username, String password, PlayerLevel level) {
        this(username, password, level, 0);
    }
    public Player(String username, String password) {
        this(username, password, PlayerLevel.LEATHER, 0, 100.0);
    }


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public PlayerLevel getLevel() {
        return level;
    }
    public void setLevel(PlayerLevel level) {
        this.level = level;
    }
    public int getNumPlots() {
        return numPlots;
    }
    public void setNumPlots(int numPlots) {
        this.numPlots = numPlots;
    }
    public String getUsername() {
        return username;
    }
    public void setInventory(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }
    public List<Tool> getTools() {
        return tools;
    }
    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Tool getSelectedTool(){
        if(selectedToolindx >= 0 && selectedToolindx < tools.size()){
            return tools.get(selectedToolindx);
        }
        return null;
    }
    public void addItem(Item item) {
        inventory.put(item.getName(), inventory.getOrDefault(item.getName(), 0) + 1);
    }

    public boolean removeItem(Item item, int quantity) {
        String itemName = item.getName();
        if(!inventory.containsKey(itemName) || !(inventory.get(itemName) < quantity)){
            return false;
        }
        inventory.put(itemName, inventory.get(itemName) - quantity); // decrease amount by 1
        if(inventory.get(itemName) == 0){
            inventory.remove(itemName); // if there are none left then remove the item completely
        }
        return true;
    }

    public boolean hasEnoughItems(Recipe recipe) {
        for(Item ingredient: recipe.getIngredients()){
            if(!inventory.containsKey(ingredient.getName()) || inventory.get(ingredient.getName()) == 0){
                return false;
            }
        }
        return true;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
//    public Tool getSelectedTool(){
//        if(selectedToolindx >= 0 && selectedToolindx < tools.size()){
//            return tools.get(selectedToolindx);
//        }
//        return null;
//    }
    /*
    This is for when the players get killed so that they drop their inventory because keepInventory = false;
     */
    public List<Item> dropItem() {
        List<Item> droppedItems = new ArrayList<>((Collection) inventory);
        inventory.clear();
        return droppedItems;
    }

    public boolean hasPlot(){
        return plot!= null;
    }
    public void setPlot(Plot plot) {
        this.plot = plot;
    }
    public Plot getPlot() {
        return plot;
    }

//    public void upgradeRankIfEligible(){
// VERSION 2
//    }

    public void takeDamage(int damage){
        double newHealth = getHealth() - damage;
        setHealth(newHealth);
    }

    public boolean removeItemByName(String itemName){
        if(!inventory.containsKey(itemName) || inventory.get(itemName) == 0){
            return false;
        }
        inventory.put(itemName, inventory.get(itemName) - 1);
        if(inventory.get(itemName) == 0){
            inventory.remove(itemName);
        }
        return true;
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
        return getName() + ", is ranked " + level + " and they have " + numPlots + " plots";
    }
}
