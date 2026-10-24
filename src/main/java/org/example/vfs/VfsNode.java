package org.example.vfs;

/**
 * Узел виртуальной файловой системы: файл или папка.
 * <p>
 * Хранит имя узла и папку, в которой он лежит.
 */
public abstract class VfsNode {
    private String name;
    private VfsDirectory parent;

    /**
     * Создаёт узел с заданным именем.
     *
     * @param name имя узла; у корневой папки пустая строка
     */
    public VfsNode(String name) {
        this.name = name;
    }

    /**
     * @return имя узла
     */
    public String getName() {
        return name;
    }

    /**
     * @return папка, в которой лежит узел; null для корня
     */
    public VfsDirectory getParent() {
        return parent;
    }

    /**
     * Меняет имя узла. Вызывается только при перемещении из {@link VirtualFileSystem}.
     *
     * @param name новое имя узла
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * Задаёт папку, в которой лежит узел. Вызывается только из {@link VfsDirectory}.
     *
     * @param parent родительская папка
     */
    void setParent(VfsDirectory parent) {
        this.parent = parent;
    }
}
