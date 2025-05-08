package control;

import model.*;
import view.*;

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
        ArrayList<Item> items = ItemFileReader.readItems();
        Map<BlockType, List<Item>> dropMap = new HashMap<>();
        for(Item item : items){
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
            loggedInPlayer.addToInventory(loot);
            ui.println("From mining the " + minedBlock + " you get: " + loot.toString());
        }
    }

}
