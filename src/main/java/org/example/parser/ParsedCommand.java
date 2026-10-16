package org.example.parser;


import java.util.Arrays;

public class ParsedCommand {
    private final String command;
    private final String[] argument;

    public ParsedCommand(String command, String[] argument){
        this.command = command;
        this.argument = argument;
    }

    public String getCommand(){
        return command;
    }

    public String[] getArgument(){
        return argument;
    }

    @Override
    public String toString(){
        return "name=" + command + ", arguments=" + Arrays.toString(argument);
    }

}


