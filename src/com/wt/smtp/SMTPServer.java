package com.wt.smtp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.wt.utils.LoggerFactory;


/**
 * This is a SMTP server
 * @author wangtao
 * @time 2014/11/17
 */
public class SMTPServer implements Runnable {
    private int port = 25;
    private ServerSocket server = null;
    private Executor service = null;
    public static enum ServerType {FORCLIENT, FORSERVER};
    private ServerType type = null;

    public static Logger logger = LoggerFactory.getLogger(SMTPServer.class);

    public SMTPServer(ServerType type, int port) {
        this.type = type;
        this.port = port;
    }


    @Override
    public void run() {
        try {
            this.start();
        } catch (IOException e) {
            logger.error(e);
        }
        this.stop();
    }


    /**
     * This function is to start the server
     * @throws IOException
     */
    private void start() throws IOException {
        if (this.type == ServerType.FORCLIENT)
            logger.info("SMTP server for client starting at port " + this.port + " ...");
        else
            logger.info("SMTP server for server starting at port " + this.port + " ...");
        
        server = new ServerSocket(port);
        service = Executors.newCachedThreadPool();

        while (true) {
            Socket client = server.accept();
            logger.debug("A connection from " + client.getInetAddress()
                        .getHostAddress());

            //Create a new thread if there is no idle thread;
            //Reuse the old thread if there is a idle thread;
            service.execute(new SMTPServiceThread(client, this.type));
        }
    }

    
    /**
     * This function is to stop the server
     */
    private void stop() {
        if (server != null) {
            try {
                server.close();
            } catch (Exception e) {
                SMTPServer.logger.error(e);
            }
        }
    }
}
