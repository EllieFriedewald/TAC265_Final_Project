package model;

public abstract class WorldObject {
    private String name;
    private int x;
    private int y;
    private boolean canPickUp;
    private String description;

    public WorldObject(String name, int x, int y, boolean canPickUp, String description) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.canPickUp = canPickUp;
        this.description = description;
    }
    public WorldObject(String name, int x, int y, boolean canPickUp) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public WorldObject(String name, boolean canPickUp, String description){
        this.name = name;
        this.canPickUp = canPickUp;
        this.description = description;
    }

    public abstract void interact(Player player);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
