package model;
import java.util.*;

public class Recipe {
    private Item itemCrafted;
    private ArrayList<Item> ingredients;

    public Recipe(Item itemCrafted, ArrayList<Item> ingredients) {
        this.itemCrafted = itemCrafted;
        this.ingredients = ingredients;
    }

    public Item getItemCrafted() {
        return itemCrafted;
    }


    public ArrayList<Item> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return itemCrafted.toString();
    }
}
