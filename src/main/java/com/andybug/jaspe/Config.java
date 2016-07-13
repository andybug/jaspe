package com.andybug.jaspe;

import com.andybug.jaspe.exception.OutOfRangeException;


class Config
{

    /* internal classes */

    static class ServerPorts
    {
	public short jaspe;
	public short redis;
	public short postgres;
    }


    /* document members */

    public ServerPorts servers;


    /* methods */

    public boolean validate() throws OutOfRangeException
    {
	/* validate ports */
	if (!validatePort(servers.jaspe))
	    throw new OutOfRangeException("jaspe port " + servers.jaspe + " out of range");

	if (!validatePort(servers.redis))
	    throw new OutOfRangeException("redis port " + servers.redis + " out of range");

	if (!validatePort(servers.postgres))
	    throw new OutOfRangeException("postgres port " + servers.postgres + " out of range");

	return true;
    }


    /* private methods */

    private boolean validatePort(short port)
    {
	final int PORT_MAX = (1 << 16) - 1;

	if (port < 0 || port > PORT_MAX)
	    return false;

	return true;
    }

}
