package org.example;

import org.example.command.CommandResult;
import org.example.parser.ParsedCommand;
import org.example.parser.Parser;
import org.example.command.CommandRegistry;
import org.example.command.Command;
import org.example.config.Config;
import org.example.vfs.VfsDirectory;


import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.net.InetAddress;

/**
 * Интерактивный цикл эмулятора командной оболочки (REPL).
 * <p>
 * Показывает приглашение к вводу, читает строки пользователя,
 * передаёт их парсеру и выполняет найденные команды.
 * По умолчанию приглашение строится из имени пользователя и имени хоста
 * реальной ОС в формате username@hostname:~$, но может быть задано
 * параметром запуска. Перед интерактивным режимом может быть выполнен
 * стартовый скрипт.
 */
public class Terminal {
    private final String userName;
    private final String hostName;
    private static final String DEFAULT_HOSTNAME = "localhost";
    private final Parser parser;
    private final CommandRegistry commands;
    private final Config config;
    private final VfsDirectory vfsRoot;

    /**
     * Создаёт терминал и подготавливает данные из ОС.
     * <p>
     * Имя пользователя и имя хоста определяются в ОС.
     * Создаёт парсер с переменными окружения системы и реестр команд.
     *
     * @param config  настройки эмулятора из параметров командной строки
     * @param vfsRoot корень загруженной VFS или null, если VFS не задана
     */
    public Terminal(Config config, VfsDirectory vfsRoot) {
        this.hostName = resolveHostName();
        this.userName = System.getProperty("user.name");
        this.parser = new Parser(System.getenv());
        this.commands = new CommandRegistry();
        this.config = config;
        this.vfsRoot = vfsRoot;


    }

    /**
     * Получает название компьютера из ОС.
     *
     * @return имя компьютера; при ошибке ставится название localhost
     */
    private String resolveHostName() {
        try {
            InetAddress host = InetAddress.getLocalHost();
            return host.getHostName();
        } catch (UnknownHostException e) {
            return DEFAULT_HOSTNAME;
        }
    }

    /**
     * Собирает приглашение для ввода.
     *
     * @return пользовательское приглашение, если оно задано,
     * иначе строка в формате username@hostname:~$
     */
    private String buildPrompt() {
        if (config.getPrompt() != null) {
            return config.getPrompt();
        }
        return userName + "@" + hostName + ":~$ ";
    }

    /**
     * Разбирает строку, выделяет команду и выполняет ее.
     *
     * @param line строка введенная пользователем.
     * @return результат выполненной команды; если строку не удалось
     * разобрать или команда не найдена, результат с ошибкой.
     */
    private CommandResult executeLine(String line) {
        ParsedCommand parsed;
        try {
            parsed = parser.parse(line);

        } catch (IllegalArgumentException e) {
            return new CommandResult(e.getMessage(), false, false);
        }

        Command command = commands.find(parsed.getCommand());

        if (command == null) {
            return new CommandResult(parsed.getCommand() + ": command not found", false, false);
        }

        return command.execute(parsed.getArgument());
    }

    /**
     * Выполняет стартовый скрипт, если он задан, и запускает цикл ввода команд.
     * <p>
     * Метод завершается по команде exit или при достижении конца ввода.
     */
    public void run() {
        String scriptPath = config.getScriptPath();
        if (scriptPath != null) {
            if (!runScript(scriptPath)) {
                return;
            }
        }

        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print(buildPrompt());
            if (!input.hasNextLine()) {
                System.out.println();
                break;
            }
            String line = input.nextLine();
            if (line.isBlank()) {
                continue;
            }

            CommandResult result = executeLine(line);
            if (!result.getOutput().isEmpty()) {
                System.out.println(result.getOutput());
            }

            if (result.isExit()) {
                break;
            }
        }
    }

    /**
     * Выполняет стартовый скрипт построчно, показывая каждую команду и её вывод.
     * <p>
     * Пустые строки пропускаются. Выполнение останавливается на первой
     * команде с ошибкой, с указанием номера строки.
     *
     * @param scriptPath путь к файлу скрипта
     * @return true, если после скрипта нужно перейти в интерактивный режим;
     * false, если скрипт выполнил exit или файл не удалось прочитать
     */
    private boolean runScript(String scriptPath) {
        Path path = Path.of(scriptPath);
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("cannot read script");
            return false;
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                continue;
            }
            System.out.println(buildPrompt() + line);
            CommandResult result = executeLine(line);
            if (!result.getOutput().isEmpty()) {
                System.out.println(result.getOutput());
            }
            if (!result.isSuccess()) {
                System.err.println("script stopped at line " + (i + 1));
                return true;
            }
            if (result.isExit()) {
                return false;
            }
        }
        return true;
    }
}