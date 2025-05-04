package view;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Scanner;

/**
 *  view.MyIO -- an alternative version of Helper/IO that does not really on Java 23 preview features
 *  of the language and does better error checking for input
 * @author Kendra Walther and
 * * TAC265
 * @version updated April 2025
 */
public class MyIO implements UI {

    private  Scanner sc; //instance variable

    public MyIO(){
        sc = new Scanner(System.in); //create the Scanner
    }

    /**
     * Method print Short-cut for System.out.print
      *
     * @param obj The object to be printed (Will work with primitives and reference data types BECAUSE java will automatically "box" primitive types in the
     *                                      corresponding Wrapper class)
     */
    public  void print(Object obj){
        System.out.print(obj);
    }

    /**
     * Method println Short-cut for System.out.println()
     * */
    public  void println(){
        System.out.println();
    }

    /**
     * Method println Short-cut for System.out.println*
     * @param obj The object to be printed (Will work with primitives and reference data types BECAUSE java will automatically "box" primitive types in the
     *                                      corresponding Wrapper class)
     */
    public void println(Object obj){
        System.out.println(obj);
    }

    /**
     * Method println Short-cut for System.out.println
     *
     * @param s The String to be printed
     */
    public void println(String s){
        System.out.println(s);
    }

    /**
     * Prints the given String between a set of 50 stars
     * @param msg the message to be displayed
     */
    public void printFancy(String msg){
        System.out.println("*".repeat(50));
        System.out.println(msg);
        System.out.println("*".repeat(50));
    }
    /**
     * Method readInt: gets an int value based on user input.
     * @param prompt The question to ask the user
     * @return the int value the user types in
     */
    public int readInt(String prompt){
        print(prompt + " > ");
        while(!sc.hasNextInt()){ // make sure there is an int waiting for us.
            String garbage = sc.nextLine(); // grab the "bad data"
            System.err.println(garbage + " was not an int.");
            print(prompt + " > ");
        } //here, we know an int is waiting on System.in

        int num = sc.nextInt(); // grab the number
        String extra =  sc.nextLine(); // get rid of return or anything after the int to clear the stream.
        return num;

    }
    /**
     * Method readInt: gets an int value between min and max (inclusive) based on user input.
     *
     * @param prompt The question to ask the user
     * @param min The min allowed int value for the user input (inclusive)
     * @param max The max allowed int value for the user input (inclusive)
     * @return an int between [min, max]
     */
    public int readInt(String prompt, int min, int max){
        int num = readInt(prompt); //get a number
        while (num < min || num > max) {
            System.err.println(num + " is invalid, Choose a num " + min + " to " + max);
            num = readInt(prompt); //get a new number
        }
        return num;
    }
    /**
     * Method readInt: gets an int value between min and max (inclusive) based on user input.
     *
     * @param prompt The question to ask the user
     * @param minValue The min allowed int value for the user input (inclusive)
     * @param maxValue The max allowed int value for the user input (inclusive)
    * @param quitValue The max allowed int value for the user input (inclusive)
     * @return an int between [min, max]  or equal to the quitValue
     */
    public int readInt(String prompt, int minValue, int maxValue, int quitValue) {
        int num = readInt(prompt); //get a number
        while (! ( num >= minValue && num <= maxValue) || num == quitValue){
            System.err.println(num + " is invalid, Choose a num " + minValue + " to " + maxValue + " or " + quitValue + " for quit");
            num = readInt(prompt); //get a new number
        }
        return num;
    }

    /**
     * Method readDouble: gets a double value based on user input.
     *
     * @param prompt The question to ask the user
     * @return the double value the user types in
     */
    public double readDouble(String prompt){
        print(prompt + " > ");
        while(!sc.hasNextDouble()){
            String garbage = sc.nextLine(); // grab the "bad data"
            System.err.println(garbage + " was not a double.");
            print(prompt + " > ");
        } //here, we know a double is waiting on System.in
        double num = sc.nextDouble(); // grab the number
        sc.nextLine(); //clear the inputStream
        return num;
    }

