# shell-emulator-cli

Эмулятор командной оболочки UNIX-подобной ОС, написанный на Java.
Учебная практическая работа по дисциплине конфигурационного управления, вариант 7.

## Общее описание

Программа работает в терминале в режиме REPL: показывает приглашение к вводу,
читает команду, выполняет её и выводит результат. Работа эмулятора максимально
приближена к работе в командной строке UNIX.

Все пять этапов работы выполнены: REPL, конфигурация, виртуальная файловая
система, основные команды и команда mv, изменяющая файловую систему в памяти.

## Функции и настройки

### Приглашение к вводу

По умолчанию формируется из реальных данных ОС и текущей папки VFS
в формате `username@hostname:путь$`, например `mike@laptop:/home/user$`.
Если имя хоста определить не удалось, используется `localhost`.
Приглашение можно заменить своим с помощью параметра `--prompt`.

### Переменные окружения

Перед выполнением команды переменные вида `$NAME` заменяются значениями
из окружения реальной ОС. Неизвестные переменные заменяются пустой строкой.

### Команды

| Команда | Описание |
|---|---|
| `ls [путь]` | выводит содержимое текущей или указанной папки; для файла выводит его имя |
| `cd [путь]` | переходит в указанную папку; без аргумента переходит в корень |
| `tac файл` | выводит строки файла в обратном порядке |
| `mv откуда куда` | перемещает или переименовывает файл или папку |
| `uname` | выводит название ОС, в которой запущен эмулятор |
| `exit` | завершает работу эмулятора |

Пути могут быть абсолютными (`/home/user`) и относительными (`docs/notes.txt`),
поддерживаются `.` и `..`. Работа также завершается при конце ввода (Ctrl+D).

### Команда mv

Режим работы зависит от второго аргумента:

| Второй аргумент | Пример | Результат |
|---|---|---|
| существующая папка | `mv readme.txt tmp` | файл переносится в папку с прежним именем |
| несуществующее имя | `mv readme.txt info.txt` | переименование в текущей папке |
| путь с несуществующим именем | `mv readme.txt tmp/info.txt` | перенос в папку с новым именем |

Папки переносятся вместе со всем содержимым. Изменения происходят
только в памяти, исходный XML-файл не изменяется.

### Параметры запуска

| Параметр | Описание |
|---|---|
| `--vfs путь` | путь к XML-файлу виртуальной файловой системы |
| `--prompt текст` | пользовательское приглашение вместо стандартного |
| `--script путь` | путь к стартовому скрипту |

Все параметры необязательные и могут идти в любом порядке.
При запуске эмулятор выводит значения всех параметров.
Без `--vfs` используется пустая файловая система.

### Стартовый скрипт

Текстовый файл с командами эмулятора, по одной на строку.
Перед интерактивным режимом команды выполняются по очереди,
на экран выводятся и сами команды, и их результат.

- Пустые строки пропускаются.
- На первой ошибке выполнение скрипта останавливается с указанием
  номера строки, после чего начинается интерактивный режим.
- Если скрипт выполняет `exit`, эмулятор завершается.
- Если файл скрипта не удалось прочитать, эмулятор завершается.

### Виртуальная файловая система (VFS)

VFS загружается из XML-файла и хранится только в памяти,
исходный файл не изменяется.

Формат:

```xml
<vfs>
    <dir name="home">
        <file name="notes.txt">SGVsbG8=</file>
    </dir>
</vfs>
```

- `vfs` - корневой элемент, корневая папка `/`;
- `dir` - папка, может содержать другие `dir` и `file`;
- `file` - файл, содержимое закодировано в base64;
- атрибут `name` обязателен у `dir` и `file`.

Примеры VFS лежат в `tests/vfs`: минимальная, с несколькими файлами
и с вложенностью больше трёх уровней.

Ошибки загрузки, после которых эмулятор завершается с кодом 1:

| Ситуация | Сообщение |
|---|---|
| файл не найден | `VFS file not found: путь` |
| неверный XML | `invalid XML in VFS file: ...` |
| корень не `vfs` | `root element must be vfs` |
| неизвестный тег | `unknown element тег` |
| нет атрибута name | `missing name attribute in тег` |
| неверный base64 | `invalid base64 content in file имя` |

### Обработка ошибок

