package com.andybug.jaspe;

import redis.clients.jedis.Jedis;

class KeyValueStore
{
    private Jedis jedis = null;


    public KeyValueStore(Config config)
    {
	short port = config.servers.redis;
	jedis = new Jedis("localhost", port);
    }
}
