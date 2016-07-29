package com.andybug.jaspe;

import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;


class ConfigFile
{
    /* internal classes */

    static class ServerPorts
    {
        public short jaspe;
        public short redis;
        public short postgres;
    }

    static class LocalDatabaseSettings
    {
        public String path;
    }

    /* document members */

    public ServerPorts servers;
    public LocalDatabaseSettings local_database;


    /* static methods */

    public static ConfigFile getDefaultConfigFile()
    {
        ConfigFile config = new ConfigFile();

        config.servers.jaspe = 30500;
        config.servers.redis = 6379;
        config.servers.postgres = 5432;

        config.local_database.path = "/usr/share/jaspe/data";

        return config;
    }

    public static ConfigFile parse(String configPath)
        throws FileNotFoundException
    {
        File file = new File(configPath);

        /* make sure the file exists */
        if (!file.isFile())
            throw new FileNotFoundException("Unable to read config file " +
                                            file.getPath());

        ConfigFile configFile = null;

        try {
            /* map the .yaml to the fields in the Config class */
            ObjectMapper mapper = new YAMLMapper();
            configFile = mapper.readValue(file, ConfigFile.class);
        } catch (Exception e) {
            System.err.println(e);
        }

        return configFile;
    }


    /* methods */

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ConfigFile ---\n");
        sb.append("servers.jaspe = " + servers.jaspe + "\n");
        sb.append("servers.redis = " + servers.redis + "\n");
        sb.append("servers.postgres = " + servers.postgres + "\n");
        sb.append("local_database.path = " + local_database.path + "\n");

        return sb.toString();
    }

    public boolean validate()
    {
        return true;
    }
}
