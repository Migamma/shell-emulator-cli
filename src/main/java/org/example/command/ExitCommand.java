package org.example.command;

public class ExitCommand implements Command{
    private final static int MAX_ARGS = 0;

    @Override
    public CommandResult execute(String[] args){
        String output = "";
        if (args.length > MAX_ARGS) {
            output += "exit: too many arguments";
            return new CommandResult(output, false, false);
        }
        return new CommandResult(output, true, true);
    }
}
