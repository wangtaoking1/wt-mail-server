package com.wt.smtp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.wt.utils.LoggerFactory;
import com.wt.utils.MailMessage;
import com.wt.utils.mx.MXExchanger;

/**
 * This is a client to send message to server
 * @author wangtao
 * @time 2014/11/13
 */
public class SMTPClient {
    private Logger logger = LoggerFactory.getLogger(SMTPClient.class);
    
    private Socket socket = null;
    private BufferedReader input = null;
    private PrintWriter output = null;
    private String server = null;
    private int port;
    private MailMessage message;
    
    public SMTPClient() {
        logger.info("Create a smtp client");
    }
    
    public SMTPClient(MailMessage message) {
        logger.info("Create a smtp client");
        this.setMessage(message);
    }
    
    public void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }
    
    public MailMessage getMessage() {
        return message;
    }
    public void setMessage(MailMessage message) {
        this.message = message;
        this.setServerInfo();
    }
    
    private void setServerInfo() {
        int pos = this.message.getTo().indexOf("@");
        server = this.message.getTo().substring(pos + 1);
        if ("yahoo.com".equals(server) || "gmail.com".equals(server))
        {
            port = 465;
        }
        else
            port = 25;
        
        try {
            server = MXExchanger.getMxServer(server);
        }
        catch (Exception e) {
            logger.error(e);
            server = null;
        }
        
        logger.debug("server: " + server + "\tport: " + String.valueOf(port));
    }
    
    /**
     * Set server information
     * @param server
     * @param port
     */
    public void setServerInfo(String server, int port) {
        this.server = server;
        this.port = port;
    }
    
    /**
     * Send the message to the server
     * @return whether the message is sent successfully
     */
    public boolean sendMail() {
        boolean flag = true;
        try {
            init();
            
            sayHelo();
            
            setEnvelop();
            
            sendMessage();
            
            quit();
        }
        catch (Exception e) {
            logger.error(e);
            logger.info("Fail to send the message from " + this.message
                    .getFrom() + " to " + this.message.getTo());
            flag = false;
        }
        finally {
            this.close();
        }
        return flag;
    }

    /**
     * Initial the socket, input, output
     * @throws Exception
     */
    public void init() throws Exception {
        logger.info("Connecting " + server + " ...");
        
        socket = new Socket(server, port);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
        
        int token = getResultToken();
        if (token != 220)
        {
            throw new Exception("Connected " + server + " fail");
        }
        logger.info("Connected " + server + " successfully");
    }

    /**
     * Register from the server
     * @throws IOException
     */
    public void sayHelo() throws Exception {
        int token = this.sendData("HELO " + this.server);
        
        if (token != 250)
        {
            throw new Exception("register the server fail");
        }
        logger.info("register the server successfully");
    }
    
    /**
     * Set the mail from and to
     * @throws Exception
     */
    public void setEnvelop() throws Exception {
        //set the mail from
        int token = this.sendData("MAIL From:<" + this.message.getFrom() + ">");
        if (token != 250)
            throw new Exception("set envelop fail");
        
        //set the mail to
        token = this.sendData("RCPT To:<" + this.message.getTo() + ">");
        if (token != 250)
            throw new Exception("set envelop fail");
        
        logger.info("set envelop successfully");
    }
    
    /**
     * Send the content of the mail message
     * @throws Exception
     */
    public void sendMessage() throws Exception {
        int token = this.sendData("data");
        if (token != 354)
            throw new Exception("send 'data' command fail");
        
        token = this.sendData(this.message.getContent() + "\n.");
        if (token != 250)
            throw new Exception("send the body of message fail");
        
        logger.info("send the message successfully");
    }
    
    /**
     * Quit from the connection with the server
     * @throws Exception
     */
    public void quit() throws Exception {
        int token = this.sendData("QUIT");
        
        if (token != 221)
        {
            throw new Exception("quit fail");
        }
        logger.info("quit successfully");
        
    }
    
    /**
     * Send message to server
     * @param data "the data needed to send to the server"
     * @return "the returned token from the server"
     */
    private int sendData(String data) throws IOException {
        this.output.println(data);
        this.output.flush();
        
        logger.debug("Send '" + data + "' to the server successfully");
        return this.getResultToken();
    }
    
    /**
     * 
     * @return "the token of the returned string"
     */
    private int getResultToken() {
        String line = "";
        try {
            line = this.input.readLine();
        }
        catch (IOException e) {
            logger.error(e);
        }
        
        logger.debug(line);
        
        StringTokenizer get = new StringTokenizer(line, " ");
        return Integer.parseInt(get.nextToken());
    }
}
