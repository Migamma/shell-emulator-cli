package org.example.command;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandRegistry(){
        LsCommand ls = new LsCommand();
        CdCommand cd = new CdCommand();
        ExitCommand exit = new ExitCommand();

        commands.put("ls", ls);
        commands.put("cd", cd);
        commands.put("exit", exit);
    }

    public Command find(String commandName){
        return commands.get(commandName);
    }
}
