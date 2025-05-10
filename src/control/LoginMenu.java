package control;

public enum LoginMenu {
    CREATE_ACCOUNT ("Create Account"),
    LOGIN ("Login"),
    LOGOUT ("logout"),
    DISPLAY_USERS ("Show all users"),
    DISPLAY_STATUS ("Show all users and their status"),
    MINE("Mine block"),
    SHOW_INVENTORY("Show inventory"),
    CRAFT("Craft an item on crafting table"),
    MAKE_PLOT("Create your plot"),
    ADD_PLAYER("Add a player to your plot"),
    REMOVE_PLAYER ("Remove a player from your plot"),
    BUILD ("Build a building on your plot"),
    QUIT ("Save and quit to tile");

    private final String option;

    LoginMenu(String description){
        this.option = description;
    }

    @Override
    public String toString() {
        return option;
    }
    public static LoginMenu getOptionNumber(int n){
        return LoginMenu.values()[n];
    }
    public static String getMenuString(){
        StringBuilder menuString = new StringBuilder("What would you like to do?\n");
        for(LoginMenu option : LoginMenu.values()){
            menuString.append(option.ordinal()).append(": ").append(option.option).append("\n");
        }
        return menuString.toString();
    }

}
