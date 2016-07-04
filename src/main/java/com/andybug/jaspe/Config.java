package com.andybug.jaspe;

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

    public boolean validate()
    {
	boolean retval = true;

	/* validate ports */
	retval = retval && validatePort(servers.jaspe);
	retval = retval && validatePort(servers.redis);
	retval = retval && validatePort(servers.postgres);

	return retval;
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
