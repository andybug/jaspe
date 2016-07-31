package com.andybug.jaspe;

import java.io.*;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.moandjiezana.toml.Toml;


class Config
{
    private Map<String, Object> cvars;


    /* public methods */

    public Config(String[] args)
    {
        cvars = new HashMap<String, Object>();
        loadDefaultCvars();

        parseArgs(args);
        System.out.println(this);
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("--- config ---\n");
        sb.append("jaspe.root = " + cvars.get("jaspe.root") + "\n");
        sb.append("jaspe.port = " + cvars.get("jaspe.port") + "\n");
        sb.append("kvs.type = " + cvars.get("kvs.type") + "\n");
        sb.append("kvs.port = " + cvars.get("kvs.port") + "\n");
        sb.append("database.type = " + cvars.get("database.type") + "\n");
        sb.append("database.port = " + cvars.get("database.port") + "\n");

        return sb.toString();
    }


    /* cvar methods */

    private void loadDefaultCvars()
    {
        cvars.put("jaspe.root", "/usr/share/jaspe");
        cvars.put("jaspe.port", new Short((short) 30500));
        cvars.put("kvs.type", "redis");
        cvars.put("kvs.port", new Short((short) 6379));
        cvars.put("database.type", "postgres");
        cvars.put("database.port", new Short((short) 5432));
    }


    /* argument parsing */

    private void parseArgs(String[] args)
    {
        CommandLine line = null;
        CommandLineParser parser = new DefaultParser();
        Options options = this.buildOptionsList();

        try {
            line = parser.parse(options, args);
        }
        catch (ParseException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        if (line.hasOption("config"))
            readConfigFile(line.getOptionValue("config"));
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


    /* config file parsing */

    private void readConfigFile(String path)
    {
        /* handle ~ in file path */
        File file = new File(path.replaceFirst("^~", System.getProperty("user.home")));
        Toml conf = null;

        try {
            /* make sure the file exists */
            if (!file.isFile())
                throw new FileNotFoundException("Unable to read config file " + file.getPath());

            conf = new Toml().read(file);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        readConfigFileSettings(conf);
    }

    private void readConfigFileSettings(Toml conf)
    {
        readConfigFileString(conf, "jaspe.root");
        readConfigFileShort(conf, "jaspe.port");
        readConfigFileString(conf, "kvs.type");
        readConfigFileShort(conf, "kvs.port");
        readConfigFileString(conf, "database.type");
        readConfigFileShort(conf, "database.port");
    }

    private void readConfigFileString(Toml conf, String cvar)
    {
        String str = conf.getString(cvar);
        if (str != null)
            cvars.put(cvar, str);
    }

    private void readConfigFileShort(Toml conf, String cvar)
    {
        Long lng = conf.getLong(cvar);
        if (lng != null) {
            Short shrt = new Short(lng.shortValue());
            cvars.put(cvar, shrt);
        }
    }


    /* getters */

    public String getRootPath()
    {
        return (String) cvars.get("jaspe.root");
    }

    public short getRedisPort()
    {
        return ((Short) cvars.get("kvs.port")).shortValue();
    }
}
