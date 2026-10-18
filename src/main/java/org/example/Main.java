package org.example;

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
        Terminal terminal = new Terminal();

        terminal.run();
    }
}