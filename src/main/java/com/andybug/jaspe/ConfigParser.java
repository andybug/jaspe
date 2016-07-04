package com.andybug.jaspe;


import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;


class ConfigParser
{
    private final File configFile;


    public ConfigParser(String configPath)
    {
	configFile = new File(configPath);
    }

    public Config parse() throws FileNotFoundException, IOException
    {
	/* make sure the file exists */
	if (!configFile.isFile())
	    throw new FileNotFoundException("Unable to read config file " + configFile.getPath());

	/* map the .yaml to the fields in the Config class */
	ObjectMapper mapper = new YAMLMapper();
	Config config = mapper.readValue(configFile, Config.class);

	/* validate config */
	boolean valid = config.validate();

	return config;
    }
}
