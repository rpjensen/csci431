/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scannerproject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

/**
 * A calculator parser.
 *
 * @author Ryan Jensen
 * @version Oct 4, 2015 CSCI 431 Project 1
 */
public class CalcParser {

    private CalcScanner sc;
    private int lookahead;
    private int index;
    private String[] lookup = {"$$", "(", ")", "read", "write", "+", "-", "*", "/", ":=", "id", "num"};

    public CalcParser(String inputFile) throws FileNotFoundException {
        FileReader reader = new FileReader(inputFile);
        sc = new CalcScanner(reader);

    }

    public void parse() throws IOException, ParseException {
        lookahead = sc.nextToken();
        program();
        System.out.println("Parse successful");
    }

    private void program() throws IOException, ParseException {
//        System.out.println("Program");
        stmtList();
        match("$$");
    }

    private void stmtList() throws IOException, ParseException {
//        System.out.println("Statement list");

        while (hasStmt()) {
            stmt();
        }
    }

    private void stmt() throws IOException, ParseException {
//        System.out.println("Statement");

        if (checkLookahead("id")) {
            match("id");
            match(":=");
            expr();
        } else if (checkLookahead("read")) {
            match("read");
            match("id");

        } else if (checkLookahead("write")) {
            match("write");
            expr();
        } else {
            match("Expected Statement");
        }
    }

    private boolean hasStmt() {
        return checkLookahead("id") || checkLookahead("read") || checkLookahead("write");
    }

    private void expr() throws IOException, ParseException {
//        System.out.println("Expression");

        term();
        termTail();
    }

    private void term() throws IOException, ParseException {
//        System.out.println("Term");

        factor();
        factorTail();
    }

    private void termTail() throws IOException, ParseException {
//        System.out.println("Term tail");
        if (hasAddOp()) {
            addOp();
            term();
            termTail();
        }
    }

    private void factor() throws IOException, ParseException {
//        System.out.println("Factor");

        if (checkLookahead("(")) {
            match("(");
            expr();
            match(")");
        } else if (checkLookahead("id")) {
            match("id");
        } else if (checkLookahead("num")) {
            match("num");
        } else {
            match("Expected Factor");
        }
    }

    private void factorTail() throws IOException, ParseException {
//        System.out.println("Factor Tail");
        if (hasMultOp()) {
            multOp();
            factor();
            factorTail();
        }
    }

    private void addOp() throws IOException, ParseException {
//        System.out.println("Add op");

        if (checkLookahead("+")) {
            match("+");
        } else if (checkLookahead("-")) {
            match("-");
        } else {
            match("Expected Add operation");
        }
    }

    private boolean hasAddOp() {
        return checkLookahead("+") || checkLookahead("-");
    }

    private void multOp() throws IOException, ParseException {
//        System.out.println("Mult op");
        if (checkLookahead("*")) {
            match("*");
        } else if (checkLookahead("/")) {
            match("/");
        } else {
            match("Expected Mult operation");
        }
    }

    private boolean hasMultOp() {
        return checkLookahead("*") || checkLookahead("/");
    }

    private void match(String token) throws IOException, ParseException {
        if (checkLookahead(token)) {
            index++;
            if (sc.hasNextToken()) {
                lookahead = sc.nextToken();
            } else {
                lookahead = -1;
            }
        } else {
            if (lookahead == -1) {
                throw new ParseException("Parse error at end of stream found " + token + " at index " + index, index);
            }
            throw new ParseException("Parse error at token " + lookup[lookahead] + ", expected " + token + " at index " + index, index);
        }
    }

    private boolean checkLookahead(String token) {
        return lookahead == reverseLookup(token);
    }

    private int reverseLookup(String token) {
        for (int i = 0; i < lookup.length; i++) {
            if (lookup[i].equals(token)) {
                return i;
            }
        }
        return -1;
    }
}
