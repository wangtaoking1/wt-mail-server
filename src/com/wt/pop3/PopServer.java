package com.wt.pop3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.wt.utils.LoggerFactory;

/**
 * This is a pop3 server
 * @author wangtao
 * @time 2014/12/22
 */
public class PopServer implements Runnable {
    private int port = 110;
    private ServerSocket server = null;
    private Executor service = null;

    public static Logger logger = LoggerFactory.getLogger(PopServer.class);

    public PopServer(int port) {
        this.port = port;
    }


    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            service = Executors.newCachedThreadPool();
            
            logger.info("Pop3 server starting at port " + this.port + " ...");
            
            while (true) {
                Socket client = server.accept();
                logger.info("A connection from " + client.getInetAddress()
                            .getHostAddress());

                //Create a new thread if there is no idle thread;
                //Reuse the old thread if there is a idle thread;
                service.execute(new PopServiceThread(client));
            }
        } catch (IOException e) {
            logger.error(e);
        }
        
        this.stop();
    }


    /**
     * This function is to stop the server
     */
    private void stop() {
        if (server != null) {
            try {
                server.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }
}
