package kavabase;

import kavabase.Prompt.Prompt;

/**
 *
 * @author Anton
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Prompt.run();
        String s = "test,";
        String[] tokens = s.split(",");
        System.out.println(tokens.length);
    }    
}
