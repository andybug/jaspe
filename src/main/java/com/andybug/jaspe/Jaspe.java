package com.andybug.jaspe;


public class Jaspe
{
    private Config config;
    private KeyValueStore kvs;
    private LocalDatabase local_db;


    public Jaspe(Config config)
    {
        this.config = config;

        try {
            kvs = new KeyValueStore(config);
            local_db = new LocalDatabase(config);
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

        try {
            Config config = new Config(args);
            Jaspe jaspe = new Jaspe(config);
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }
}
