package com.andybug.jaspe;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.TreeSet;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


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

    public void load(KeyValueStore kvs) throws IOException, FileNotFoundException
    {
        TreeSet<File> seasons = scanSeasons();

        for (File season : seasons) {
            File games = new File(season + "/games.csv");
            File rounds = new File(season + "/rounds.csv");
            parseGames(games, kvs);
            System.exit(0);
        }
    }

    private TreeSet<File> scanSeasons()
    {
        TreeSet<File> seasons = new TreeSet<File>();

        File[] files = sport.listFiles();
        for (File f : files) {
            seasons.add(f);
        }

        return seasons;
    }

    private void parseTeams(File teams, KeyValueStore kvs)
    {
    }

    private void parseGames(File games, KeyValueStore kvs) throws IOException
    {
        FileReader reader = new FileReader(games);

        CSVParser parser = new CSVParser(reader, CSVFormat.RFC4180);
        List<CSVRecord> records = parser.getRecords();

        for (CSVRecord record : records.subList(1, records.size())) {
            Game game = new Game();
            game.setDate(record.get(0));
            game.setUUID(record.get(1));
            game.setHome(record.get(2));
            game.setHomeScore(record.get(3));
            game.setAway(record.get(4));
            game.setAwayScore(record.get(5));
            game.setNeutral(record.get(6));

            kvs.addGame(game);
        }
    }

    private void parseRounds(File rounds, KeyValueStore kvs)
    {
    }
}
