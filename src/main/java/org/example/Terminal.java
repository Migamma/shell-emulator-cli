package org.example;
import org.example.command.CommandResult;
import org.example.parser.ParsedCommand;
import org.example.parser.Parser;
import org.example.command.CommandRegistry;
import org.example.command.Command;

import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.InetAddress;

public class Terminal {
    private final String userName;
    private final String hostName;
    private static final String DEFAULT_HOSTNAME = "localhost";
    private final Parser parser;
    private final CommandRegistry commands;

    public Terminal(){
        this.hostName = resolveHostName();
        this.userName = System.getProperty("user.name");
        this.parser = new Parser(System.getenv());
        this.commands = new CommandRegistry();

    }

    private String resolveHostName(){
        try{
            InetAddress host = InetAddress.getLocalHost();
            return  host.getHostName();
        } catch (UnknownHostException e){
            return DEFAULT_HOSTNAME;
        }
    }
    private String buildPrompt(){
        return userName + "@" + hostName + ":~$ ";
    }

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