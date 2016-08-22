package com.andybug.jaspe;

import java.io.File;
import java.io.IOException;

import org.zeromq.ZMQ;
import org.zeromq.ZMQException;


class ClientManager
{
    private class SocketListener implements Runnable
    {
        private short port;

        public SocketListener(short port)
        {
            this.port = port;
        }

        public void run()
        {
            ZMQ.Socket socket = context.socket(ZMQ.REP);
            socket.bind("tcp://*:" + Short.toString(port));

            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("socket.recv()");
                try {
                    byte[] msg = socket.recv(0);
                    System.out.println("Received: " + new String(msg, ZMQ.CHARSET));
                    String response = "yes, this is the server";
                    socket.send(response.getBytes(ZMQ.CHARSET), 0);
                } catch (ZMQException e) {
                    if (e.getErrorCode() == ZMQ.Error.ETERM.getCode()) {
                        System.out.println("SocketListener received TERM");
                        break;
                    }
                }
            }

            socket.close();
        }
    }


    private ZMQ.Context context = ZMQ.context(1);
    private Thread listen_thread;
    private File client_dir;
    private short port;

    public ClientManager(Config cfg)
    {
        this.client_dir = new File(cfg.getRootPath() + "/clients");
        this.port = cfg.getJaspePort();
        this.listen_thread = new Thread(new SocketListener(port), "zeromq listen loop");
    }

    public void launchClients()
    {
        this.listen_thread.start();
        try {
            Runtime.getRuntime().exec(this.client_dir.getPath() + "/elo --port=" + this.port);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    public void waitOnClients()
    {
        try {
            //context.term();
            //this.listen_thread.interrupt();
            this.listen_thread.join();
        } catch (InterruptedException e) {
            return;
        }
    }
}
