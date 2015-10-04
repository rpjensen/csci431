
package scannerproject;

import java.io.IOException;

/**
 * A testing class parse for a small calculator language
 *
 * @author Ryan Jensen
 * @version Oct 4, 2015 CSCI 431 Project 1
 */
public class ParseProject {
    
    public static void main(String[] args) throws IOException {
        /*
         * ----- Lookup Table ----- 
         * $$ 0 
         * ( 1 
         * ) 2 
         * read 3 
         * write 4 
         * + 5 
         * - 6 
         * * 7 
         * / 8
         * := 9 
         * id 10 
         * num 11
         *
         */
        String[] reverseLookup = {"$$", "(", ")", "read", "write", "+", "-", "*", "/", ":=", "id", "num", " ", "\t", "\n"};
        
    }
}
