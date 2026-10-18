package org.example.config;

/**
 * Настройки эмулятора, заданные параметрами командной строки.
 * <p>
 * Параметр, который не был передан при запуске, равен null.
 */
public class Config {
    private final String vfsPath;
    private final String prompt;
    private final String scriptPath;

    /**
     * Создаёт настройки эмулятора.
     *
     * @param vfsPath    путь к XML-файлу виртуальной файловой системы или null
     * @param prompt     пользовательское приглашение к вводу или null
     * @param scriptPath путь к стартовому скрипту или null
     */
    public Config(String vfsPath, String prompt, String scriptPath) {
        this.vfsPath = vfsPath;
        this.prompt = prompt;
        this.scriptPath = scriptPath;
    }

    /**
     * @return путь к XML-файлу VFS; null, если параметр не задан
     */
    public String getVfsPath() {
        return vfsPath;
    }

    /**
     * @return пользовательское приглашение; null, если используется стандартное
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * @return путь к стартовому скрипту; null, если скрипт не задан
     */
    public String getScriptPath() {
        return scriptPath;
    }
}
