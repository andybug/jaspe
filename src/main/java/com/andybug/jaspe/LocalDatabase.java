package com.andybug.jaspe;

import java.io.File;


class LocalDatabase
{
    private final File root;


    public LocalDatabase(Config config)
    {
	root = new File(config.getRootPath() + "/data");
    }
}
