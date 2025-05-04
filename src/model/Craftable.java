package model;
import java.util.*;

public interface Craftable {
    Map<String, Integer> getRecipe();
    String getCraftedItemName();
}
