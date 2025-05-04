package model;

import java.util.Objects;

public class Block extends WorldObject {
    private boolean isBreakable;
    private Tool tool;

    public Block(String name, int x, int y, boolean canPickup, boolean isBreakable, Tool tool) {
        super(name,x,y, false);
        this.isBreakable = isBreakable;
        this.tool = tool;
    }

    public Tool getTool() {
        return tool;
    }

    public boolean isBreakable() {
        return isBreakable;
    }

    public String getName(){
        return super.getName();
    }

    @Override
    public void interact(Player player) {
        Tool playerTool = player.getSelectedTool();
        if(isBreakable && playerTool != null && playerTool.getType().equals(tool.getType())) {
            System.out.println("You broke " + getName() + " with a " + playerTool.getType());
        }
        else {
            System.out.println("you couldn't brake " + getName() + " because you have a " + playerTool.getType());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return isBreakable == block.isBreakable && Objects.equals(tool, block.tool);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isBreakable, tool);
    }
}
