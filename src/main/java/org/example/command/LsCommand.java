package org.example.command;

/**
 * Заглушка команды ls: выводит своё имя и полученные аргументы.
 */
public class LsCommand implements Command {
    @Override
    public CommandResult execute(String[] args) {
        String output = "ls: args:";
        for (String arg : args) {
            output += " " + arg;
        }

        CommandResult obj = new CommandResult(output, true, false);
        return obj;
    }
}
