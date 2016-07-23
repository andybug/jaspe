package com.andybug.jaspe;


public class Jaspe
{
    public static void main( String[] args )
    {
        System.out.println("jaspe!");

	if (args.length < 1) {
	    System.err.println("jaspe <config.yaml>");
	    System.exit(1);
	}

	try {
	    Config config = new ConfigParser(args[0]).parse();
	    KeyValueStore kvs = new KeyValueStore(config);
	} catch (Exception e) {
	    System.err.println(e);
	    System.exit(1);
	}
    }
}
