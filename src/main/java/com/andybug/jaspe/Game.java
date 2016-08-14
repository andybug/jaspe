package com.andybug.jaspe;

import java.util.Date;
import java.util.UUID;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;


class Game
{
    static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    static class Transporter
    {
        public String date;
        public String uuid;
        public String home;
        public String hscore;
        public String away;
        public String ascore;
        public String neutral;
    }


    public Date date;
    public UUID uuid;
    public UUID home;
    public short hscore;
    public UUID away;
    public short ascore;
    public boolean neutral;

    public Game()
    {
    }

    public Game(Game.Transporter trans)
    {
        try {
            this.date = df.parse(trans.date);
        } catch (ParseException e) {
            System.err.println(e);
            System.exit(1);
        }

        this.uuid = UUID.fromString(trans.uuid);
        this.home = UUID.fromString(trans.uuid);
        this.hscore = Short.valueOf(trans.hscore);
        this.away = UUID.fromString(trans.uuid);
        this.ascore = Short.valueOf(trans.ascore);
        this.neutral = Boolean.valueOf(trans.neutral);
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("--- game ---\n");
        sb.append("date = " + date + "\n");
        sb.append("uuid = " + uuid + "\n");
        sb.append("home = " + home + "\n");
        sb.append("hscore = " + hscore + "\n");
        sb.append("away = " + away + "\n");
        sb.append("ascore = " + ascore + "\n");
        sb.append("neutral = " + neutral + "\n");

        return sb.toString();
    }
}
