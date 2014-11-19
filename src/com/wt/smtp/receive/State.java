package com.wt.smtp.receive;


import com.wt.smtp.SMTPServiceThread;

public abstract class State {
    
    /**
     * This function is to handle the command from the client
     * @param service
     * @param com
     * @param arg
     */
    public abstract void handle(SMTPServiceThread service, String com, 
            String arg);
    
    /**
     * This function is to handle the string (not command) from the client
     * @param service
     * @param inStr
     */
    public void handle(SMTPServiceThread service, String inStr) {}
}
