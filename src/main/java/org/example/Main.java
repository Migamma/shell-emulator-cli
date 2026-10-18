package org.example;
import org.example.config.Config;
import org.example.config.ConfigParser;

/**
 * Точка входа эмулятора командной оболочки.
 */
public class Main {
    /**
     * Запускает эмулятор.
     *
     * @param args аргументы командной строки, пока не используются
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

        Terminal terminal = new Terminal(config);

        terminal.run();
    }

    private static void printConfig(Config config){
        System.out.println("VFS path: " + config.getVfsPath());
        System.out.println("Prompt: " + config.getPrompt());
        System.out.println("Script: " + config.getScriptPath());
    }
}