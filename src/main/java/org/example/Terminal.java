package org.example;
import org.example.parser.ParsedCommand;
import org.example.parser.Parser;

import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.InetAddress;

public class Terminal {
    private final String userName;
    private final String hostName;
    private static final String DEFAULT_HOSTNAME = "localhost";
    private final Parser parser;

    public Terminal(){
        this.hostName = resolveHostName();
        this.userName = System.getProperty("user.name");
        this.parser = new Parser(System.getenv());
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

    public void run(){
        Scanner input = new Scanner(System.in);
        while (true){
            System.out.print(buildPrompt());
            if (!input.hasNextLine()){
                System.out.println();
                break;
            }
            String command = input.nextLine();
            if (command.isBlank()){
                continue;
            }
            ParsedCommand parsed = parser.parse(command);
            System.out.println(parsed.toString());
        }
    }
}