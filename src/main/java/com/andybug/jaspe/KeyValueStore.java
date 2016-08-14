package com.andybug.jaspe;

import java.util.Map;
import java.util.HashMap;

import redis.clients.jedis.Jedis;

class KeyValueStore
{
    private Jedis jedis = null;


    public KeyValueStore(Config config)
    {
	jedis = new Jedis("localhost", config.getRedisPort());
        jedis.flushAll();
    }

    public void addGame(Game game)
    {
        Map<String, String> values = new HashMap<String, String>();
        values.put("date", game.date.toString());
        values.put("home", game.home.toString());
        values.put("hscore", Short.toString(game.hscore));
        values.put("away", game.away.toString());
        values.put("ascore", Short.toString(game.ascore));
        values.put("neutral", Boolean.toString(game.neutral));

        jedis.hmset("game:" + game.uuid, values);
    }

    public void addGame(String season, String round, Game.Transporter game)
    {
        Map<String, String> values = new HashMap<String, String>();
        values.put("date", game.date);
        values.put("home", game.home);
        values.put("hscore", game.hscore);
        values.put("away", game.away);
        values.put("ascore", game.ascore);
        values.put("neutral", game.neutral);

        jedis.hmset("game:" + game.uuid, values);
        jedis.rpush("round:" + season + ":" + round + ":games", "game:" + game.uuid);
    }

    public void addSeason(String season)
    {
        jedis.rpush("seasons", "season:" + season);
    }

    public void addRound(String season, String id, String date)
    {
        String key = "round:" + season + ":" + id;
        jedis.set(key, date);
        jedis.rpush("season:" + season + ":rounds", key);
    }
}
