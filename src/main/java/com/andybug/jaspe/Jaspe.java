package com.andybug.jaspe;


public class Jaspe
{
    private KeyValueStore kvs;
    private LocalDatabase local_db;


    public Jaspe(String config_path)
    {
	try {
	    Config config = Config.parse(config_path);
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
