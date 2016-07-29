package com.andybug.jaspe;

import java.io.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


class Config
{
    private ConfigFile configFile;


    public Config(String[] args) throws FileNotFoundException
    {
        parseArgs(args);
        System.out.println(configFile);
    }

    private void parseArgs(String[] args) throws FileNotFoundException
    {
        CommandLine line = null;
        CommandLineParser parser = new DefaultParser();
        Options options = buildOptionsList();

        try {
            line = parser.parse(options, args);
        }
        catch (ParseException e) {
            System.err.println(e);
            System.exit(1);
        }

        if (line.hasOption("config"))
            this.configFile = ConfigFile.parse(line.getOptionValue("config"));
        else
            this.configFile = ConfigFile.getDefaultConfigFile();

    }

    private static Options buildOptionsList()
    {
        Options options = new Options();

        Option config = OptionBuilder
            .withLongOpt("config")
            .withDescription("path to the jaspe config file")
            .hasArg()
            .withArgName("config file")
            .create();

        options.addOption(config);

        return options;
    }


    /* getters */

    public short getRedisPort()
    {
        return configFile.servers.redis;
    }

    public String getLocalDatabasePath()
    {
        return configFile.local_database.path;
    }
}
