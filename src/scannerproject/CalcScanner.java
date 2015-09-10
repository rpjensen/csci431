/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scannerproject;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashSet;

/**
 *  ----- Lookup Table -----
 *      $$      0
 *      (       1
 *      )       2
 *      read    3
 *      write   4
 *      +       5
 *      -       6
 *      *       7
 *      /       8
 *      :=      9
 *      id      10
 *      num     11
 * @author rjensen
 */
public class CalcScanner {
    
    private PushbackReader stream;
    private int[][] transitionTable;
    private int currentState;
    
    public CalcScanner(PushbackReader stream) {
        this.stream = stream;
        this.currentState = 0;
    }
    
    public int nextToken() throws IOException {
        int nextChar = stream.read();
        
        if (nextChar == -1) {
            return -1;
        }
        int nextState = getNextState((char)nextChar);
        
        if (nextState >= transitionTable.length) {
            return checkFinalState(nextState);
        }
        currentState = nextState;
        
        return -1;
    }
    
    private int checkFinalState(int state) {
        
        return -1;
    }
    
    private int getNextState(char c) {
        return transitionTable[currentState][characterSetLookup(c)];
    }
    
    private int[][] initializeTransitionTable() {
        int[][] table = new int[5][13];
        table[0] = new int[] { 1, 2, 3, 4, 4, 9, 9, 9, 9, 9, 9, 10, 0};
        table[1] = new int[] { 5, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
        table[2] = new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 6, 10 };
        table[3] = new int[] { 7, 7, 3, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7 };
        table[4] = new int[] { 8, 8, 4, 4, 4, 8, 8, 8, 8, 8, 8, 8, 8 };
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
        if (c == ' ') {
            return 12;
        }
        return -1;
    }
}
