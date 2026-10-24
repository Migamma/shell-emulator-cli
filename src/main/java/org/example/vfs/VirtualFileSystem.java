package org.example.vfs;

/**
 * Виртуальная файловая система во время работы эмулятора.
 * <p>
 * Хранит корень дерева и текущую папку, находит узлы по пути
 * и строит путь узла. Все команды работают с одним объектом этого класса.
 */
public class VirtualFileSystem {
    private final VfsDirectory root;
    private VfsDirectory current;

    /**
     * Создаёт файловую систему; текущей папкой становится корень.
     *
     * @param root корневая папка загруженной VFS
     */
    public VirtualFileSystem(VfsDirectory root) {
        this.root = root;
        this.current = root;
    }

    /**
     * @return текущая папка
     */
    public VfsDirectory getCurrent() {
        return current;
    }

    /**
     * @return корневая папка
     */
    public VfsDirectory getRoot() {
        return root;
    }

    /**
     * Делает папку текущей.
     *
     * @param current новая текущая папка
     */
    public void setCurrent(VfsDirectory current) {
        this.current = current;
    }

    /**
     * Находит узел по абсолютному или относительному пути.
     * <p>
     * Путь, начинающийся с /, ищется от корня, иначе от текущей папки.
     * Части . пропускаются, .. поднимают к родителю; у корня .. остаётся в корне.
     *
     * @param path путь к файлу или папке
     * @return найденный узел или null, если такого пути нет
     */
    public VfsNode resolve(String path) {
        VfsNode node;
        if (path.startsWith("/")) {
            node = root;
        } else {
            node = current;
        }
        for (String part : path.split("/")) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            }
            if (part.equals("..")) {
                if (node.getParent() != null) {
                    node = node.getParent();
                }
                continue;
            }
            if (!(node instanceof VfsDirectory)) {
                return null;
            }
            node = ((VfsDirectory) node).getChild(part);
            if (node == null) {
                return null;
            }
        }
        return node;
    }

    /**
     * Строит абсолютный путь узла, поднимаясь по родителям до корня.
     *
     * @param node узел дерева
     * @return путь вида /home/user; для корня /
     */
    public String getPath(VfsNode node) {
        if (node == root) {
            return "/";
        }
        StringBuilder path = new StringBuilder();
        while (node.getParent() != null) {
            path.insert(0, "/" + node.getName());
            node = node.getParent();
        }
        return path.toString();
    }

    public void move(VfsNode node, VfsDirectory target, String newName){
        node.getParent().removeChild(node.getName());
        node.setName(newName);
        target.addChild(node);
    }
}

