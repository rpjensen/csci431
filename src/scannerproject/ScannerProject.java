/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scannerproject;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author rjensen
 */
public class ScannerProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        /**
         * ----- Lookup Table ----- 
         * $$ 0 
         * ( 1 
         * ) 2 
         * read 3 
         * write 4 
         * + 5 - 6 * 7 / 8
         * := 9 
         * id 10 
         * num 11
         *
         * @return token value of the scanned token, else -1.
         * @throws IOException if the input stream given to the scanner was
         * malformed.
         */
        String[] reverseLookup = {"$$", "(", ")", "read", "write", "+", "-", "*", "/", ":=", "id", "num", " "};
        int length = reverseLookup.length;
        ArrayList<Integer> trueVal = new ArrayList<>();
        ArrayList<Integer> myVal = new ArrayList<>();
        Random rand = new Random();
        StringBuilder programText = new StringBuilder();
        int programLength = 10;
        int maxIdentifierLength = 10;
        int maxNumberLength = 8;

        int previousToken = -1;
        for (int i = 0; i < programLength; i++) {
            int nextToken = rand.nextInt(13);
            System.out.print(reverseLookup[nextToken]);
            if (nextToken < 12) {
                trueVal.add(nextToken);
            }
            
            if (reverseLookup[nextToken].equals("id")) {
                programText.append(buildIdentifier(maxIdentifierLength, rand));
                programText.append(" ");
                
            }
            else if (reverseLookup[nextToken].equals("num")) {
                programText.append(buildNumber(maxNumberLength, rand));
                programText.append(" ");
            }
            else {
                programText.append(reverseLookup[nextToken]);
                if (reverseLookup[nextToken].equals("read") || reverseLookup[nextToken].equals("write")) {
                    programText.append(" ");
                }
            }
            
            previousToken = nextToken;
        }
        System.out.println();

        if (programText.charAt(programText.length()-1) == ' ') {
            programText.setLength(programText.length()-1);
        }
        
        StringReader program = new StringReader(programText.toString());
        CalcScanner scanner = new CalcScanner(program);
        while (scanner.hasNextToken()) {
            myVal.add(scanner.nextToken());
        }

        if (programLength < 100) {
            System.out.println(programText);
            System.out.println("True:" + trueVal.size() + " \t\t" + trueVal.toString());
            System.out.println("Parsed:" + trueVal.size() + " \t" + myVal.toString());
        }
        int errorCount = 0;
        for (int i = 0; i < myVal.size(); i++) {
            int real = trueVal.get(i);
            int my = myVal.get(i);
            if (my != real) {
                System.out.println("-------------------");
                System.out.println();
                System.out.printf("Error at token %d\n Real Token\t %s\n My Token\t %s\n", i, reverseLookup[real], reverseLookup[my]);
                errorCount++;
            }
        }

        if (errorCount > 0) {
            System.out.println();
            System.out.println();
            System.out.println("------------------------");
            System.out.printf("Test failed with %d errors\n", errorCount);
        } else {
            System.out.println("Test successful");
        }
    }
    
    public static String buildIdentifier(int length, Random rand) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length-1; i++) {
            builder.append(nextLetter(rand));
        }
        builder.append(nextDigit(rand));
        
        return builder.toString();
    }
    
    public static String buildNumber(int length, Random rand) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length-1; i++) {
            builder.append(nextDigit(rand));
        }
        
        return builder.toString();
    }
    
    public static char nextLetter(Random rand) {
        if (rand.nextBoolean()) {
            return (char) (rand.nextInt(26) + 97);
        } else {
            return (char) (rand.nextInt(26) + 65);
        }
    }

    public static int nextDigit(Random rand) {
        return rand.nextInt(10);
    }

}
