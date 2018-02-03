package com.comdes.assign1;

import java.io.*;
import java.util.*;

public class Lexer {

    List<String> tokenList = new ArrayList<String>();
    List<String> errorList = new ArrayList<String>();

    TransitionTable transitionTable;
    String startState = "A";

    public Lexer() {
        transitionTable = new TransitionTable();
    }

    public void parse(String line) {
        if(line.isEmpty()) {
            System.out.println("Line is empty");
            return;
        }

       for(int i = 0; i<line.length();i++){
            if(line.charAt(i) == ' '){
            continue;
            } else {
                i = nextToken(i, line);
            }
       }
       System.out.println("parseCompleted");
    }

    public int nextToken (int startPos, String line){
        int currPos = startPos;
        String currToken = "";
        char currChar;
        char nextChar;
        String currState = startState;
        String nextState = "blank";
        String currStateStateType = "INIT_STATE_TYPE";
        String currStateTokenType = "INIT_TOKEN_TYPE";

        do {

            nextChar = nextChar(currPos, line);
            currToken += Character.toString(nextChar);
            if (nextChar == '@'){

                currStateStateType = transitionTable.getStateType(nextState);
                currStateTokenType = transitionTable.getTokenType(nextState);

                if(currStateStateType.equals("State Type not found") || currStateTokenType.equals("Token Type not found")){
                    errorList.add("<" + line.substring(startPos,currPos)+ ", " + startPos + "-" + (currPos-1)+ ", " + currStateStateType + ", " + currStateTokenType + ">");
                }else {
                    tokenList.add("<" + line.substring(startPos, currPos) + ", " + startPos + "-" + (currPos - 1) + ", " + currStateStateType + ", " + currStateTokenType + ">");
                }
                return currPos;
            }

            nextState = transitionTable.findState(currState, isL(nextChar));

            currState = nextState;
            currPos++;
        }while(true);

    }

    public char nextChar (int pos, String line){
        char currChar;
        if (pos >= line.length() || line.charAt(pos) ==  ' '){
            return '@';
        }else{
            return currChar = line.charAt(pos);
        }

    }

    public String loadTestFile (String filename){
        String csvFile = filename;
        BufferedReader br = null;
        String line = "";
        String source = "";
        String cvsSplitBy = "`";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                source +=line + " ";
            }
            return source;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    return source;
    }

    public char isL(char c){
        if(Character.isLetter(c)){
            if (c != 'a' && c != 'c' && c != 'd' && c != 'e' && c != 'f' && c != 'g' && c != 'h' && c != 'i' && c != 'l' && c != 'm' && c != 'n' && c != 'o' && c != 'p' && c != 'r' && c != 's' && c != 't' && c != 'u') {
                return 'L';
            }
            return c;
        }
        if(Character.isDigit(c)){
            if (c != '0') {
                return 'N';
            }
            return c;
        }
        return c;
    }

    public static void printList(List<String[]> myList) {
        for (String [] arrlist: myList) {
            for (int i=0;i<arrlist.length;i++) {
                System.out.print(arrlist[i]+ "\t\t\t "+ i+" |");
            }
            System.out.println();
        }
    }

    public static void writeToFile(String filename, List<String> stringList) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println(Arrays.toString(stringList.toArray()));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        Lexer lexer = new Lexer ();
        lexer.transitionTable.loadTransitionTableCSV();
        String source = lexer.loadTestFile("test.txt");
        System.out.println("Source: " + source);
        lexer.parse(source);

        writeToFile("tokenResults.txt",lexer.tokenList);
        writeToFile("errorResults.txt",lexer.errorList);

    }



}