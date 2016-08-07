package com.andybug.jaspe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.TreeSet;


class LocalDatabase
{
    private final File root;
    private final File sport;


    public LocalDatabase(Config config) throws FileNotFoundException
    {
        root = new File(config.getRootPath() + "/data");
        sport = new File(root.getPath() + "/" + config.getSport());

        if (!sport.exists()) {
            throw new FileNotFoundException("Cannot find directory " + sport.getPath());
        }
    }

    public void load(KeyValueStore kvs) throws FileNotFoundException
    {
        TreeSet<File> seasons = scanSeasons();

        for (File season : seasons) {
            File games = new File(season + "/games.csv");
            File rounds = new File(season + "/rounds.csv");
            System.out.println(games + "\t" + rounds);
        }
    }

    private TreeSet<File> scanSeasons()
    {
        TreeSet<File> seasons = new TreeSet<File>();

        File[] files = sport_root.listFiles();
        for (File f : files) {
            seasons.add(f);
        }

        return seasons;
    }

    private void parseTeams(File teams, KeyValueStore kvs)
    {
    }

    private void parseGames(File games, KeyValueStore kvs)
    {
    }

    private void parseRounds(File rounds, KeyValueStore kvs)
    {
    }
}