    /**
     * Method readDouble: gets a double value based on user input.
     * @param prompt The question to ask the user
     * @param min The min allowed double value for the user input (inclusive)
     * @param max The max allowed double value for the user input (inclusive)
     * @return a double between [min, max]
     */
    public double readDouble(String prompt, double min, double max){
        double num = readDouble(prompt); //get a number
        while (num < min || num > max) {
            System.err.println(num + " is invalid, Choose a num " + min + " to " + max);
            num = readDouble(prompt); //get a new number
        }
        return num;
    }
    /**
     * Method readBoolean: prompts the user to answer a true/false question, looping until a valid response is given
     *
     * @param prompt The question to ask the user
     * @return a boolean value
     */
    public boolean readBoolean(String prompt) {
        String userInput = readln(prompt).toLowerCase(); // chaining two methods together.
        while(! (userInput.equals("true")|| userInput.equals("false"))){
            System.err.println(userInput + " is invalid, Must type \"true\" or \"false\".");
            userInput = readln(prompt).toLowerCase();
        }
        return userInput.equals("true"); // boolean zen - returns true if string is true, false otherwise.
    }
    /**
     * Method readYesOrNo: prompts the user to answer a yes/no question, looping until a valid response is given
     *
     * @param prompt The question to ask the user
     * @return a boolean value, true for yes/y and false for no/n
     */
    public  boolean readYesOrNo(String prompt){
        String answer = readln(prompt + "(yes/no) > ").toLowerCase();

        while (! (answer.equals("yes") || answer.equals("y")
                || answer.equals("no") || answer.equals("n"))) {
            System.err.println(answer + " is Invalid. ");
            answer = readln(prompt + "(yes/no) > ").toLowerCase();
        }
        return answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y");
    }

    /**
     * Method readln: Will read a line of text that the user types in.
     * Since IO.readln only works in Java 23+ as a preview feature, I recreated the functionality here  so we could
     * continue to have the same functionality when we are not able to use Java23
     *
     * @param prompt The question to ask the user
     * @return whatever the user types in before hitting return.
     */
    public  String readln(String prompt){
        print(prompt + " > ");
        return sc.nextLine();
    }

    /**
     * Method readString: prompts the user to answer a question that has an optional number of "correct" answers (case-insensitive)
     * Code will loop until the user provides a String that is one of the valid options provided.
     *
     * @param prompt  The question to ask the user
     * @param matches Zero or more (typically > 1) valid options for the user to type from.
     * @return A String corresponding to one of the options provided, case may not be the same as the options provided
     */
    public  String readln(String prompt, String... matches) {
        String response = readln(prompt).trim();
        while( contains(response, matches) == -1){
            System.err.println( response + " is not a valid option: choose from " + Arrays.toString(matches));
            response = readln(prompt).trim();
        }
        return response;
    }

    /**
     * Shortcut (alternate name) for the readln method
     * @param prompt
     * @param options
     * @return
     */
    public String readString(String prompt, String... options){
        return readln(prompt, options);
    }
    /**
     * Method contains: Given a word and options (var-arg with zero or more possible choices for the word to be), will return the index of the word in the
     * array of all the options, or -1 if the word is not part of the list of options (case-insensitive)
     *
     * @param word The words to be located
     * @param options Var-args, array of all the possible choices
     * @return index of the word in the array of options, or -1 if the word is not part of the options
     */
    public int contains(String word, String... options){
        int index = -1;
        int i = 0;
        while( (index == -1) && i < options.length){
            if(options[i].equalsIgnoreCase(word)){
                index = i;
            }
            i++;
        }
        return index;
    }

    /**
     * Reads in a date from the user by prompting for year, month, and day.
     * @param prompt
     * @return
     */
    public  LocalDate inputDate(String prompt) {
        System.out.println(prompt); // what kind of date is the program looking for
        int year = readInt("Year ", 1900, 2555); // arbitrary future date.
        int month = readInt("Month ", 1, 12);
        int numDays = Month.of(month).length(Year.isLeap(year)); // find out the number of actual days in the month.
        int day = readInt("Day ", 1, numDays);

        // create LocalDate object
        return LocalDate.of(year, month, day);
    }

    /**
     * Reads in a char from the user, matching one of the allowed values
     * @param prompt
     * @param matches
     * @return
     */
    public char readChar(String prompt, char... matches) {
        char letter = readln(prompt).trim().toUpperCase().charAt(0);
        boolean found = false;
        while(!found){
            int i = 0;
            while(!found && i < matches.length){
                if(letter ==  (matches[i])){ // the user input is an ALLOWED match.
                    found = true;
                }
                i++;
            }
            if(!found){ // bad input
                System.err.printf("%s is bad. Must match one of the allowed chars %s%n",
                    letter, Arrays.toString(matches));
                letter = readln(prompt).trim().toUpperCase().charAt(0); // get a new try
            }
        }
        return letter;
    }

    /**
     * Allows the user to select from a variety of options.
     * @param prompt
     * @param options
     * @return
     */
    public Object chooseFrom(String prompt, Object... options){
        String choices = "";
        for(int i = 0; i< options.length; i++){
            choices  += i + ": " + options[i].toString() + "\n";
        }
        int num = readInt(choices + "\n" + prompt, 0, options.length);
        return options[num];
    }
}
