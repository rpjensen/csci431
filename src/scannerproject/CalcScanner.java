/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scannerproject;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.HashSet;

/**
 * ----- Lookup Table ----- $$ 0 ( 1 ) 2 read 3 write 4 + 5 - 6 * 7 / 8 := 9 id
 * 10 num 11
 *
 * @author Ryan Jensen
 * @version Sep 10, 2015 CSCI 431 Project 1
 */
public class CalcScanner {

    private final PushbackReader stream;
    private int[][] transitionTable;
    private int currentState;
    private StringBuilder workingString;

    public CalcScanner(Reader stream) {
        this.stream = new PushbackReader(stream);
        currentState = 0;
        workingString = new StringBuilder();
        transitionTable = initializeTransitionTable();
    }

    /**
     * ----- Lookup Table ----- $$ 0 ( 1 ) 2 read 3 write 4 + 5 - 6 * 7 / 8 := 9
     * id 10 num 11
     *
     * @return token value of the scanned token, else -1.
     * @throws IOException if the input stream given to the scanner was
     * malformed.
     */
    public int nextToken() throws IOException {
        // Add an extra method call to capture the returned value and 
        // perform cleanup at a higher level after the return statement.
        int token = nextTokenHelper();
        // clean up the state before return
        workingString = new StringBuilder();
        currentState = 0;

        return token;
    }

    public boolean hasNextToken() throws IOException {
        int charCode = stream.read();

        // end of stream
        if (charCode == -1) {
            return false;
        } else {
            stream.unread(charCode);
            return true;
        }
    }

    private int nextTokenHelper() throws IOException {
        int charCode;
        char c = (char) 0;
        while (currentState < transitionTable.length) {
            // Read the next character
            charCode = stream.read();

            // end of stream
            if (charCode == -1 && workingString.length() == 0) {
                return -1;
            }

            // cast as char and append to working string
            c = (char) charCode;
            workingString.append(c);

            currentState = getNextState(c);
            
            if (currentState == -1) {
                return -1;
            }
        }

        return checkFinalState(currentState, c);
    }

    private int getNextState(char c) {
        if (currentState >= transitionTable.length) {
            return currentState;
        }
        int charSlot = characterSetLookup(c);
        if (charSlot == -1) {
            return -1;
        }
        return transitionTable[currentState][characterSetLookup(c)];
    }

    private int checkFinalState(int state, char c) throws IOException {
        switch (state) {
            case 5:
                return 0;
            case 6:
                return 9;
            case 7:
                stream.unread((int) c);
                return 11;
            case 8:
                stream.unread((int) c);
                int token = checkReservedWordTable(workingString.toString().trim());
                return token != -1 ? token : 10;
            case 9:
                return checkSingleTokenTable(c);
            default:
                return -1;
        }
    }

    private int checkReservedWordTable(String workingString) {
        switch (workingString) {
            case "read":
                return 3;
            case "write":
                return 4;
            default:
                return -1;
        }
    }

    private int checkSingleTokenTable(char c) {
        switch (c) {
            case '(':
                return 1;
            case ')':
                return 2;
            case '+':
                return 5;
            case '-':
                return 6;
            case '*':
                return 7;
            case '/':
                return 8;
            default:
                return -1;
        }
    }

    private int[][] initializeTransitionTable() {
        int[][] table = new int[5][13];
        table[0] = new int[]{1, 2, 3, 4, 4, 9, 9, 9, 9, 9, 9, 10, 0};
        table[1] = new int[]{5, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        table[2] = new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 6, 10};
        table[3] = new int[]{7, 7, 3, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
        table[4] = new int[]{8, 8, 4, 4, 4, 8, 8, 8, 8, 8, 8, 8, 8};
        return table;
    }

    private int characterSetLookup(char c) {

        if (c == '$') {
            return 0;
        }
        if (c == ':') {
            return 1;
        }
        if (c >= 48 && c <= 57) {
            // Number
            return 2;
        }
        if (c >= 65 && c <= 90) {
            // Upper case char
            return 3;
        }
        if (c >= 97 && c <= 122) {
            // Lower case char
            return 4;
        }
        if (c == '(') {
            return 5;
        }
        if (c == ')') {
            return 6;
        }
        if (c == '*') {
            return 7;
        }
        if (c == '+') {
            return 8;
        }
        if (c == '-') {
            return 9;
        }
        if (c == '/') {
            return 10;
        }
        if (c == '=') {
            return 11;
        }
        switch (c) {
            case ' ':
                break;
            case '\n':
                break;
            case '\t':
                break;
            case 65535:
                break;
        }
        if (c == ' ' || c == '\n' || c == '\t' || c == 65535) {
            return 12;
        }
        return -1;
    }
}
