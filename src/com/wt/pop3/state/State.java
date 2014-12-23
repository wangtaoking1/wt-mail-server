package com.wt.pop3.state;

import com.wt.pop3.PopServiceThread;

/**
 * @author wangtao
 * @time 2014/12/22
 */
public abstract class State {
    /**
     * To handle the command from clients
     * @param service
     * @param args
     */
    public abstract void handle(PopServiceThread service, String[] args);
    
    /**
     * To update the operations into databases
     */
    public void synWithDB() {}
}
