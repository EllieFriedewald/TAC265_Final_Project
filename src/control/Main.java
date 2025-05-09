package control;

import model.*;
import view.*;

import javax.crypto.spec.RC2ParameterSpec;
import javax.swing.*;
import java.util.*;

public class Main {
    private Player loggedInPlayer;
    private UI ui;
    private Map<String, Player> playerMap;
    private Map<String, Item> itemMap;

    private static final String DB_FILE = "playerDatabase.ser";

    public Main(){
        playerMap = PlayerDatabaseSaver.readInDatabaseFromFile(DB_FILE);
        itemMap = (Map<String, Item>) ItemFileReader.readItems();
        loggedInPlayer = null;
        ui = new MyIO();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
        PlayerDatabaseSaver.writeObjectToFile(main.playerMap, "playerDatabase.ser");
    }

    private void run(){
        boolean quit = false;
        while (!quit){
            LoginMenu option;
            if(ui instanceof MyIO){
                int choice = ui.readInt(LoginMenu.getMenuString()+ "\n>",0,LoginMenu.values().length - 1);
                option = LoginMenu.getOptionNumber(choice);
            }
            else{
                option = (LoginMenu) ui.chooseFrom("Pick something you'd like to do!", (Object[])LoginMenu.values());
            }

            switch (option){
                case LOGIN -> login();
                case LOGOUT -> loggedInPlayer = null;
                case DISPLAY_USERS -> displayUsers();
                case DISPLAY_STATUS -> displayStatus();
                case MINE -> {
                    if(playerIsLoggedIn()){
                        mineBlock();
                    }
                }
                case SHOW_INVENTORY -> {
                    if(playerIsLoggedIn()){
                        showInventory();
                    }
                }
                case CRAFT -> {
                    if(playerIsLoggedIn()){
                        craftItem();
                    }
                }
                case MAKE_PLOT -> {
                    if(playerIsLoggedIn()){
                        makePlot();
                    }
                }
                case ADD_PLAYER -> {
                    if(playerIsLoggedIn()){
                        addPlayerToPlot();
                    }
                }
                case REMOVE_PLAYER -> {
                    if(playerIsLoggedIn()){
                        removePlayerFromPlot();
                    }
                }
                case BUILD ->{
                    if(playerIsLoggedIn()){
                        buildOnPlot();
                    }
                }
                case QUIT -> {
                    quit = true;
                    PlayerDatabaseSaver.writeObjectToFile(playerMap, DB_FILE);
                }
            }
        }
    }

    boolean playerIsLoggedIn(){
        return loggedInPlayer != null;
    }
    private void logout(){
        loggedInPlayer = null;
    }

    private void login(){
        if(loggedInPlayer != null){
            logout();
        }
        else{
            String username = ui.readln("Please enter your username: ");
            if(playerMap.containsKey(username)){
                Player player = playerMap.get(username);
                String password = ui.readln("Please enter your password: ");
                if(player.getPassword().equals(password)){
                    loggedInPlayer = player;
                }
                else{
                    ui.println("Incorrect password!");
                }
            }
        }
    }

    private void displayUsers(){
        String acc = "All Users: ";
        int counter = 1;
        for(String player : playerMap.keySet()){
            acc += counter + ". " + player + "\n";
            counter++;
        }
        ui.println(acc);
    }

    private void displayStatus(){
        String acc = "All User Status: ";
        int counter = 1;
        for(Player player : playerMap.values()){
            acc += counter + ". " + player.toString() + "\n";
            counter++;
        }
        ui.println(acc);
    }

    private BlockType getRandomBlockType(){
        List<BlockType> blockWeights = new ArrayList<>();
        for(BlockType type : BlockType.values()){
            for(int i = 0; i < type.getWeight(); i++){
                blockWeights.add(type);
            }
        }
        int randInx = new Random().nextInt(blockWeights.size());
        return blockWeights.get(randInx);
    }

    private Map<BlockType, List<Item>> getItemDropByBlockType(){
        Map<String, Item> items = ItemFileReader.readItems();
        Map<BlockType, List<Item>> dropMap = new HashMap<>();
        for(Item item : items.values()){
            BlockType block = item.getDropSource();
            dropMap.putIfAbsent(block, new ArrayList<>());
            dropMap.get(block).add(item);
        }
        return dropMap;
    }

