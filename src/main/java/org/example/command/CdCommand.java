package org.example.command;

/**
 * Заглушка команды cd: выводит своё имя и полученный аргумент.
 * <p>
 * Принимает не больше одного аргумента, иначе возвращает ошибку.
 */
public class CdCommand implements Command {
    private static final int MAX_ARGS = 1;

    @Override
    public CommandResult execute(String[] args) {
        String output = "cd: ";
        if (args.length > MAX_ARGS) {
            output += "too many arguments";
            return new CommandResult(output, false, false);
        } else if (args.length == MAX_ARGS) {
            output += "args: " + args[0];
            return new CommandResult(output, true, false);
        }
        output += "args: no argument";
        return new CommandResult(output, true, false);
    }
}
