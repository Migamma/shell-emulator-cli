package org.example;

import org.example.config.Config;
import org.example.config.ConfigParser;
import org.example.vfs.VfsDirectory;
import org.example.vfs.VfsLoadException;
import org.example.vfs.VfsLoader;

/**
 * Точка входа эмулятора командной оболочки.
 */
public class Main {
    /**
     * Разбирает параметры командной строки, выводит их и запускает эмулятор.
     * <p>
     * При неверных параметрах выводит ошибку и завершается с кодом 1.
     *
     * @param args параметры запуска: --vfs, --prompt и --script
     */
    public static void main(String[] args) {
        Config config;
        try {
            config = ConfigParser.parse(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return;
        }
        printConfig(config);

        VfsDirectory vfsRoot = null;
        if (config.getVfsPath() != null){
            try{
                vfsRoot = VfsLoader.load(config.getVfsPath());
                System.out.println("VFS loaded");
            } catch (VfsLoadException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

        Terminal terminal = new Terminal(config, vfsRoot);

        terminal.run();
    }

    /**
     * Выводит значения всех параметров запуска для отладки.
     *
     * @param config настройки эмулятора
     */
    private static void printConfig(Config config) {
        System.out.println("VFS path: " + config.getVfsPath());
        System.out.println("Prompt: " + config.getPrompt());
        System.out.println("Script: " + config.getScriptPath());
    }
}