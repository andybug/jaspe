package com.andybug.jaspe;


public class Jaspe
{
    private Config config;
    private KeyValueStore kvs;
    private LocalDatabase local_db;


    public Jaspe(String config_path)
    {
	try {
	    config = new ConfigParser(config_path).parse();
	    kvs = new KeyValueStore(config.servers.redis);
	    local_db = new LocalDatabase(config.local_database.path);
	} catch (Exception e) {
	    System.err.println(e);
	    System.exit(1);
	}
    }

    public static void main( String[] args )
    {
        System.out.println("jaspe!");

	if (args.length < 1) {
	    System.err.println("jaspe <config.yaml>");
	    System.exit(1);
	}

	Jaspe jaspe = new Jaspe(args[0]);
    }
}
