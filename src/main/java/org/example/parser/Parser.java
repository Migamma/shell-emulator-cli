package org.example.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private final Map<String, String> variables;
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$(\\w+)");

    public Parser(Map<String, String> variables){
        this.variables = variables;
    }
    public ParsedCommand parse(String line){

        String command = line.trim();

        if (command.isBlank()){
            throw new IllegalArgumentException("ERROR! Line is empty");
        }

        String[] commandAll = command.split("\\s+");

        List<String> expanded = new ArrayList<>();

        for (String name : commandAll){
            String expandVariable = expandVariables(name);
            if (!expandVariable.isEmpty()){
                expanded.add(expandVariable);
            }
        }


        if (expanded.isEmpty()){
             throw new IllegalArgumentException("ERROR! List is empty");
        }

        String commandName = expanded.get(0);
        String[] arguments = expanded.subList(1, expanded.size()).toArray(new String[0]);
        ParsedCommand obj = new ParsedCommand(commandName, arguments);

        return obj;
    }

    private String expandVariables(String word){
        Matcher matcher = VARIABLE_PATTERN.matcher(word);
        StringBuilder result = new StringBuilder();
        while (matcher.find()){
            String name = matcher.group(1);
            String value = variables.getOrDefault(name, "");
            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
