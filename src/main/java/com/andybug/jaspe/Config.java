package com.andybug.jaspe;

import java.io.*;


class Config
{
    private ConfigFile configFile;


    public Config(String[] args) throws FileNotFoundException
    {
        configFile = ConfigFile.parse(args[0]);
        System.out.println(configFile);
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
