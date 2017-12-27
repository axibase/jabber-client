package com.axibase.xmpp.core;

import org.apache.commons.cli.*;

import java.nio.file.Path;
import java.util.Collections;

public class XmppClientConfig {
    private static final int DEFAULT_PORT = 5222;

    private CommandLine commandLine;

    public XmppClientConfig(String[] args) {
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption(buildOption("user", "JID"));
        options.addOption(buildOption("password", "PASS"));
        options.addOption(buildOption("host", "HOST"));
        options.addOption(buildOption("port", "PORT"));
        options.addOption(buildOption("domain", "DOMAIN"));
        options.addOption(buildOption("auth", "MECHANISM"));
        options.addOption(buildOption("to", "JID"));
        options.addOption(buildOption("nick", "NAME"));
        options.addOption(buildOption("room-password", "PASSWORD"));
        options.addOption(buildOption("insecure"));
        options.addOption(buildOption("debug"));

        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            throw Errors.errorExit("Invalid arguments", e);
        }

        logOptions();
    }

    private Option buildOption(String optionName) {
        return Option.builder().longOpt(optionName).build();
    }

    private Option buildOption(String optionName, String argName) {
        return Option.builder().longOpt(optionName).hasArg().argName(argName).build();
    }

    private void logOptions() {
        for (Option opt : commandLine.getOptions()) {
            StringBuilder message = new StringBuilder();
            String optionName = opt.getLongOpt();
            message.append(optionName);
            if (opt.hasArg()) {
                message.append(" =");
                for (String value : opt.getValues()) {
                    if (optionName.equals("password")) {
                        value = String.join("", Collections.nCopies(value.length(), "*"));
                    }
                    message.append(" ").append(value);
                }
            } else {
                message.append(" is enabled");
            }
            Debug.info(message.toString());
        }
    }

    public String getUser() {
        String user = getRequiredOption("user");
        if (user.contains("@")) {
            user = user.substring(0, user.indexOf('@'));
        }
        return user;
    }

    public String getPassword() {
        return getRequiredOption("password");
    }

    public String getHost() {
        return getRequiredOption("host");
    }

    public int getPort() {
        if (!commandLine.hasOption("port")) {
            return DEFAULT_PORT;
        }
        return Integer.valueOf(getRequiredOption("port"));
    }

    public String getDomain() {
        String domain = commandLine.getOptionValue("domain");
        if (domain != null) {
            return domain;
        }
        String jid = getRequiredOption("user");
        int atSignPosition = jid.indexOf('@');
        if (atSignPosition >= 0) {
            return jid.substring(atSignPosition + 1);
        }
        throw Errors.errorExit("Cannot set XMPP domain, please specify --domain option");
    }

    public String getAuth() {
        return commandLine.getOptionValue("auth");
    }

    public boolean getInsecure() {
        return commandLine.hasOption("insecure");
    }

    public boolean getDebug() {
        return commandLine.hasOption("debug");
    }

    public String getTo() {
        return getRequiredOption("to");
    }

    public String getNick() {
        return getRequiredOption("nick");
    }

    public String getRoomPassword() {
        return commandLine.getOptionValue("room-password");
    }

    private String getRequiredOption(String name) {
        if (!commandLine.hasOption(name)) {
            throw Errors.errorExit("Option --" + name + " is required");
        }
        return commandLine.getOptionValue(name);
    }
}
