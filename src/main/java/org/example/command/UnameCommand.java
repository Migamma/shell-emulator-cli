package org.example.command;

/**
 * Команда uname: выводит название ОС, в которой запущен эмулятор.
 */
public class UnameCommand implements Command {
    private static final int MAX_ARGUMENT = 0;

    @Override
    public CommandResult execute(String[] args) {
        if (args.length > MAX_ARGUMENT) {
            return new CommandResult("uname: too many arguments", false, false);
        }
        String osName = System.getProperty("os.name");
        return new CommandResult(osName, true, false);
    }

}
