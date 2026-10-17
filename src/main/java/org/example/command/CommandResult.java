package org.example.command;

public class CommandResult {
    private final String output;
    private final boolean success;
    private final boolean exit;

    public CommandResult(String output, boolean success, boolean exit){
        this.output = output;
        this.success = success;
        this.exit = exit;
    }

    public String getOutput() {
        return output;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isSuccess(){
        return success;
    }
}
