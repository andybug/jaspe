package com.andybug.jaspe;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


class LocalDatabase
{
    private final File root;
    private final File sport;


    private class Season
    {
        private String season;
        private File root;
        private ArrayList<Round> rounds;


        public Season(File root)
        {
            this.season = root.getName();
            this.root = root;
            this.rounds = new ArrayList<Round>();
        }

        public void load(KeyValueStore kvs) throws IOException
        {
            File rounds_csv = new File(root + "/rounds.csv");
            File games_csv = new File(root + "/games.csv");

            kvs.addSeason(this.season);
            parseRounds(rounds_csv, kvs);
            parseGames(games_csv, kvs);
        }

        private void parseRounds(File rounds_csv, KeyValueStore kvs) throws IOException
        {
            FileReader reader = new FileReader(rounds_csv);

            CSVParser parser = new CSVParser(reader, CSVFormat.RFC4180);
            List<CSVRecord> records = parser.getRecords();

            for (CSVRecord record : records.subList(1, records.size())) {
                Round round = new Round(record.get(0), record.get(1));
                this.rounds.add(round);
                kvs.addRound(this.season, round.id, round.date);
            }
        }

        private void parseGames(File games_csv, KeyValueStore kvs) throws IOException
        {
            FileReader reader = new FileReader(games_csv);

            CSVParser parser = new CSVParser(reader, CSVFormat.RFC4180);
            List<CSVRecord> records = parser.getRecords();

            Iterator<Round> round_iter = rounds.iterator();
            Round round_current = round_iter.next();
            Round round_next = round_iter.next();
            for (CSVRecord record : records.subList(1, records.size())) {
                Game.Transporter game = new Game.Transporter();
                game.date = record.get(0);
                game.uuid = record.get(1);
                game.home = record.get(2);
                game.hscore = record.get(3);
                game.away = record.get(4);
                game.ascore = record.get(5);
                game.neutral = record.get(6);

                if (round_next != null && round_next.inRound(game.date)) {
                    round_current = round_next;
                    if (round_iter.hasNext())
                        round_next = round_iter.next();
                    else
                        round_next = null;
                }

                kvs.addGame(this.season, round_current.id, game);
            }
        }
    }


    private class Round
    {
        public String id;
        public String date;


        public Round(String id, String date)
        {
            this.id = id;
            this.date = date;
        }

        public boolean inRound(String date)
        {
            return date.compareTo(this.date) >= 0;
        }
    }


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
        TreeSet<File> season_dirs = scanSeasons(this.sport);

        for (File season_dir : season_dirs) {
            Season season = new Season(season_dir);
            season.load(kvs);
        }
    }

    public TreeSet<File> scanSeasons(File sport)
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
}
