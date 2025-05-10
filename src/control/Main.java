package control;

import model.*;
import view.*;

import javax.crypto.spec.RC2ParameterSpec;
import javax.swing.*;
import java.io.File;
import java.util.*;

public class Main {
    private Player loggedInPlayer;
    private UI ui;
    private Map<String, Player> playerMap;
    private Map<String, Item> itemMap;

    private static final String DB_FILE = "playerDatabase.ser";

    public Main(){
        File playerDatabase = new File(DB_FILE);
        if(playerDatabase.exists()){
            playerMap = PlayerDatabaseSaver.readInDatabaseFromFile(DB_FILE);
            if(playerMap == null){
                System.out.println("failed to read db file");
                playerMap = new HashMap<>();
            }
            else{
                System.out.println("loaded players: " + playerMap.keySet());
            }
        }
        else{
            playerMap = new HashMap<>();
        }
        itemMap = ItemFileReader.readItems();
        loggedInPlayer = null;
        ui = new MyIO();
//        ui = new PopupUI("MINECRAFT");
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
                case CREATE_ACCOUNT -> createAccount();
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
                    quit();
                    quit = true;
                    PlayerDatabaseSaver.writeObjectToFile(playerMap, "playerDatabase.ser");
                }
            }
            if (!quit) { //pause
                if(ui instanceof MyIO) ui.readln("enter something to continue");
            }
        }
    }

    boolean playerIsLoggedIn(){
        return loggedInPlayer != null;
    }
    private void logout(){
        loggedInPlayer = null;
    }
    //start
    private String getUsername() {
        boolean login = false;
        String username = ui.readln("What would you like your username to be?");
        if(username == null || playerMap.containsKey(username)) {
            login = ui.readYesOrNo("That user is already taken would you like to login?");
            if(login) {
                login();
                return null;
            }
        }
        return username;
    }

    public void createAccount() {
        String username = getUsername();
        if(!playerMap.containsKey(username) && username != null) {
            String password = ui.readln("What would you like your password to be?");
            String confirmPass = ui.readln("Please retype your password to confirm it: ");
            if (!password.equals(confirmPass)) {
                ui.println("Sorry! Your account was not created, because the passwords did not match.");
            } else {
                PlayerLevel level = PlayerLevel.LEATHER;
                double health = 100.0;
                int numPlots = 0;
                Player newPlayer = new Player(username, password, level, numPlots, health);
                playerMap.put(username, newPlayer);
                loggedInPlayer = newPlayer;
                ui.println("Your account has successfully been created! Welcome" + username + ". Please now login to your account.");
            }
        }
        else{
            String prompt = ui.readln("This username has already been created. Would you like to try a different username or login to an existing account? type: different or login... " );
            if(prompt.equalsIgnoreCase("different")){
                createAccount();
            }
            else if(prompt.equalsIgnoreCase("login")){
                login();
            }
        }
    }

    private void login(){
        if(loggedInPlayer != null){
            logout();
        }
        String username = ui.readln("Please enter your username: ");
        if(playerMap.containsKey(username)) {
            Player player = playerMap.get(username);
            String password = ui.readln("Please enter your password: ");

            if (player.getPassword().equals(password)) {
                loggedInPlayer = player;
                ui.println("Your account has successfully been logged in! Welcome back " + username);
            } else {
                ui.println("Incorrect password!");
            }
        }
        else {
            ui.println("No account found with that username.");
        }
    }

    private void displayUsers(){
        String acc = "All Users:\n";
        int counter = 1;
        for(String player : playerMap.keySet()){
            acc += counter + ". " + player + "\n";
            counter++;
        }
        ui.println(acc);
    }

    private void displayStatus(){
        String acc = "All User Status:\n";
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
        ui.println("Items that drop from " + sourceBlock + ": " + items);
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
        return recipes.get(choice - 1); // corrected indexing
    }

    private void showRecipes(ArrayList<Recipe> recipes){
        String acc = "--=== Recipes ===--";
        int counter = 1;
        for(Recipe recipe: recipes){
            acc += counter + ". " + recipe.toString() + "\n";
        }
        ui.println(acc);
    }

    private void craftItem(){
        ArrayList<Recipe> recipes = recipeFileReader.readRecipes();
        Recipe selectedRecipe = getSelectedRecipe(recipes);
        if(loggedInPlayer.hasEnoughItems(selectedRecipe)){
            ArrayList<Item> ingredients = selectedRecipe.getIngredients();
            for(Item ingredient : ingredients){
                loggedInPlayer.removeItem(ingredients);
            }
            loggedInPlayer.addItem(selectedRecipe.getItemCrafted());
        }
        else{
            ui.println("You don't have enough items in your inventory to craft "+ selectedRecipe +"!");
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

    private void addPlayerToPlot(){
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
    private void removePlayerFromPlot(){
        if(!loggedInPlayer.hasPlot()){
            ui.println("You cannot remove a user from your plot because you have no plots! Please make a plot for yourself!");
        }
        else{
            String other = ui.readln("Please enter the username of the player you wish to remove: ");
            Player otherPlayer = playerMap.get(other);
            if(otherPlayer == null){
                ui.println("There is no player with that username!");
            }
            else{
                loggedInPlayer.getPlot().removePlayer(otherPlayer);
            }
        }
    }

    private void buildOnPlot(){
        Plot plot = loggedInPlayer.getPlot();
        if(!loggedInPlayer.hasPlot()){
            ui.println("You do not have a plot to build on because you have no plots!");
        }
        else if(!plot.getOwner().equals(loggedInPlayer) && !plot.getAllowedPlayers().contains(loggedInPlayer)){
            ui.println("You do not have permission to build on this plot!");
        }
        else{
            plot.build();
            ui.println("You have successfully built a house on your plot!");
        }
    }

    public void quit(){
        PlayerDatabaseSaver.writeObjectToFile(playerMap, DB_FILE);
        ui.println("Quitting...");
        System.exit(1);
    }
}
