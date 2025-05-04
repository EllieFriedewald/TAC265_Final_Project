package model;

public class Tool extends WorldObject implements Craftable {
    private int durability;
    private ToolType toolType;
    private String level;

    public Tool(String name, int x, int y, boolean canPickUp, String description, ToolType toolType, String level) {
        super(name, x, y, canPickUp, description);
        this.toolType = toolType;
        this.level = level;
    }
    public Tool(String name, boolean canPickUp, String description, ToolType toolType, String level) {
        super(name,canPickUp,description);
        this.toolType = toolType;
        this.level = level;
    }

    public ToolType getType() {
        return toolType;
    }
    public String getLevel() {
        return level;
    }
    public int getDurability() {
        return durability;
    }

}
