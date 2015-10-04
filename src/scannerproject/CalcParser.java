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
 *
 * @author rjensen
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
        System.out.println("Program");
        stmtList();
        match("$$");
    }

    private void stmtList() throws IOException, ParseException {
        System.out.println("Statement list");

        while (checkLookahead("id") || checkLookahead("read") || checkLookahead("write")) {
            stmt();
        }
    }

    private void stmt() throws IOException, ParseException {
        System.out.println("Statement");

        if (checkLookahead("id")) {
            match("id");
            match(":=");
            expr();
        } else if (checkLookahead("read")) {
            System.out.println("Read");

            match("read");
            match("id");

        } else if (checkLookahead("write")) {
            match("write");
            expr();
        } else {
            match("Expected Statement");
        }
    }

    private void expr() throws IOException, ParseException {
        System.out.println("Expression");

        term();
        while (checkLookahead("+")) {
            match("+");
            term();
        }
    }

    private void term() throws IOException, ParseException {
        System.out.println("Term");

        factor();
        while (checkLookahead("*")) {
            match("*");
            factor();
        }
    }

    private void factor() throws IOException, ParseException {
        System.out.println("Factor");

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

    private void match(String token) throws IOException, ParseException {
        if (checkLookahead(token)) {
            index++;
            if (sc.hasNextToken()) {
                lookahead = sc.nextToken();
            } else {
                lookahead = -1;
            }
        } else {
            throw new ParseException("Parse error at token " + lookup[lookahead] + " found " + token + " at index " + index, index);
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
