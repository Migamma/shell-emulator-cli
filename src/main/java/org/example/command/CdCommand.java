package org.example.command;

import org.example.vfs.VfsDirectory;
import org.example.vfs.VfsNode;
import org.example.vfs.VirtualFileSystem;

/**
 * Команда cd: делает указанную папку текущей.
 * <p>
 * Без аргумента переходит в корень. Принимает не больше одного аргумента.
 */
public class CdCommand implements Command {
    private static final int MAX_ARGS = 1;
    private final VirtualFileSystem fileSystem;

    /**
     * @param fileSystem файловая система, с которой работает команда
     */
    public CdCommand(VirtualFileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public CommandResult execute(String[] args) {
        if (args.length > MAX_ARGS) {
            return new CommandResult("cd: too many arguments", false, false);
        }
        if (args.length == 0) {
            fileSystem.setCurrent(fileSystem.getRoot());
            return new CommandResult("", true, false);
        }
        VfsNode node = fileSystem.resolve(args[0]);
        if (node == null) {
            return new CommandResult("cd: no such file or directory: " + args[0], false, false);
        }
        if (!(node instanceof VfsDirectory)) {
            return new CommandResult("cd: not a directory: " + args[0], false, false);
        }
        fileSystem.setCurrent((VfsDirectory) node);
        return new CommandResult("", true, false);
    }
}
