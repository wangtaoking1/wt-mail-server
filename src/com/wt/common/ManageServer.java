package com.wt.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.wt.utils.LoggerFactory;
import com.wt.utils.Manager;

/**
 * This is a user manage server
 * @author wangtao
 * @time 2014/12/21
 */
public class ManageServer implements Runnable {
    private int port = 5055;
    private ServerSocket server = null;
    private BufferedReader input = null;
    private PrintWriter output = null;

    public static Logger logger = LoggerFactory.getLogger(ManageServer.class);

    public ManageServer(int port) {
        this.port = port;
    }

    
    /**
     * This function is to start the server
     * @throws IOException
     */
    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            logger.info("Server for user manage starting at port " + this.port
                           + " ...");
            
            while (true) {
                Socket client = server.accept();
                logger.info("A connection from " + client.getInetAddress()
                            .getHostAddress());
                
                input = new BufferedReader(new InputStreamReader(
                            client.getInputStream()));
                output = new PrintWriter(client.getOutputStream());
                
                String inStr = input.readLine();
                String outStr = this.applyForRequest(inStr);
                
                this.output.println(outStr);
                this.output.flush();
                client.close();
            }
        }
        catch (IOException e) {
            logger.error(e);
        }
    }
    
    /**
     * To apply for the client request
     * @param inStr
     * @return
     */
    public String applyForRequest(String inStr) {
        String[] args = inStr.split(" ");
        String command = args[0].toLowerCase();
        if (command.equals("reg") && args.length == 3) {
            boolean flag = Manager.register(args[1], args[2]);
            return flag ? "200 ok" : "501 user exists";
        }
        if (command.equals("unreg") && args.length == 3) {
            boolean flag = Manager.unRegister(args[1], args[2]);
            return flag ? "200 ok" : "501 delete failed";
        }
        return "404 unknown command";
    }
}