    private Item getItemDrop(BlockType sourceBlock){
        List<Item> items = new ArrayList<>();
        for(Item item : itemMap.values()){
            if(item.getDropSource() == sourceBlock){
                items.add(item);
            }
        }
        if(items.isEmpty()){
            return null;
        }
        int randInx = new Random().nextInt(items.size());
        return items.get(randInx);
    }

    private void mineBlock(){
        BlockType minedBlock = getRandomBlockType();
        Item loot = getItemDrop(minedBlock);
        ui.println("You found and mined a " + minedBlock + " block!");
        if(loot != null){
            loggedInPlayer.addItem(loot);
            ui.println("From mining the " + minedBlock + " you get: " + loot.toString());
        }
    }

    private void showInventory(){
        ui.println("--=== Inventory ===--");
        Map<String, Integer> itemCount = loggedInPlayer.getInventory();
        final int rows = 4;
        final int cols = 9;
        final int totSlots = rows * cols;

        List<String> sortedNames = new ArrayList<>(itemCount.keySet());
        Collections.sort(sortedNames);

        List<String> cells = new ArrayList<>();
        for(String name : sortedNames){
            int count = itemCount.get(name);
            String cell = String.format("[%1$-10s]", name + "x" + count); // looked up different formatting options here so i could get one that i liked!
            cells.add(cell);
        }

        while(cells.size() < totSlots){ // filling the rest of the inventory with empty slots so that it will show full grid
            cells.add(String.format("[%1$-10s]", "Empty"));
        }
        // actually printing out the grid
        for(int i = 0; i < totSlots; i++){
            ui.print(cells.get(i) + " ");
            if((i+1) % cols == 0){ // mod so we can get even rows and cols, if 0 then make a new row!
                ui.print("\n");
            }
        }
    }

    private Recipe getSelectedRecipe(ArrayList<Recipe> recipes){
        int choice = ui.readInt("Choose a recipe number: ", 1, recipes.size());
        return recipes.get(choice);
    }

    private void showRecipes(ArrayList<Recipe> recipes){
        String acc = "--=== Recipes ===--";
        int counter = 1;
        for(Recipe recipe: recipes){
            acc += counter + ". " + recipe.toString() + "\n";
        }
    }

    private void craftItem(Recipe recipe){
        if(loggedInPlayer.hasEnoughItems(recipe)){
            ArrayList<Item> ingredients = recipe.getIngredients();
            for(Item ingredient : ingredients){
                loggedInPlayer.removeItem(ingredients);
            }
            loggedInPlayer.addItem(recipe.getItemCrafted());
        }
        else{
            ui.println("You don't have enough items in your inventory to craft "+ recipe +"!");
        }
    }

    private void makePlot(){
        if(!loggedInPlayer.hasPlot()){
            Plot p = new Plot(loggedInPlayer);
            loggedInPlayer.setPlot(p);
            ui.println("A plot has successfully been added to your user!");
        }
        else{
            ui.println("You already have a plot!");
        }
    }

    private void addPlayerToPlot(Player p){
        if(!loggedInPlayer.hasPlot()){
            ui.println("You cannot add a user to your plot because you have no plots! Please make a plot for yourself!");
        }
        else{
            String otherUser = ui.readln("Please enter the username of the player you wish to add to your plot: ");

            Player other = playerMap.get(otherUser);
            if(other == null){
                ui.println("There is no player with that username!");
            }
            else{
                loggedInPlayer.getPlot().addPlayer(other);
            }
        }
    }
    private void removePlayerFromPlot(Player p){
        if(!loggedInPlayer.hasPlot()){
            ui.println("You cannot remove a user from your plot because you have no plots! Please make a plot for yourself!");
        }
        else{
            String user = ui.readln("Please enter the username of the player you wish to remove: ");
            Player userPlayer = playerMap.get(user);
            if(userPlayer == null){
                ui.println("There is no player with that username!");
            }
            else{
                loggedInPlayer.getPlot().removePlayer(userPlayer);
            }
        }
    }

    private void buildOnPlot(){

    }

    public void quit(){
        PlayerDatabaseSaver.writeObjectToFile(playerMap, DB_FILE);
        ui.println("Quitting...");
        System.exit(1);
    }


}
