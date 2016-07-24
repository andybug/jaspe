package com.andybug.jaspe;

import redis.clients.jedis.Jedis;

class KeyValueStore
{
    private Jedis jedis = null;


    public KeyValueStore(short port)
    {
	jedis = new Jedis("localhost", port);
    }
}
