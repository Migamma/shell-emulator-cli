package org.example.config;

public class ConfigParser {
    private static final String VFS = "--vfs";
    private static final String PROMPT = "--prompt";
    private static final String SCRIPT = "--script";

    public static Config parse(String[] args) {
        String vfsPath = null;
        String prompt = null;
        String script = null;
        for (int i = 0; i < args.length; i++) {
            String conf = args[i];
            if (conf.equals(VFS)) {
                vfsPath = readValue(args, i, VFS);
                i++;
            } else if (conf.equals(PROMPT)) {
                prompt = readValue(args, i, PROMPT);
                i++;
            } else if (conf.equals(SCRIPT)) {
                script = readValue(args, i, SCRIPT);
                i++;
            } else {
                throw new IllegalArgumentException("unknown option: " + conf);
            }
        }
        return new Config(vfsPath, prompt, script);
    }

    private static String readValue(String[] args, int index, String option ){
        if (index + 1 >= args.length){
            throw new IllegalArgumentException("missing value for " + option);
        }
        if (args[index + 1].startsWith("--")){
            throw new IllegalArgumentException("missing value for " + option );
        }
        return args[index + 1];
    }
}
