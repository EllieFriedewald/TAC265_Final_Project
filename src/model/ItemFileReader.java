package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ItemFileReader {
    private static final String ITEM_FILE_NAME = "itemFile.txt";
    public static ArrayList<Item> readItems() {
        ArrayList<Item> items = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(ITEM_FILE_NAME); Scanner scan = new Scanner(fis)){
            scan.nextLine();
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                if(!line.isEmpty()) {
                    Item drops = parseLine(line);
                    items.add(drops);
                }
            }
        }
        catch(IOException e){
            System.err.println("File not found...");
            System.exit(1);
        }
        return items;
    }
    //String name, boolean canPickUp, String description
    private static Item parseLine(String line){
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
}
