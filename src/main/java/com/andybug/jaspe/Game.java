package com.andybug.jaspe;

import java.util.Date;
import java.util.UUID;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;


class Game
{
    static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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

    public void setDate(String date)
    {
        try {
            this.date = df.parse(date);
        } catch (ParseException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    public void setUUID(String uuid)
    {
        this.uuid = UUID.fromString(uuid);
    }

    public void setHome(String uuid)
    {
        this.home = UUID.fromString(uuid);
    }

    public void setHomeScore(String score)
    {
        this.hscore = Short.valueOf(score);
    }

    public void setAway(String uuid)
    {
        this.away = UUID.fromString(uuid);
    }

    public void setAwayScore(String score)
    {
        this.ascore = Short.valueOf(score);
    }

    public void setNeutral(String neutral)
    {
        if (neutral.equals("0"))
            this.neutral = false;
        else
            this.neutral = true;
    }
}
