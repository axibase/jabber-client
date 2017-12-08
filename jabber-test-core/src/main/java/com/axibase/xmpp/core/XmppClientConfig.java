package com.axibase.xmpp.core;

import org.apache.commons.cli.*;

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
        options.addOption(buildOption("insecure"));
        options.addOption(buildOption("debug"));

        options.addOption(Option.builder().longOpt("enable-auth").hasArgs().build());
        options.addOption(Option.builder().longOpt("disable-auth").hasArgs().build());

        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            throw Errors.errorExit("Invalid arguments", e);
        }
    }

    private Option buildOption(String optionName) {
        return Option.builder().longOpt(optionName).build();
    }

    private Option buildOption(String optionName, String argName) {
        return Option.builder().longOpt(optionName).hasArg().argName(argName).build();
    }

    public String getUser() {
        return getRequiredOption("user");
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
        String jid = getUser();
        int atSignPosition = jid.indexOf('@');
        if (atSignPosition >= 0) {
            return jid.substring(atSignPosition + 1);
        }
        throw Errors.errorExit("Cannot set XMPP domain, please specify --domain option");
    }

    public String[] getEnabledAuthMethods() {
        return commandLine.getOptionValues("enable-auth");
    }

    public String[] getDisabledAuthMethods() {
        return commandLine.getOptionValues("disable-auth");
    }

    public boolean getInsecure() {
        return commandLine.hasOption("insecure");
    }

    public boolean getDebug() {
        return commandLine.hasOption("debug");
    }

    private String getRequiredOption(String name) {
        if (!commandLine.hasOption(name)) {
            throw Errors.errorExit("Option --" + name + " is required");
        }
        return commandLine.getOptionValue(name);
    }
}
