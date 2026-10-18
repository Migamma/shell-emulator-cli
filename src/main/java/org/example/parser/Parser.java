package org.example.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Разбивает строку ввода на имя команды и аргументы.
 * <p>
 * Строка делится на слова по пробельным символам.
 * Переменная окружения вида $NAME заменяется их значениями.
 * Неизвестные переменные заменяются пустой строкой, а слова, ставшие пустыми, отбрасываются.
 */
public class Parser {
    private final Map<String, String> variables;
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$(\\w+)");

    /**
     * Создает парсер с заданными переменными окружения.
     *
     * @param variables переменные окружения используемые при подстановке.
     */
    public Parser(Map<String, String> variables) {
        this.variables = variables;
    }

    /**
     * Разбирает строку ввода на имя команды и аргументы.
     *
     * @param line строка, введённая пользователем
     * @return разобранная команда с именем и аргументами
     * @throws IllegalArgumentException если строка пустая или после
     *                                  подстановки переменных не осталось ни одного слова
     */
    public ParsedCommand parse(String line) {

        String command = line.trim();

        if (command.isBlank()) {
            throw new IllegalArgumentException("empty input");
        }

        String[] commandAll = command.split("\\s+");

        List<String> expanded = new ArrayList<>();

        for (String name : commandAll) {
            String expandVariable = expandVariables(name);
            if (!expandVariable.isEmpty()) {
                expanded.add(expandVariable);
            }
        }


        if (expanded.isEmpty()) {
            throw new IllegalArgumentException("no command after variable expansion");
        }

        String commandName = expanded.get(0);
        String[] arguments = expanded.subList(1, expanded.size()).toArray(new String[0]);
        ParsedCommand obj = new ParsedCommand(commandName, arguments);

        return obj;
    }

    /**
     * Заменяет переменные окружения вида $NAME в слове их значениями.
     *
     * @param word слово из строки ввода
     * @return слово с подставленными значениями; неизвестные переменные
     * заменяются пустой строкой
     */
    private String expandVariables(String word) {
        Matcher matcher = VARIABLE_PATTERN.matcher(word);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String name = matcher.group(1);
            String value = variables.getOrDefault(name, "");
            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
