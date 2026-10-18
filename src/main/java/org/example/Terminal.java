package org.example;
import org.example.command.CommandResult;
import org.example.parser.ParsedCommand;
import org.example.parser.Parser;
import org.example.command.CommandRegistry;
import org.example.command.Command;

import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.InetAddress;

/**
 * Интерактивный цикл эмулятора командной оболочки (REPL).
 * <p>
 * Показывает приглашение к вводу, читает строки пользователя,
 * передаёт их парсеру и выполняет найденные команды.
 * Приглашение строится из имени пользователя и имени хоста
 * реальной ОС в формате username@hostname:~$.
 */
public class Terminal {
    private final String userName;
    private final String hostName;
    private static final String DEFAULT_HOSTNAME = "localhost";
    private final Parser parser;
    private final CommandRegistry commands;

    /**
     * Создает терминал и подготавливает данные из ОС.
     * Имя пользователя и имя хоста определяется в ОС.
     * Создает парсер с переменными окружения системы и реестр команд.
     */
    public Terminal(){
        this.hostName = resolveHostName();
        this.userName = System.getProperty("user.name");
        this.parser = new Parser(System.getenv());
        this.commands = new CommandRegistry();

    }

    /**
     * Получает название компьютера из ОС.
     *
     * @return имя компьютера; при ошибке ставится название localhost
     */
    private String resolveHostName(){
        try{
            InetAddress host = InetAddress.getLocalHost();
            return  host.getHostName();
        } catch (UnknownHostException e){
            return DEFAULT_HOSTNAME;
        }
    }

    /**
     * Собирает приглашение для ввода.
     *
     * @return строка приглашения в форме username@hostname:~$.
     */
    private String buildPrompt(){
        return userName + "@" + hostName + ":~$ ";
    }

    /**
     * Разбирает строку, выделяет команду и выполняет ее.
     *
     * @param line строка введенная пользователем.
     * @return результат выполненной команды; если строку не удалось
     * разобрать или команда не найдена, результат с ошибкой.
     */
    private CommandResult executeLine(String line){
        ParsedCommand parsed;
        try{
            parsed = parser.parse(line);

        } catch (IllegalArgumentException e){
            return  new CommandResult(e.getMessage(), false, false);
        }

        Command command = commands.find(parsed.getCommand());

        if (command == null){
            return new CommandResult(parsed.getCommand() + ": command not found", false, false);
        }

        return command.execute(parsed.getArgument());
    }

    /**
     * Запускает цикл ввода и вывода команд.
     * <p>
     * Метод завершается по команде exit или при достижении конца ввода.
     */
    public void run(){
        Scanner input = new Scanner(System.in);
        while (true){
            System.out.print(buildPrompt());
            if (!input.hasNextLine()){
                System.out.println();
                break;
            }
            String line = input.nextLine();
            if (line.isBlank()){
                continue;
            }

            CommandResult result = executeLine(line);
            if (!result.getOutput().isEmpty()){
                System.out.println(result.getOutput());
            }

            if (result.isExit()){
                break;
            }
        }
    }
}