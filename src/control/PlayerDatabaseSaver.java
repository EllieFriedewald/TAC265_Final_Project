package control;
import java.io.*;
import java.util.*;
import model.*;

public class PlayerDatabaseSaver {
    public static Map<String, Player> readInDatabaseFromFile(String filename){
        Map<String, Player> playerMap = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)){
            Object o = ois.readObject();
            if (o instanceof Map){
                playerMap = (Map<String, Player>) o;
            }
        }
        catch (Exception e){
            System.err.println("Error caught in readInDatabaseFromFile: " + e);
            System.out.println("Couldn't read in database from file, will need to create new empty database :P");
        }
        if(playerMap == null){
            System.out.println("Couldn't read in database from file, will need to create new empty database :p");
        }
        return playerMap;
    }
    public static void writeObjectToFile(Map<String, Player> player, String fileName){
        try (FileOutputStream fos = new FileOutputStream(fileName);ObjectOutputStream oos = new ObjectOutputStream(fos)){
            System.out.println("\t writing to file: " + fileName);
            oos.writeObject(player);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
