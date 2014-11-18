package com.wt.smtp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.wt.smtp.SMTPServer.ServerType;
import com.wt.smtp.receive.HeloState;
import com.wt.smtp.receive.MailReceiver;

/**
 * This is a thread class to accept the message from the client
 * @author wangtao
 * @time 2014/11/17
 */
public class ServiceThread implements Runnable {
    
    private Socket client = null;
    private BufferedReader input = null;
    private PrintWriter output = null;
    private MailReceiver receiver = null;
    private ServerType type = null;
    
    public ServiceThread(Socket client, ServerType type) {
        this.client = client;
        this.type = type;
    }
    
    
    public ServerType getType() {
        return this.type;
    }
    public MailReceiver getReceiver() {
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
        
        SMTPServer.logger.info("Communicating with the client " + 
                client.getInetAddress().getHostAddress() + " ...");
        
        receiver = new MailReceiver(new HeloState());
        
        while(true) {
            try {
                String inStr = input.readLine();
                
                this.receiver.handleInput(this, inStr);
            }
            catch (SocketTimeoutException e) {
                SMTPServer.logger.error(e);
                this.writeToClient("Timeout, Connection closed by server.");
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
            } catch (Exception oE) {
                SMTPServer.logger.error(oE);
            }
        }
        
        SMTPServer.logger.info("Communication with the client " + 
                client.getInetAddress().getHostAddress() + " finished");
    }
}
