package com.andybug.jaspe;


import java.io.*;


class ConfigParser
{
    private final File config;


    public ConfigParser(String configPath) throws FileNotFoundException
    {
	config = new File(configPath);

	/* make sure the file exists */
	if (!config.isFile())
	    throw new FileNotFoundException(configPath);
    }

    public Config parse()
    {
	return null;
    }
}
