package org.example.parser;

import java.util.Arrays;

public class Parser {

    public ParsedCommand parse(String line){

        String command = line.trim();
        if (command.isBlank()){
            throw new IllegalArgumentException("ERROR! Line is empty");
        }
        String[] commandAll = command.split("\\s+");
        String[] argument = Arrays.copyOfRange(commandAll, 1, commandAll.length);

        ParsedCommand obj = new ParsedCommand(commandAll[0], argument);

        return obj;
    }
}
