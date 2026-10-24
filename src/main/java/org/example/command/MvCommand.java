package org.example.command;

import org.example.vfs.VfsDirectory;
import org.example.vfs.VfsNode;
import org.example.vfs.VirtualFileSystem;

/**
 * Команда mv: перемещает или переименовывает файл, или папку.
 * <p>
 * Если второй аргумент - существующая папка, узел переносится в неё
 * с прежним именем. Иначе последняя часть второго аргумента становится
 * новым именем, а остальная часть указывает папку назначения.
 * Изменения происходят только в памяти.
 */
public class MvCommand implements Command {
    private final VirtualFileSystem fileSystem;
    private final static int MAX_ARGS = 2;

    /**
     * @param fileSystem файловая система, с которой работает команда
     */
    public MvCommand(VirtualFileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public CommandResult execute(String[] args) {
        if (args.length != MAX_ARGS) {
            return new CommandResult("mv: expected 2 arguments", false, false);
        }

        VfsNode node = fileSystem.resolve(args[0]);
        if (node == null) {
            return new CommandResult("mv: no such file or directory " + args[0], false, false);
        }

        if (node == fileSystem.getRoot()) {
            return new CommandResult("mv: cannot move root ", false, false);
        }

        VfsNode target = fileSystem.resolve(args[1]);

        if (target != null && target instanceof VfsDirectory) {
            return moveTo(node, (VfsDirectory) target, node.getName());
        } else {
            VfsDirectory targetDirectory = findTargetDirectory(args[1]);
            if (targetDirectory == null) {
                return new CommandResult("mv: target directory not found: " + args[1], false, false);
            }
            String newName = args[1].substring(args[1].lastIndexOf("/") + 1);
            return moveTo(node, targetDirectory, newName);
        }
    }

    /**
     * Проверяет перемещение и выполняет его.
     *
     * @param node      перемещаемый узел
     * @param directory папка назначения
     * @param name      имя узла в папке назначения
     * @return успех или ошибка, если папка переносится в саму себя
     * или имя в папке назначения уже занято
     */
    private CommandResult moveTo(VfsNode node, VfsDirectory directory, String name) {
        if (isInside(node, directory)) {
            return new CommandResult("mv: cannot move a directory into itself", false, false);
        }
        if (directory.getChild(name) != null) {
            return new CommandResult("mv: target already exists", false, false);
        }
        fileSystem.move(node, directory, name);
        return new CommandResult("", true, false);
    }

    /**
     * Находит папку назначения по второму аргументу mv: часть пути
     * до последней косой черты.
     *
     * @param arg второй аргумент mv
     * @return текущая папка, если косой черты нет; корень, если путь
     * вида /имя; иначе найденная папка или null, если её нет
     */
    private VfsDirectory findTargetDirectory(String arg) {
        int lastSlash = arg.lastIndexOf('/');
        if (lastSlash == -1) {
            return fileSystem.getCurrent();
        }
        String dirPath = arg.substring(0, lastSlash);
        if (dirPath.isEmpty()) {
            return fileSystem.getRoot();
        }
        VfsNode node = fileSystem.resolve(dirPath);
        if (node instanceof VfsDirectory) {
            return (VfsDirectory) node;
        } else {
            return null;
        }
    }

    /**
     * Проверяет, находится ли папка внутри узла или совпадает с ним.
     *
     * @param node      перемещаемый узел
     * @param directory папка назначения
     * @return true, если перемещение поместило бы узел внутрь самого себя
     */
    private boolean isInside(VfsNode node, VfsDirectory directory) {
        VfsNode vfsnode = directory;
        while (vfsnode != null) {
            if (vfsnode == node) {
                return true;
            }
            vfsnode = vfsnode.getParent();
        }
        return false;
    }
}
