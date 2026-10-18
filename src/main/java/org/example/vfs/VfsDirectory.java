package org.example.vfs;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Папка виртуальной файловой системы.
 * <p>
 * Хранит вложенные папки и файлы, упорядоченные по имени.
 */
public class VfsDirectory extends VfsNode {
    private final Map<String, VfsNode> children = new TreeMap<>();

    /**
     * Создаёт пустую папку.
     *
     * @param name имя папки; у корня пустая строка
     */
    public VfsDirectory(String name) {
        super(name);
    }

    /**
     * Добавляет узел в папку и назначает ему эту папку родителем.
     *
     * @param child добавляемый файл или папка
     */
    public void addChild(VfsNode child) {
        children.put(child.getName(), child);
        child.setParent(this);
    }

    /**
     * Находит вложенный узел по имени.
     *
     * @param name имя узла
     * @return узел или null, если узла с таким именем нет
     */
    public VfsNode getChild(String name) {
        return children.get(name);
    }

    /**
     * @return все вложенные узлы в порядке имён
     */
    public Collection<VfsNode> getChildren() {
        return children.values();
    }
}
