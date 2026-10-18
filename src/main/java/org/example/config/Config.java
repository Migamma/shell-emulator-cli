package org.example.config;

public class Config {
    private final String vfsPath;
    private final String prompt;
    private final String scriptPath;

    public Config(String vfsPath, String prompt, String scriptPath) {
        this.vfsPath = vfsPath;
        this.prompt = prompt;
        this.scriptPath = scriptPath;
    }

    public String getVfsPath(){
        return vfsPath;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getScriptPath(){
        return scriptPath;
    }
}
