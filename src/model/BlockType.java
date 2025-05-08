package model;

public enum BlockType {
    DIRT(60),
    GRASS(40),
    SAND(40),
    STONE(40),
    COBBLESTONE(12),
    OAK_WOOD (20),
    LEAVES (8),
    IRON_ORE(7),
    GOLD_ORE (6),
    DIAMOND_ORE (3),
    BEDROCK (1);

    private final int weight;
    private BlockType(int weight) {
        this.weight = weight;
    }
    public int getWeight() {
        return weight;
    }
}
