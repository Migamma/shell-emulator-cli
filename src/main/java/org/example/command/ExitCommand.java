package org.example.command;

/**
 * Команда exit: сообщает терминалу, что нужно завершить работу.
 * <p>
 * Не принимает аргументов, иначе возвращает ошибку.
 */
public class ExitCommand implements Command {
    private final static int MAX_ARGS = 0;

    @Override
    public CommandResult execute(String[] args) {
        String output = "";
        if (args.length > MAX_ARGS) {
            output += "exit: too many arguments";
            return new CommandResult(output, false, false);
        }
        return new CommandResult(output, true, true);
    }
}
