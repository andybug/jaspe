package com.andybug.jaspe;

import java.io.PrintStream;


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

        try {
            Config config = new Config(args);
            Jaspe jaspe = new Jaspe(config);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace(new PrintStream(System.err));
            System.exit(1);
        }
    }
}
