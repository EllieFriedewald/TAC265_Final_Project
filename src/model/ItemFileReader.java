package model;

import java.io.*;
import java.util.*;

public class ItemFileReader {
    private static final Map<String, Item> itemMap = new HashMap<>();
    private static final String ITEM_FILE_NAME = "itemFile.txt";
    public static Map<String, Item> readItems() {
        try(FileInputStream fis = new FileInputStream(ITEM_FILE_NAME); Scanner scan = new Scanner(fis)){
            scan.nextLine();
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                if(!line.isEmpty()) {
                    Item drops = parseLine(line);
                    if(drops != null) {
                        itemMap.put(drops.getName(), drops);
                    }
                }
            }
        }
        catch(IOException e){
            System.err.println("File not found...");
            System.exit(1);
        }
        return itemMap;
    }
    //String name, boolean canPickUp, String description
    private static Item parseLine(String line){
        if(line.isEmpty()) {
            throw new IllegalArgumentException("Line is empty");
        }
        Scanner ls = new Scanner(line);
        ls.useDelimiter("\t");
        String name = ls.next();
        String pickUp = ls.next();
        boolean canPickUp = false;
        if(pickUp.equals("yes")) {
            canPickUp = true;
        }
        String description = ls.next();
        String from = ls.next();
        return new Item(name, canPickUp, description, from);
    }

    public static Item getItemByName(String name){
        return itemMap.get(name);
    }
}
