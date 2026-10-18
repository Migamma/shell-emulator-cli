package org.example.command;

/**
 * Результат выполнения команды.
 */
public class CommandResult {
    private final String output;
    private final boolean success;
    private final boolean exit;

    /**
     * Создаёт результат выполнения команды.
     *
     * @param output  текст для вывода пользователю, может быть пустым
     * @param success true, если команда выполнена без ошибок
     * @param exit    true, если после команды эмулятор должен завершиться
     */
    public CommandResult(String output, boolean success, boolean exit) {
        this.output = output;
        this.success = success;
        this.exit = exit;
    }

    /**
     * @return текст для вывода пользователю; пустая строка, если выводить нечего
     */
    public String getOutput() {
        return output;
    }

    /**
     * @return true, если эмулятор должен завершить работу
     */
    public boolean isExit() {
        return exit;
    }

    /**
     * @return true, если команда выполнена без ошибок
     */
    public boolean isSuccess() {
        return success;
    }
}
