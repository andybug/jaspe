package com.andybug.jaspe;

import java.io.File;


class LocalDatabase
{
    private final File root;


    public LocalDatabase(String path)
    {
	root = new File(path);
    }
}
