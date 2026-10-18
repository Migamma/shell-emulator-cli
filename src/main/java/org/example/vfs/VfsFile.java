package org.example.vfs;

/**
 * Файл виртуальной файловой системы.
 */
public class VfsFile extends VfsNode {
    private final byte[] content;

    /**
     * Создаёт файл.
     *
     * @param name    имя файла
     * @param content содержимое файла, уже раскодированное из base64
     */
    public VfsFile(String name, byte[] content) {
        super(name);
        this.content = content;
    }

    /**
     * @return содержимое файла в виде массива байтов
     */
    public byte[] getContent() {
        return content;
    }
}
