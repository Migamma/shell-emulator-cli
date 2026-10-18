package org.example.vfs;

/**
 * Ошибка загрузки виртуальной файловой системы из XML.
 */
public class VfsLoadException extends Exception {
    /**
     * Создаёт исключение с описанием ошибки.
     *
     * @param message текст ошибки для пользователя
     */
    public VfsLoadException(String message) {
        super(message);
    }
}
