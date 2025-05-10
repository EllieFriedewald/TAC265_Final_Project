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

        if(playerMap == null) {
            System.out.println("Coudn't read in database from file, will need to create new empty database :-(");
            playerMap = new HashMap<>(); //make an empty database instead of having a null
        }
        return playerMap;
    }
    public static void writeObjectToFile(Map<String, Player> player, String fileName){
        try (FileOutputStream fs = new FileOutputStream(fileName)){
            System.out.println("\t writing to file: " + fileName); //message
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(player); //write the thing to a file in a machine-readable way
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
