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
 * A testing class scanner for a small calculator language
 *
 * @author Ryan Jensen
 * @version Sep 10, 2015 CSCI 431 Project 1
 */
public class ScannerProject {

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
        ArrayList<Integer> trueVal = new ArrayList<>();
        ArrayList<Integer> myVal = new ArrayList<>();
        Random rand = new Random();
        StringBuilder programText = new StringBuilder();
        int programLength = 100;
        int maxIdentifierLength = 10;
        int maxNumberLength = 8;

        int previousToken = 0;
        for (int i = 0; i < programLength; i++) {
            int nextToken;
            // Don't end on a space
            if ((i + 1) == programLength) {
                nextToken = rand.nextInt(12);
            }
            else {
                nextToken = rand.nextInt(reverseLookup.length);
            }
            
            
            // If it is a white space then it is not a true token
            if (nextToken < 12) {
                // Print out the token that is expected
                System.out.print(reverseLookup[nextToken]);
                trueVal.add(nextToken);
            }
            
            String tokenVal;
            switch (reverseLookup[nextToken]) {
                case "id":
                    tokenVal = buildIdentifier(maxIdentifierLength, rand);
                    break;
                case "num":
                    tokenVal = buildNumber(maxNumberLength, rand);
                    break;
                default:
                    tokenVal = reverseLookup[nextToken];
                    break;
            }
            
            // If it is a consecutive special token (potential for ambigous start and stop)
            // then add a space between them
            if (isSpecialToken(reverseLookup[previousToken]) && isSpecialToken(reverseLookup[nextToken])) {
                programText.append(" ");
            }
            programText.append(tokenVal);
            previousToken = nextToken;
        }
        
        System.out.println();
        System.out.println("===================== Program start =====================");
        System.out.println(programText);
        System.out.println("===================== Program end =======================");


        StringReader program = new StringReader(programText.toString());
        CalcScanner scanner = new CalcScanner(program);
        while (scanner.hasNextToken()) {
            myVal.add(scanner.nextToken());
        }

        System.out.println("True:" + trueVal.size() + " \t" + trueVal.toString());
        System.out.println("Parsed:" + trueVal.size() + " \t" + myVal.toString());
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
    
    public static boolean isSpecialToken(String val) {
        return val.equals("read") || val.equals("write") || val.equals("id") || val.equals("num");
    }

}