Ошибки во время работы, после которых эмулятор продолжает работу:

| Ситуация | Сообщение |
|---|---|
| неизвестная команда | `имя: command not found` |
| лишние аргументы | `cd: too many arguments` |
| путь не найден в ls или tac | `ls: cannot access 'путь': No such file or directory` |
| путь не найден в cd | `cd: no such file or directory: путь` |
| cd на файл | `cd: not a directory: путь` |
| tac на папку | `tac: путь: Is a directory` |
| tac без файла | `tac: missing file operand` |
| mv с неверным числом аргументов | `mv: expected 2 arguments` |
| mv: исходный путь не найден | `mv: no such file or directory путь` |
| mv корня | `mv: cannot move root` |
| перенос папки в саму себя | `mv: cannot move a directory into itself` |
| в папке назначения уже есть узел с таким именем | `mv: target already exists` |
| папка назначения не найдена | `mv: target directory not found: путь` |
| после подстановки переменных не осталось команды | `no command after variable expansion` |
| ошибка в стартовом скрипте | `script stopped at line N` |

Ошибки при запуске, после которых эмулятор завершается с кодом 1:

| Ситуация | Сообщение |
|---|---|
| неизвестный параметр | `unknown option: --foo` |
| параметр без значения | `missing value for --vfs` |
| файл скрипта не найден | `cannot read script` |

## Сборка и запуск

Требуется Java 17 или новее и Maven.

Сборка:

```
mvn package
```

Запуск:

```
./run.sh
```

Запуск с параметрами:

```
./run.sh --vfs tests/vfs/deep.xml --script tests/scripts/stage5.txt
```

Скрипт `run.sh` собирает проект и запускает эмулятор, передавая ему все параметры.

### Тесты

Тестовые скрипты лежат в папке `tests`, стартовые скрипты эмулятора
в `tests/scripts`, примеры VFS в `tests/vfs`. Тесты можно запускать из любой папки:

```
./tests/test_prompt.sh
./tests/test_script.sh
./tests/test_all_options.sh
./tests/test_invalid_options.sh
./tests/test_vfs.sh
./tests/test_commands.sh
./tests/test_mv.sh
```

## Примеры использования

Работа с VFS:

```
$ ./run.sh --vfs tests/vfs/deep.xml
VFS path: tests/vfs/deep.xml
Prompt: null
Script: null
VFS loaded
mike@tazik-logovazik:/$ ls
home  readme.txt  tmp
mike@tazik-logovazik:/$ cd home/user/docs
mike@tazik-logovazik:/home/user/docs$ ls
notes.txt  old
mike@tazik-logovazik:/home/user/docs$ tac notes.txt
line3
line2
line1
mike@tazik-logovazik:/home/user/docs$ cd ..
mike@tazik-logovazik:/home/user$ uname
Linux
mike@tazik-logovazik:/home/user$ cd nope
cd: no such file or directory: nope
mike@tazik-logovazik:/home/user$ exit
```

Перемещение и переименование:

```
$ ./run.sh --vfs tests/vfs/deep.xml
VFS path: tests/vfs/deep.xml
Prompt: null
Script: null
VFS loaded
mike@tazik-logovazik:/$ mv readme.txt tmp
mike@tazik-logovazik:/$ ls tmp
readme.txt
mike@tazik-logovazik:/$ mv tmp/readme.txt info.txt
mike@tazik-logovazik:/$ ls
home  info.txt  tmp
mike@tazik-logovazik:/$ mv home/user/docs tmp
mike@tazik-logovazik:/$ ls /tmp/docs
notes.txt  old
mike@tazik-logovazik:/$ mv home home/user
mv: cannot move a directory into itself
mike@tazik-logovazik:/$ exit
```

Стартовый скрипт с ошибкой и своим приглашением:

```
$ ./run.sh --prompt "emu> " --script tests/scripts/script_error.txt
VFS path: null
Prompt: emu> 
Script: tests/scripts/script_error.txt
emu> ls
emu> pwd
pwd: command not found
script stopped at line 2
emu> exit
```

Неверный формат VFS:

```
$ ./run.sh --vfs tests/vfs/wrong.xml
VFS path: tests/vfs/wrong.xml
Prompt: null
Script: null
unknown element folder
```