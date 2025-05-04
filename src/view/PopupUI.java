package view;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PopupUI implements UI
{

    private String title;
    private String iconFile;
    private ImageIcon icon;

    public PopupUI(String title, String icon){
        this.title = title;
        this.iconFile = icon;
        this.icon = new ImageIcon(iconFile);

    }
    public PopupUI(String title){
        this(title, null);
    }
    public PopupUI(){
        this ("265 Popup");
    }

    public void print(String output) {
        JOptionPane.showMessageDialog(null, output, title, JOptionPane.PLAIN_MESSAGE, icon);
    }
    @Override
    public void print(Object o) {
        print(o.toString());
    }

    public void println(Object output) {
        JOptionPane.showMessageDialog(null, output, title, JOptionPane.PLAIN_MESSAGE, icon);
    }

    public void println(String output) {
        JOptionPane.showMessageDialog(null, output, title, JOptionPane.PLAIN_MESSAGE, icon);

    }

    public void printFancy(String output) {
        JOptionPane.showMessageDialog(null, output, title, JOptionPane.PLAIN_MESSAGE, icon);
    }

    public String readln(String prompt) {
        return JOptionPane.showInputDialog(null, prompt, title, JOptionPane.QUESTION_MESSAGE);
    }
    public String readln(String prompt, String... matches) {
        return (String)JOptionPane.showInputDialog(null, prompt,title, JOptionPane.QUESTION_MESSAGE,
                icon, matches, matches[0]);
    }



    public int readInt(String prompt) {

        int value = 0;
        boolean gotNumber = false;
        while (!gotNumber) {
            String s = JOptionPane.showInputDialog(null, prompt, title, JOptionPane.QUESTION_MESSAGE);
            try {
                value = Integer.parseInt(s);
                gotNumber = true;
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(null, s + " was not a valid int", title,
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        return value;
    }


    public int readInt(String prompt, int minValue, int maxValue) {
        int num = readInt(prompt); // make sure you get a num
        while(num < minValue || num > maxValue) {
            String msg = num  + " is not in the allowed range: [" + minValue
                    + "-" + maxValue + "]";
            JOptionPane.showMessageDialog(null,  msg, title,
                    JOptionPane.ERROR_MESSAGE);
            num = readInt(prompt); // make sure you get a num
        }
        return num;
    }



    public int readInt(String prompt, int minValue, int maxValue, int quitValue) {
        int num = readInt(prompt); // make sure you get a num

        while(num != quitValue && (num < minValue || num > maxValue)) {
            String msg = num + " is not in the allowed range: [" + minValue
                    + "-" + maxValue + "] (or " + quitValue + " to quit)";
            JOptionPane.showMessageDialog(null,  msg, title,
                    JOptionPane.ERROR_MESSAGE, icon);

        }
        return num;
    }


    public double readDouble(String prompt) {
        double value = 0;
        boolean gotNumber = false;
        while (!gotNumber) {
            String s = JOptionPane.showInputDialog(null, prompt, title, JOptionPane.QUESTION_MESSAGE);
            try {
                value = Double.parseDouble(s);
                gotNumber = true;
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(null, s + " was not a valid double", title,
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        return value;
    }

    public double readDouble(String prompt, double minValue, double maxValue) {
        double num = readDouble(prompt); // make sure you get a num
        while(num < minValue || num > maxValue) {
            String msg = num  + " is not in the allowed range: [" + minValue
                    + "-" + maxValue + "]";
            JOptionPane.showMessageDialog(null,  msg, title,
                    JOptionPane.ERROR_MESSAGE, icon);
            num = readDouble(prompt); // make sure you get a double
        }
        return num;
    }


    /**
     * Prompt the user enter yes or no (will match y/yes and n/no any case) and return true for yes and false for no.
     * @param prompt: the question to ask the user
     * @return: a boolean value
     */
    public boolean readYesOrNo(String prompt) {
        String[] options = {"yes", "no"};
        int index = JOptionPane.showOptionDialog(null, prompt, title, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return index == 0;
    }

    public boolean readBoolean(String prompt) {
        int value = JOptionPane.showConfirmDialog(null, prompt, title, JOptionPane.YES_NO_OPTION);
        if(value == JOptionPane.YES_OPTION) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public LocalDate inputDate(String prompt) {
        LocalDate parsedDate = LocalDate.now();
        String dateString = "";
        boolean validDate = false;
        while(!validDate) {
            try {
                dateString = readln(prompt + "\nUse format:MM dd yyyy");
                parsedDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MM dd yyyy"));
                validDate = true;
            }
            catch (Exception e) {
                String message = "ERROR: " + dateString +  " could not be parsed. Try again.";
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE, icon);
                validDate = false;
            }
        }
        return parsedDate;
    }


    @Override
    public Object chooseFrom(String prompt, Object... options) {
        return JOptionPane.showInputDialog(null, prompt,title, JOptionPane.QUESTION_MESSAGE,
                icon, options, options[0]);
    }

}
