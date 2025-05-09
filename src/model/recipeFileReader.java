package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class recipeFileReader {
    private static final String RECIPIES = "recipes.txt"; // change lo
    public static ArrayList<Recipe> readRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(RECIPIES); Scanner scan = new Scanner(fis)){
            scan.nextLine(); // header
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                if(!line.isEmpty()) {
                    Recipe recipe = parseLine(line);
                    recipes.add(recipe);
                }
            }
        }
        catch(IOException e){
            System.err.println("File not found...");
            System.exit(1);
        }
        return recipes;
    }
    //itemToCraft, ingredient1, ingredient2, ingredient3, ingredient4, ingredient5, ingredient6, ingredient7, ingredient8, ingredient9
    private static Recipe parseLine(String line){
        Scanner ls = new Scanner(line);
        ls.useDelimiter("\t");
        Item crafted = null;
        String craftedItem = ls.next();
        if(!craftedItem.isEmpty()) {
            crafted = ItemFileReader.getItemByName(craftedItem);
        }
        ArrayList<Item> ingredients = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            String ingredientStr = ls.next();
            if(!ingredientStr.isEmpty()) {
                Item ingredient = ItemFileReader.getItemByName(ingredientStr);
                if(ingredient != null) {
                    ingredients.add(ingredient);
                }
            }
        }

        return new Recipe(crafted, ingredients);
    }
}
