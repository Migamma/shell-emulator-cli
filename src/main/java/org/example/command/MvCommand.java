package org.example.command;

import org.example.vfs.VfsDirectory;
import org.example.vfs.VfsNode;
import org.example.vfs.VirtualFileSystem;

public class MvCommand implements Command{
    private final VirtualFileSystem fileSystem;
    private final static int MAX_ARGS = 2;

    public MvCommand(VirtualFileSystem fileSystem){
        this.fileSystem = fileSystem;
    }

    @Override
    public CommandResult execute(String[] args){
        if (args.length != MAX_ARGS){
            return new CommandResult("mv: expected 2 arguments", false, false);
        }

        VfsNode node = fileSystem.resolve(args[0]);
        if (node == null){
            return new CommandResult("No such file or directory", false, false);
        }

        VfsNode target = fileSystem.resolve(args[1]);

        if (target != null && target instanceof VfsDirectory) {
            fileSystem.move(node, (VfsDirectory) target, node.getName());
            return new CommandResult("", true, false);
        } else{
            return new CommandResult("mv: not supported yet", false, false);
        }
    }
}
