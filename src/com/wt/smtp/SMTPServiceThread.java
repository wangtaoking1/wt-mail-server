package com.wt.smtp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.wt.smtp.SMTPServer.ServerType;
import com.wt.smtp.state.HeloState;
import com.wt.smtp.state.SmtpReceiver;

/**
 * This is a thread class to accept the message from the client
 * @author wangtao
 * @time 2014/11/17
 */
public class SMTPServiceThread implements Runnable {
    
    private Socket client = null;
    private BufferedReader input = null;
    private PrintWriter output = null;
    private SmtpReceiver receiver = null;
    private ServerType type = null;
    
    
    public SMTPServiceThread(Socket client, ServerType type) {
        this.client = client;
        this.type = type;
    }
    
    
    public ServerType getType() {
        return this.type;
    }
    public SmtpReceiver getReceiver() {
        return this.receiver;
    }
    
    @Override
    public void run() {
        try {
            this.client.setSoTimeout(30 * 1000);
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream());
        }
        catch (Exception e) {
            SMTPServer.logger.error(e);
        }
        
        SMTPServer.logger.debug("Connection with the client " + 
                client.getInetAddress().getHostAddress() + " created");
        
        //Send hello info to the client
        this.writeToClient("220 Welcome to wt-mail smtp Server");
        
        //Initial with Helo state
        receiver = new SmtpReceiver(new HeloState());
        
        while(true) {
            try {
                String inStr = input.readLine();
                
                this.receiver.handleInput(this, inStr);

                if (client == null)
                    break;
            }
            catch (SocketTimeoutException e) {
                //Connection timeout
                SMTPServer.logger.error(e);
                break;
            }
            catch (Exception e) {
                SMTPServer.logger.error(e);
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
                this.writeToClient("221 Bye");
                client.close();
                SMTPServer.logger.debug("Connection with the client " + 
                    client.getInetAddress().getHostAddress() + " closed");
            } catch (Exception oE) {
                SMTPServer.logger.error(oE);
            }
            client = null;
        }
    }
}
