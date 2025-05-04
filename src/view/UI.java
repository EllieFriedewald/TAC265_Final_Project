package view;

import java.time.LocalDate;

// New and improved UI interface April 2025
public interface UI {
    // Display methods
    public void print(Object output);
    public  void printFancy(String output);
    public void println(Object output);
    public void println(String output);

    //Input Primitives
    public int readInt(String prompt) ;
    public int readInt(String prompt, int minValue, int maxValue) ;
    public int readInt(String prompt, int minValue, int maxValue, int quit) ;
    public boolean readBoolean(String prompt);
    public double readDouble(String prompt);
    public double readDouble(String prompt, double min, double max);
    public boolean readYesOrNo(String prompt) ;

    //Input Strings or other reference data types
    public String readln(String prompt);
    public String readln(String prompt, String... matches);
    public Object chooseFrom(String prompt, Object... options);
    public LocalDate inputDate(String prompt);

}

