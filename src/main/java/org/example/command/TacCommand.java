package org.example.command;

import org.example.vfs.VfsDirectory;
import org.example.vfs.VfsFile;
import org.example.vfs.VfsNode;
import org.example.vfs.VirtualFileSystem;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TacCommand implements Command {
    private static final int MAX_ARGS = 1;
    private final VirtualFileSystem fileSystem;

    TacCommand(VirtualFileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public CommandResult execute(String[] args) {
        if (args.length > MAX_ARGS) {
            return new CommandResult("tac: too many arguments", false, false);
        }
        if (args.length == 0){
            return new CommandResult("tac: need a one argument", false, false);
        }

        VfsNode node = fileSystem.resolve(args[0]);
        if (node == null) {
            return new CommandResult("tac: cannot access '" + args[0] + "': No such file or directory", false, false);
        }
        if (node instanceof VfsDirectory) {
            return new CommandResult("tac: " + args[0] + ": Is a directory", false, false);
        }

        VfsFile file = (VfsFile) node;

        byte[] content = file.getContent();
        String text = new String(content, StandardCharsets.UTF_8);

        String[] lines = text.split("\n");
        List<String> reversed =  Arrays.asList(lines);
        Collections.reverse(reversed);
        return new CommandResult(String.join("\n", reversed), true, false);
    }
}
