package org.example.command;

import org.example.vfs.VirtualFileSystem;

import java.util.HashMap;
import java.util.Map;

/**
 * Реестр команд: находит команду по её имени.
 */
public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Создаёт реестр и регистрирует команды ls, cd, tac, uname и exit.
     *
     * @param fileSystem файловая система для команд, работающих с VFS
     */
    public CommandRegistry(VirtualFileSystem fileSystem) {
        LsCommand ls = new LsCommand(fileSystem);
        CdCommand cd = new CdCommand(fileSystem);
        ExitCommand exit = new ExitCommand();
        UnameCommand uname = new UnameCommand();
        TacCommand tac = new TacCommand(fileSystem);

        commands.put("ls", ls);
        commands.put("cd", cd);
        commands.put("exit", exit);
        commands.put("uname", uname);
        commands.put("tac", tac);
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
