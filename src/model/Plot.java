package model;
import java.io.Serializable;
import java.util.*;

public class Plot implements Serializable {

    private Player owner;
    private List<Player> allowedPlayers;
    private boolean hasBuild;

    public Plot(Player owner){
        this.owner = owner;
        this.allowedPlayers = new ArrayList<>();
        this.hasBuild = false;
    }

    public Player getOwner() {
        return owner;
    }

    public List<Player> getAllowedPlayers() {
        return allowedPlayers;
    }

    public boolean isHasBuild() {
        return hasBuild;
    }

    public void build(){
        if(!hasBuild){
            hasBuild = true;
            System.out.println("A build was successfully placed on your plot!");
        }
        else{
            System.out.println("You already have a build on your plot!");
        }
    }
    public void addPlayer(Player player){
        if(!allowedPlayers.contains(player)){
            allowedPlayers.add(player);
        }
        else{
            System.out.println(player.getName() + " already has already been added to your plot!");
        }
    }

    public void removePlayer(Player player){
        if(!allowedPlayers.contains(player)){
            allowedPlayers.remove(player);
        }
        else{
            System.out.println(player.getName() + " already has already been removed from your plot!");
        }
    }

    public boolean canAccessPlot(Player player){
        return player.equals(owner)|| allowedPlayers.contains(player);
    }
}
