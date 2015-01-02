package com.wt.pop3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.wt.pop3.state.AuthState;
import com.wt.pop3.state.PopReceiver;


/**
 * This is a thread class to push the message to the client
 * @author wangtao
 * @time 2014/12/22
 */
public class PopServiceThread implements Runnable {
    
    private Socket client = null;
    private BufferedReader input = null;
    private PrintWriter output = null;
    private PopReceiver receiver = null;
    
    public PopServiceThread(Socket client) {
        this.client = client;
    }
    
    public PopReceiver getReceiver() {
        return this.receiver;
    }
    
    @Override
    public void run() {
        try {
            this.client.setSoTimeout(30 * 1000);
            input = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            output = new PrintWriter(client.getOutputStream());
        }
        catch (Exception e) {
            PopServer.logger.error(e);
        }
        
        PopServer.logger.debug("Connection with the client " + 
                client.getInetAddress().getHostAddress() + " created");
        
        //Send hello info to the client
        this.writeToClient("+OK Welcome to wt-mail Pop3 Server");
        
        //Initial with Auth state
        receiver = new PopReceiver(new AuthState());
        
        while(true) {
            try {
                String inStr = input.readLine();
                
                this.receiver.handleInput(this, inStr);

                if (client == null)
                    break;
            }
            catch (SocketTimeoutException e) {
                //Connection timeout
                PopServer.logger.error(e);
                break;
            }
            catch (Exception e) {
                PopServer.logger.error(e);
                break;
            }
        }
        this.closeConnection();
    }
    
    /**
     * Write string to the client
     * @param outStr
     */
    public void writeToClient(String outStr) {
        this.output.println(outStr);
        this.output.flush();
    }
    
    /**
     * Close the connection with the client
     */
    public void closeConnection() {
      //close the client
        if (client != null) {
            try {
                client.close();
                PopServer.logger.debug("Connection with the client " + 
                    client.getInetAddress().getHostAddress() + " closed");
            } catch (Exception oE) {
                PopServer.logger.error(oE);
            }
            client = null;
        }
    }
}
