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
    
    private int characterSetLookup(char c) {
        
        if (c >= 48 && c <= 57) {
            // Number
            return 0;
        }
        if (c >= 65 && c <= 90) {
            // Upper case char
            return 1;
        }
        if (c >= 97 && c <= 122) {
            // Lower case char
            return 2;
        }
        if (c == '$') {
            return 3;
        }
        if (c == '(') {
            return 4;
        }
        if (c == ')') {
            return 5;
        }
        if (c == '+') {
            return 6;
        }
        if (c == '-') {
            return 7;
        }
        if (c == '*') {
            return 8;
        }
        if (c == '/') {
            return 9;
        }
        if (c == ':') {
            return 10;
        }
        if (c == '=') {
            return 11;
        }
        return -1;
    }
}
