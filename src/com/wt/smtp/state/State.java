package com.wt.smtp.state;


import com.wt.smtp.SMTPServiceThread;

/**
 * This is the abstract class of command state
 * @author wangtao
 * @time 2014/11/13
 */
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
