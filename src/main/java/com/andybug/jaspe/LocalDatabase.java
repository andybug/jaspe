package com.andybug.jaspe;

import java.io.File;
import java.io.FileNotFoundException;


class LocalDatabase
{
    private final File root;
    private final String sport;
    private final File sport_root;


    public LocalDatabase(Config config) throws FileNotFoundException
    {
        root = new File(config.getRootPath() + "/data");
        sport = config.getSport();
        sport_root = new File(root.getPath() + "/" + sport);

        if (!sport_root.exists()) {
            throw new FileNotFoundException("Cannot find directory " + sport_root.getPath());
        }
    }
}
