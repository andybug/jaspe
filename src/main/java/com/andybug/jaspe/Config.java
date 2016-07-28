package com.andybug.jaspe;

import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import com.andybug.jaspe.exception.OutOfRangeException;


class Config
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


    /* methods */

    public boolean validate() throws OutOfRangeException
    {
	/* validate ports */
	if (!validatePort(servers.jaspe))
	    throw new OutOfRangeException("jaspe port " + servers.jaspe + " out of range");

	if (!validatePort(servers.redis))
	    throw new OutOfRangeException("redis port " + servers.redis + " out of range");

	if (!validatePort(servers.postgres))
	    throw new OutOfRangeException("postgres port " + servers.postgres + " out of range");

	return true;
    }


    /* static methods */

    public static Config parse(String configPath)
	throws FileNotFoundException, IOException, OutOfRangeException
    {
	File configFile = new File(configPath);

	/* make sure the file exists */
	if (!configFile.isFile())
	    throw new FileNotFoundException("Unable to read config file " + configFile.getPath());

	/* map the .yaml to the fields in the Config class */
	ObjectMapper mapper = new YAMLMapper();
	Config config = mapper.readValue(configFile, Config.class);

	/* validate config */
	config.validate();

	return config;
    }


    /* private methods */

    private boolean validatePort(short port)
    {
	final int PORT_MAX = (1 << 16) - 1;

	if (port < 0 || port > PORT_MAX)
	    return false;

	return true;
    }
}
