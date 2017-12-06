package com.axibase.xmpp;

import org.apache.commons.cli.*;

class ArgumentsManager {
    private CommandLine commandLine;

    ArgumentsManager(String[] args) {
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption(buildOption("user", "JID"));
        options.addOption(buildOption("password", "PASS"));
        options.addOption(buildOption("host", "HOST"));
        options.addOption(buildOption("port", "PORT"));
        options.addOption(buildOption("domain", "DOMAIN"));
        options.addOption(buildOption("insecure"));
        options.addOption(buildOption("debug"));
        options.addOption(buildOption("plain-auth"));
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            Errors.errorExit("Invalid arguments", e);
        }
    }

    private Option buildOption(String optionName) {
        return Option.builder().longOpt(optionName).build();
    }

    private Option buildOption(String optionName, String argName) {
        return Option.builder().longOpt(optionName).hasArg().argName(argName).build();
    }

    public String getOption(String optionName) {
        return commandLine.getOptionValue(optionName);
    }

    public String getOption(String optionName, boolean required) {
        if (!commandLine.hasOption(optionName)) {
            Errors.errorExit("Option " + optionName + " is required");
        }
        return commandLine.getOptionValue(optionName);
    }

    public boolean getBooleanOption(String optionName) {
        return commandLine.hasOption(optionName);
    }
}
