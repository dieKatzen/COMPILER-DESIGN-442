package com.comdes.assign1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TransitionTable {

    ArrayList<String []> transitionTable = new ArrayList <String []>();


    public void loadTransitionTableCSV (){
        String csvFile = "tt11.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "`";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            int row = 0;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                transitionTable.add(line.split(cvsSplitBy));
                row++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            Lexer.printList(transitionTable);
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String findState(String currState, char currChar) {
        String [] symbolRow = transitionTable.get(0);
        String newState ="";

        int index = -1;

        try {
            for (int i = 0; i < symbolRow.length; i++) {
                if (symbolRow[i].equals(String.valueOf(currChar))) {
                    index = i;
                    break;
                }
            }

            if(index == -1){
                throw new SymbolNotFoundException();
            }

            for (int i=1;i<transitionTable.size();i++) {
                if (transitionTable.get(i)[0].equals(currState)) {
                    newState = transitionTable.get(i)[index];
                    return newState;
                }
            }

            if (newState.trim().length() > 0 || newState.isEmpty()) throw new Exception ("newState is empty!");

        }catch(Exception e) {
            System.out.println("Error in findState");
        }
        return "NOT FOUND";

    }

    public String getTokenType(String State){
            String tokenType;
            for (int i=1;i<transitionTable.size();i++) {
                if (transitionTable.get(i)[0].equals(State)) {
                    tokenType = transitionTable.get(i)[transitionTable.get(0).length-3];
                    return tokenType;
                }
            }
            return "Token Type not found";
    }

    public String getStateType(String State){
        String stateType;
        for (int i=1;i<transitionTable.size();i++) {
            if (transitionTable.get(i)[0].equals(State)) {
                stateType = transitionTable.get(i)[transitionTable.get(0).length-4];
                return stateType;
            }
        }
        return "State Type not found";
    }

    public static void main(String [] args){
        com.comdes.assign1.TransitionTable tt = new com.comdes.assign1.TransitionTable();
        tt.loadTransitionTableCSV();
    }
}

