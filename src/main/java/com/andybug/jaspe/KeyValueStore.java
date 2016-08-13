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
}
