package org.shujito.ucs;

import redis.clients.jedis.Jedis;

public class JedisWrapper
{
	public static final String TAG = JedisWrapper.class.getSimpleName();
	
	public static final Jedis open()
	{
		return new Jedis("127.0.0.1", 6379);
	}
}
