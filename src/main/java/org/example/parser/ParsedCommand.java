package org.example.parser;

import java.util.Arrays;

/**
 * Результат разбора строки ввода: имя команды и её аргументы.
 */
public class ParsedCommand {
    private final String command;
    private final String[] argument;

    /**
     * Создаёт разобранную команду.
     *
     * @param command имя команды
     * @param argument аргументы команды, без имени
     */
    public ParsedCommand(String command, String[] argument){
        this.command = command;
        this.argument = argument;
    }

    /**
     * @return имя команды
     */
    public String getCommand(){
        return command;
    }

    /**
     * @return аргументы команды; пустой массив, если их нет
     */
    public String[] getArgument(){
        return argument;
    }

    @Override
    public String toString(){
        return "name=" + command + ", arguments=" + Arrays.toString(argument);
    }

}


