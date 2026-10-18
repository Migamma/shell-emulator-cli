package org.example.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Реестр команд: находит команду по её имени.
 */
public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Создаёт реестр и регистрирует команды ls, cd и exit.
     */
    public CommandRegistry() {
        LsCommand ls = new LsCommand();
        CdCommand cd = new CdCommand();
        ExitCommand exit = new ExitCommand();

        commands.put("ls", ls);
        commands.put("cd", cd);
        commands.put("exit", exit);
    }

    /**
     * Находит команду по имени.
     *
     * @param commandName имя команды, введённое пользователем
     * @return команда или null, если команды с таким именем нет
     */
    public Command find(String commandName) {
        return commands.get(commandName);
    }
}
