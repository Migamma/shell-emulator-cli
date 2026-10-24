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
            return new CommandResult("mv: no such file or directory " + args[0], false, false);
        }

        if (node == fileSystem.getRoot()){
            return new CommandResult("mv: cannot move root ", false, false);
        }

        VfsNode target = fileSystem.resolve(args[1]);

        if (target != null && target instanceof VfsDirectory) {
            return moveTo(node, (VfsDirectory) target, node.getName());
        } else{
            VfsDirectory targetDirectory = findTargetDirectory(args[1]);
            if (targetDirectory == null){
                return new CommandResult("mv: target directory not found: " + args[1], false, false);
            }
            String newName = args[1].substring(args[1].lastIndexOf("/") + 1);
            return moveTo(node, targetDirectory, newName);
        }
    }

    private CommandResult moveTo(VfsNode node, VfsDirectory directory, String name){
        if (isInside(node, directory)){
            return new CommandResult("mv: cannot move a directory into itself", false, false);
        }
        if (directory.getChild(name) != null){
            return new CommandResult("mv: target already exists", false, false);
        }
        fileSystem.move(node, directory, name);
        return new CommandResult("", true, false);
    }

    private VfsDirectory findTargetDirectory(String arg){
        int lastSlash = arg.lastIndexOf('/');
        if (lastSlash == -1){
            return fileSystem.getCurrent();
        }
        String dirPath = arg.substring(0, lastSlash);
        if (dirPath.isEmpty()){
            return  fileSystem.getRoot();
        }
        VfsNode node = fileSystem.resolve(dirPath);
        if (node instanceof VfsDirectory){
            return (VfsDirectory) node;
        } else {
            return null;
        }
    }

    private boolean isInside(VfsNode node, VfsDirectory directory){
        VfsNode vfsnode = directory;
        while (vfsnode != null){
            if (vfsnode == node){
                return true;
            }
            vfsnode = vfsnode.getParent();
        }
        return false;
    }
}
