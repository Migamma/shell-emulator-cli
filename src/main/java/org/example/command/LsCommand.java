package org.example.command;

import org.example.vfs.VirtualFileSystem;
import org.example.vfs.VfsNode;
import org.example.vfs.VfsDirectory;

import java.util.ArrayList;
import java.util.List;

/**
 * Заглушка команды ls: выводит своё имя и полученные аргументы.
 */
public class LsCommand implements Command {
    private final VirtualFileSystem fileSystem;
    private static final int MAX_ARGS = 1;

    public LsCommand(VirtualFileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public CommandResult execute(String[] args) {
        VfsNode node = args.length == 0 ? fileSystem.getCurrent() : fileSystem.resolve(args[0]);

        if (args.length > MAX_ARGS){
            return new CommandResult("ls: too many arguments", false, false);
        }
        if (node == null) {
            return new CommandResult("ls: cannot access '" + args[0] + "': No such file or directory", false, false);
        }
        if (!(node instanceof VfsDirectory)) {
            return new CommandResult(node.getName(), true, false);
        }

        List<String> names = new ArrayList<>();

        for (VfsNode child : ((VfsDirectory) node).getChildren()) {
            names.add(child.getName());

        }
        return new CommandResult(String.join("  ", names), true, false);
    }
}
