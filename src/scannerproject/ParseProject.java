
package scannerproject;

import java.io.IOException;
import java.text.ParseException;

/**
 * A testing class parse for a small calculator language
 *
 * @author Ryan Jensen
 * @version Oct 4, 2015 CSCI 431 Project 1
 */
public class ParseProject {
    
    public static void main(String[] args) throws IOException, ParseException {
        CalcParser parser = new CalcParser("Program1.txt");
        parser.parse();
        
    }
}
