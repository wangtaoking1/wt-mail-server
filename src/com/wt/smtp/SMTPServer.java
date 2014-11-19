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
public class SMTPServer {
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
    
    /**
     * This function is to start the server
     * @throws IOException
     */
    public void start() throws IOException {
        logger.info("SMTP server starting...");
        
        server = new ServerSocket(port);
        service = Executors.newCachedThreadPool();
        
        while(true) {
            Socket client = server.accept();
            logger.info("A connection from " + client.getInetAddress().getHostAddress());
            
            //Create a new thread if there is no idle thread;
            //Reuse the old thread if there is a idle thread;
            service.execute(new SMTPServiceThread(client, this.type));
        }
    }
    
    /**
     * This function is to stop the server
     */
    public void stop() {
        if (server != null) {
            try {
                server.close();
            }
            catch (Exception e) {
                SMTPServer.logger.error(e);
            }
        }
    }
}
