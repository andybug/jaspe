package com.andybug.jaspe;

import redis.clients.jedis.Jedis;

class KeyValueStore
{
    private Jedis jedis = null;


    public KeyValueStore(Config config)
    {
	jedis = new Jedis("localhost", config.getRedisPort());
    }
}
