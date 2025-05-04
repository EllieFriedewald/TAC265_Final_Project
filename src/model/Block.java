package model;

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

    public void interact(Player player,Block block) {
        if(block.isBreakable()) {
            Tool playerTool = player.getSelectedTool();
            if(playerTool != null && playerTool.getType().equals(block.getTool())){
                System.out.println("You broke " + block);
            }
        }
    }


}
